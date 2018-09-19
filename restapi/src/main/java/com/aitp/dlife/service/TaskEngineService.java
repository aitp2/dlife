package com.aitp.dlife.service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.background.TaskEngineRunner;
import com.aitp.dlife.domain.SystemTotalPoints;
import com.aitp.dlife.domain.TaskDefine;
import com.aitp.dlife.domain.UserEvent;
import com.aitp.dlife.domain.UserPointDetails;
import com.aitp.dlife.domain.UserTask;
import com.aitp.dlife.domain.WechatUser;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.repository.SystemTotalPointsRepository;
import com.aitp.dlife.repository.TaskDefineRepository;
import com.aitp.dlife.repository.UserEventRepository;
import com.aitp.dlife.repository.UserPointDetailsRepository;
import com.aitp.dlife.repository.UserTaskRepository;
import com.aitp.dlife.repository.WechatUserRepository;
import com.aitp.dlife.service.dto.EventResultDTO;
import com.aitp.dlife.service.dto.UserEventDTO;
import com.aitp.dlife.service.dto.UserPointDetailsDTO;
import com.aitp.dlife.service.dto.UserTaskDTO;
import com.aitp.dlife.service.mapper.UserEventMapper;
import com.aitp.dlife.service.mapper.UserPointDetailsMapper;
import com.aitp.dlife.service.mapper.UserTaskMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class TaskEngineService.
 */
@Service
@Transactional
public class TaskEngineService {

	/** The Constant SYSTEM_POINT_NAME. */
	private static final String SYSTEM_POINT_NAME = "allsystem";

	/** The Constant MODIFIED_BY. */
	private static final String MODIFIED_BY = "TaskEngine";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(TaskEngineService.class);

	/** The user event repository. */
	@Autowired
	private UserEventRepository userEventRepository;

	/** The task define repository. */
	@Autowired
	private TaskDefineRepository taskDefineRepository;

	/** The user task repository. */
	@Autowired
	private UserTaskRepository userTaskRepository;

	/** The user event mapper. */
	@Autowired
	private UserEventMapper userEventMapper;

	/** The task engine runner. */
	@Autowired
	private TaskEngineRunner taskEngineRunner;

	/** The customer repository. */
	@Autowired
	private WechatUserRepository wechatUserRepository;

	/** The user point details repository. */
	@Autowired
	private UserPointDetailsRepository userPointDetailsRepository;

	/** The system total points repository. */
	@Autowired
	private SystemTotalPointsRepository systemTotalPointsRepository;

	/** The user task mapper. */
	@Autowired
	private UserTaskMapper userTaskMapper;

	/** The user point details mapper. */
	@Autowired
	private UserPointDetailsMapper userPointDetailsMapper;

	/**
	 * Save new event.
	 *
	 * @param userEventDTO
	 *            the user event DTO
	 * @return the event result DTO
	 */
	public EventResultDTO saveNewEvent(@Valid UserEventDTO userEventDTO) {
		EventResultDTO dto = new EventResultDTO();
		dto.setAccept(false);

		List<UserEvent> events = userEventRepository.findByUseridAndUuid(userEventDTO.getUserid(),
				userEventDTO.getUuid());
		if (events.isEmpty()) {
			// find task define
			List<TaskDefine> defines = taskDefineRepository
					.findByStatusAndEventTypeAndTargetSystemsLikeOrderByPriorityDesc(true, userEventDTO.getEventType(),
							"%" + userEventDTO.getTargetSystem() + "%");
			if (defines.size() > 0) {
				// save user event
				UserEvent userEvent = userEventMapper.toEntity(userEventDTO);
				userEvent.setId(null);
				userEvent.setApplyTask(null);
				userEvent.setValidateTo(null);
				userEvent.setStatus(false);
				userEventRepository.save(userEvent);

				dto.setAccept(true);
				dto.setMessage("create new event");
				return dto;
			} else {
				dto.setMessage("no task define for the event");
			}
		} else {
			dto.setMessage("dulpited event");
		}

		return dto;
	}

	/**
	 * Evaluate user events.
	 */
	public void evaluateUserEvents() {
		// search user event
		List<UserEvent> events = userEventRepository.findByStatusOrderByCreateTime(false);
		for (UserEvent event : events) {
			// search taskdefine
			List<TaskDefine> defines = taskDefineRepository
					.findByStatusAndEventTypeAndTargetSystemsLikeOrderByPriorityDesc(true, event.getEventType(),
							"%" + event.getTargetSystem() + "%");
			// evaluate
			if (defines.isEmpty()) {
				saveUnApplyUserEvent(event);
				log.warn("no task define for the event " + event.getId());
				continue;
			}
			// apply
			for (TaskDefine taskDefine : defines) {
				if (evaluateTaskDefine(event, taskDefine)) {
					return;
				}
			}
			saveUnApplyUserEvent(event);
		}
	}

	/**
	 * Save un apply user event.
	 *
	 * @param event
	 *            the event
	 */
	private void saveUnApplyUserEvent(UserEvent event) {
		// no apply user event set to true status
		event.setLastModifyBy(MODIFIED_BY);
		event.setLastModifyTime(ZonedDateTime.now());
		event.setStatus(true);
		// if no applied, validate to event time plus one day.
		event.setValidateTo(event.getEventTime().plusDays(1));
		userEventRepository.save(event);
	}

	/**
	 * Evaluate task define.
	 *
	 * @param event
	 *            the event
	 * @param taskDefine
	 *            the task define
	 * @return true, if successful
	 */
	private boolean evaluateTaskDefine(UserEvent event, TaskDefine taskDefine) {
		Conditions cond = new Conditions();
		cond.setUserEvent(event);
		cond.setTaskDefine(taskDefine);
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode all = mapper.readTree(taskDefine.getConditions());
			JsonNode nodes = all.get("conditions");
			for (int i = 0; i < nodes.size(); i++) {
				String condition = nodes.get(i).get("condition").asText();
				int point = nodes.get(i).get("point").asInt();
				cond.setCondition(condition);
				cond.setPoint(point);
				initPeriodCondition(cond, event.getEventTime());
				// search user event by userid, status, start, end, event,
				// taskid
				long records = userEventRepository.countByConditions(event.getUserid(), true, cond.getPeriodStart(),
						cond.getPeriodEnd(), event.getEventType().toString(), taskDefine.getId());
				log.debug("condition record  = " + records);

				cond.setRecords(records);
				if (evaluate(cond)) {
					applyEvent(cond);
					return true;
				}
			}

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * Inits the period condition.
	 *
	 * @param cond
	 *            the cond
	 * @param eventTime
	 *            the event time
	 */
	private void initPeriodCondition(Conditions cond, ZonedDateTime eventTime) {
		ZonedDateTime startTime;
		switch (cond.getTaskDefine().getPeriod()) {
		case DAILY:
			startTime = eventTime.truncatedTo(ChronoUnit.DAYS);
			cond.setPeriodStart(startTime);
			cond.setPeriodEnd(startTime.plusDays(1));
			break;
		case WEEKLY:
			startTime = eventTime.with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
			cond.setPeriodStart(startTime);
			cond.setPeriodEnd(startTime.plusWeeks(1));
			break;
		case MOTHLY:
			startTime = eventTime.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
			cond.setPeriodStart(startTime);
			cond.setPeriodEnd(startTime.plusMonths(1));
			break;
		default:
			startTime = eventTime.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
			cond.setPeriodStart(eventTime.minusYears(100));
			cond.setPeriodEnd(eventTime.plusYears(100));
		}

		log.debug("period start : " + cond.getPeriodStart());
		log.debug("period end : " + cond.getPeriodEnd());

	}

	/**
	 * Apply event.
	 *
	 * @param cond
	 *            the cond
	 */
	private void applyEvent(Conditions cond) {
		log.debug("apply start");

		// minus the system total point
		SystemTotalPoints systotal = applySystemTotalPoints(cond);
		if (systotal == null) {
			return;
		}

		// update userTask
		applyUserTask(cond);
		// change status of user event
		applyUserEvent(cond);
		// append point to user (detail and total)
		applyPointForUser(cond);

	}

	/**
	 * Apply point for user.
	 *
	 * @param cond the cond
	 */
	private void applyPointForUser(Conditions cond) {
		log.info(cond.getUserEvent().getUserid() + " gant point " + cond.getPoint());
		WechatUser user = wechatUserRepository.getOne(Long.parseLong(cond.getUserEvent().getUserid()));
		if (user.getTotalPoint() == null) {
			user.setTotalPoint(0);
		}
		user.setTotalPoint(user.getTotalPoint() + cond.getPoint());
		wechatUserRepository.save(user);

		UserPointDetails detail = new UserPointDetails();
		detail.setUserid(cond.getUserEvent().getUserid());
		detail.setApplyTime(cond.getUserEvent().getEventTime());
		detail.setChangePoint(cond.getPoint());
		detail.setCreateBy(MODIFIED_BY);
		detail.setCreateTime(ZonedDateTime.now());

		String event = cond.getUserEvent().getEventName();
		if (event == null || event.isEmpty()) {
			event = cond.getTaskDefine().getName();
		}
		detail.setDescript(
				String.format("在%s通过%s获得%d积分", cond.getUserEvent().getTargetSystem(), event, cond.getPoint()));

		detail.setEventType(cond.getUserEvent().getEventType());
		detail.setHandleBy(MODIFIED_BY);
		detail.setTargetSystem(cond.getUserEvent().getTargetSystem());
		detail.setTotalPoint(user.getTotalPoint());
		userPointDetailsRepository.save(detail);
	}

	/**
	 * Apply user event.
	 *
	 * @param cond
	 *            the cond
	 */
	private void applyUserEvent(Conditions cond) {
		cond.getUserEvent().setLastModifyBy(MODIFIED_BY);
		cond.getUserEvent().setLastModifyTime(ZonedDateTime.now());
		cond.getUserEvent().setStatus(true);
		cond.getUserEvent().setValidateTo(cond.getPeriodEnd());
		cond.getUserEvent().setApplyTask(cond.getTaskDefine());
		userEventRepository.save(cond.getUserEvent());
	}

	/**
	 * Apply user task.
	 *
	 * @param cond
	 *            the cond
	 */
	private void applyUserTask(Conditions cond) {
		List<UserTask> lists = userTaskRepository.findByConditions(cond.getUserEvent().getUserid(),
				cond.getTaskDefine().getId(), ZonedDateTime.now());
		if (lists.isEmpty()) {
			// create new one
			UserTask userTask = new UserTask();
			userTask.setGainPoint(cond.getPoint());
			userTask.setRemainPoint(cond.getTaskDefine().getTotalPoint() - cond.getPoint());
			userTask.setTask(cond.getTaskDefine());
			userTask.setTaskStatus(1);
			userTask.setUserid(cond.getUserEvent().getUserid());
			userTask.setValidateTo(cond.getPeriodEnd());
			userTask.setCreateBy(MODIFIED_BY);
			userTask.setCreateTime(ZonedDateTime.now());
			userTaskRepository.save(userTask);
		} else {
			UserTask userTask = lists.get(0);
			userTask.setLastModifyTime(ZonedDateTime.now());
			userTask.setLastModifyBy(MODIFIED_BY);
			userTask.setGainPoint(userTask.getGainPoint() + cond.getPoint());
			userTask.setRemainPoint(cond.getTaskDefine().getTotalPoint() - userTask.getGainPoint());
			userTask.setTaskStatus(userTask.getTaskStatus() + 1);
			userTaskRepository.save(userTask);
		}
	}

	/**
	 * Apply system total points.
	 *
	 * @param cond
	 *            the cond
	 * @return the system total points
	 */
	private SystemTotalPoints applySystemTotalPoints(Conditions cond) {
		SystemTotalPoints systotal = systemTotalPointsRepository.findBySystemId(SYSTEM_POINT_NAME);
		if (systotal == null || systotal.getTotalPoint() < cond.getPoint()) {
			log.error("System has no point to apply now!!!");
			this.saveUnApplyUserEvent(cond.getUserEvent());
			return null;
		}
		systotal.setLastModifyBy(MODIFIED_BY);
		systotal.setLastModifyTime(ZonedDateTime.now());
		systotal.setTotalPoint(systotal.getTotalPoint() - cond.getPoint());
		systemTotalPointsRepository.save(systotal);
		return systotal;
	}

	/**
	 * Evaluate.
	 *
	 * @param cond
	 *            the cond
	 * @return true, if successful
	 */
	private boolean evaluate(Conditions cond) {
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext itemContext = new StandardEvaluationContext(cond);
		Expression exp = parser.parseExpression(cond.getCondition());
		return exp.getValue(itemContext, Boolean.class);
	}

	/**
	 * Stop engine thread.
	 */
	public void stopEngineThread() {
		taskEngineRunner.setRunning(false);
	}

	/**
	 * Start engine thread.
	 */
	public void startEngineThread() {
		taskEngineRunner.start();
	}

	/**
	 * Clean history data.
	 */
	public void cleanHistoryData() {
		if (ZonedDateTime.now().get(ChronoField.HOUR_OF_DAY) == 6) {
			log.info("remove user task : " + userTaskRepository.deleteByValidateToLessThan(ZonedDateTime.now()));
			log.info("remove user event : " + userEventRepository.deleteByValidateToLessThan(ZonedDateTime.now()));
		}
	}

	/**
	 * Gets the user tasks.
	 *
	 * @param userid
	 *            the userid
	 * @return the user tasks
	 */
	public List<UserTaskDTO> getUserTasks(String userid) {
		List<UserTaskDTO> all = new ArrayList<>();
		List<TaskDefine> taskList = taskDefineRepository.findByStatusOrderByPriorityDesc(true);
		for (TaskDefine taskDefine : taskList) {
			List<UserTask> userTasks = userTaskRepository.findByConditions(userid, taskDefine.getId(),
					ZonedDateTime.now());
			UserTaskDTO dto;
			if (!userTasks.isEmpty()) {
				dto = userTaskMapper.toDto(userTasks.get(0));
			} else {
				dto = new UserTaskDTO();
				dto.setRemainPoint(taskDefine.getTotalPoint());
				dto.setGainPoint(0);
				dto.setTaskStatus(0);
				dto.setUserid(userid);
			}
			dto.setMaxlimit(taskDefine.getMaxlimit());
			dto.setTargetSystems(taskDefine.getTargetSystems());
			dto.setTaskDefineName(taskDefine.getName());
			dto.setEventType(taskDefine.getEventType());
			dto.setTaskId(taskDefine.getId());
			dto.setPeroid(taskDefine.getPeriod());
			all.add(dto);
		}

		return all;
	}

	/**
	 * Update point details.
	 *
	 * @param userPointDetailsDTO
	 *            the user point details DTO
	 */
	public void updatePointDetails(@Valid UserPointDetailsDTO userPointDetailsDTO) {
		if (PointEventType.SYSRECHARGE.equals(userPointDetailsDTO.getEventType())) {
			// recharge
			SystemTotalPoints systotal = systemTotalPointsRepository.findBySystemId(SYSTEM_POINT_NAME);
			if (systotal == null) {
				log.error("System has no point record!!!");
				return;
			}
			systotal.setLastModifyBy(MODIFIED_BY);
			systotal.setLastModifyTime(ZonedDateTime.now());
			systotal.setTotalPoint(systotal.getTotalPoint() + userPointDetailsDTO.getChangePoint());
			systemTotalPointsRepository.save(systotal);

			UserPointDetails detail = userPointDetailsMapper.toEntity(userPointDetailsDTO);
			detail.setTotalPoint(systotal.getTotalPoint());
			detail.setId(null);
			detail.setLastModifyBy(MODIFIED_BY);
			detail.setLastModifyTime(ZonedDateTime.now());
			userPointDetailsRepository.save(detail);

		} else if (PointEventType.REDEEM.equals(userPointDetailsDTO.getEventType())
				|| PointEventType.RECHARGE.equals(userPointDetailsDTO.getEventType())) {
			// order redeem
			WechatUser user = wechatUserRepository.getOne(Long.parseLong(userPointDetailsDTO.getUserid()));
			if (user.getTotalPoint() == null) {
				user.setTotalPoint(0);
			}
			user.setTotalPoint(user.getTotalPoint() + userPointDetailsDTO.getChangePoint());
			wechatUserRepository.save(user);

			UserPointDetails detail = userPointDetailsMapper.toEntity(userPointDetailsDTO);
			detail.setTotalPoint(user.getTotalPoint());
			detail.setId(null);
			detail.setLastModifyBy(MODIFIED_BY);
			detail.setLastModifyTime(ZonedDateTime.now());
			userPointDetailsRepository.save(detail);
		}
	}

}
