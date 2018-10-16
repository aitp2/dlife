package com.aitp.dlife.service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.aitp.dlife.domain.TaskGroup;
import com.aitp.dlife.domain.UserEvent;
import com.aitp.dlife.domain.UserPointDetails;
import com.aitp.dlife.domain.UserTask;
import com.aitp.dlife.domain.WechatUser;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.repository.SystemTotalPointsRepository;
import com.aitp.dlife.repository.TaskDefineRepository;
import com.aitp.dlife.repository.TaskGroupRepository;
import com.aitp.dlife.repository.UserEventRepository;
import com.aitp.dlife.repository.UserPointDetailsRepository;
import com.aitp.dlife.repository.UserTaskRepository;
import com.aitp.dlife.repository.WechatUserRepository;
import com.aitp.dlife.service.dto.EventResultDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.dto.QuestionDTO;
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

	/** The task group repository. */
	@Autowired
	private TaskGroupRepository taskGroupRepository;

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
	
	@Autowired
	private FitnessActivityService fitnessActivityService;
	@Autowired
	private PinFanActivityService pinFanActivityService;
	@Autowired
	private QuestionService questionService;

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

		boolean validated = validateUserEventDTO(userEventDTO);

		List<UserEvent> events = userEventRepository.findByUseridAndUuid(userEventDTO.getUserid(),
				userEventDTO.getUuid());
		if (validated && events.isEmpty()) {
			// find task define, support multi targets
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
			} else {
				dto.setMessage("no task define for the event");
			}
		} else {
			dto.setMessage("dulpited event");
		}

		return dto;
	}
	
	/**
	 * Save new event.
	 *
	 * @param userId
	 * @param eventName
	 * @param eventType
	 * @param targetSystem
	 * @param comment 
	 * @return the event result DTO
	 */
	public EventResultDTO saveNewEvent(String userId,String eventName,PointEventType eventType,String targetSystem, String comment) {
		UserEventDTO userEventDTO =new UserEventDTO();
		userEventDTO.setUserid(userId);
		userEventDTO.setEventName(eventName);
		userEventDTO.setEventType(eventType);
		userEventDTO.setTargetSystem(targetSystem);
		userEventDTO.setUuid(UUID.randomUUID().toString());
		if(StringUtils.isNotBlank(comment))
		{
			userEventDTO.setParem2(comment);
		}
		userEventDTO.setEventTime(ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
		return this.saveNewEvent(userEventDTO);
	}
	
	public EventResultDTO saveNewEventWithComment(String userId,String eventName,PointEventType eventType,String targetSystem, Long objectId) {
		String title=null;
		if(objectId!=null)
		{
			if(CommentChannel.FIT.toString().equals(targetSystem))
			{
				FitnessActivityDTO fit = fitnessActivityService.findOne(objectId);
				title=fit!=null?fit.getTitle():null;
			}
			else if(CommentChannel.PIN.toString().equals(targetSystem))
			{
				PinFanActivityDTO pin = pinFanActivityService.findOne(objectId);
				title=pin!=null?pin.getActivitiyTile():null;
			}
			else if(CommentChannel.FAQS.toString().equals(targetSystem))
			{
				Optional<QuestionDTO> question = questionService.findOne(objectId);
				title=question.isPresent()?question.get().getTitle():null;
			}
		}
		return  saveNewEvent( userId, eventName, eventType, targetSystem,title);
	}

	/**
	 * Validate user event DTO.
	 *
	 * @param userEventDTO the user event DTO
	 * @return true, if successful
	 */
	private boolean validateUserEventDTO(UserEventDTO userEventDTO) {
		if (userEventDTO.getEventTime() == null || userEventDTO.getEventType() == null
				|| userEventDTO.getUserid() == null || userEventDTO.getTargetSystem() == null) {
			return false;
		}
		return true;
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
				cond.setRecords(records);

				// find UserTask with groupid
				if (taskDefine.getGroupid() != null) {
					findValidUserTaskByGroupid(cond);
				}

				log.debug("condition record  = " + records);

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
	 * Find valid user task by groupid.
	 *
	 * @param cond
	 *            the cond
	 */
	private void findValidUserTaskByGroupid(Conditions cond) {
		List<UserTask> tasks = this.userTaskRepository.findByUseridAndGroupidAndValidateToGreaterThan(
				cond.getUserEvent().getUserid(), cond.getTaskDefine().getGroupid(), cond.getUserEvent().getEventTime());
		if (tasks.isEmpty()) {
			TaskGroup taskGroup = taskGroupRepository.getOne(Long.parseLong(cond.getTaskDefine().getGroupid()));
			UserTask task = new UserTask();
			task.setCreateBy(MODIFIED_BY);
			task.setCreateTime(ZonedDateTime.now());
			task.setGainPoint(0);
			task.setGroupid(cond.getTaskDefine().getGroupid());
			task.setGroupName(taskGroup.getGroupName());
			task.setRemainPoint(taskGroup.getMaxPoints());
			task.setTaskStatus(0);
			task.setUserid(cond.getUserEvent().getUserid());
			cond.setUserTask(task);
		} else {
			cond.setUserTask(tasks.get(0));
		}
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

		// calculate gaint point
		Integer gaint = cond.getUserTask().getRemainPoint() > cond.getPoint() ? cond.getPoint() : cond.getUserTask().getRemainPoint();
		cond.setGaintPoint(gaint);
		
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
	 * @param cond
	 *            the cond
	 */
	private void applyPointForUser(Conditions cond) {
		log.info(cond.getUserEvent().getUserid() + " gant point " + cond.getGaintPoint());
		WechatUser user = wechatUserRepository.getOne(Long.parseLong(cond.getUserEvent().getUserid()));
		if (user.getTotalPoint() == null) {
			user.setTotalPoint(0);
		}
		user.setTotalPoint(user.getTotalPoint() + cond.getGaintPoint());
		wechatUserRepository.save(user);

		UserPointDetails detail = new UserPointDetails();
		detail.setUserid(cond.getUserEvent().getUserid());
		detail.setApplyTime(cond.getUserEvent().getEventTime());
		detail.setChangePoint(cond.getGaintPoint());
		detail.setCreateBy(MODIFIED_BY);
		detail.setCreateTime(ZonedDateTime.now());

		String event = cond.getUserEvent().getEventName();
		if (event == null || event.isEmpty()) {
			event = cond.getTaskDefine().getName();
		}
//		detail.setDescript(
//				String.format("在%s通过%s获得%d积分", cond.getUserEvent().getTargetSystem(), event, cond.getPoint()));
		detail.setEventName(event);
		// description
		String descript = cond.getUserEvent().getParem2() == null ? StringUtils.EMPTY : cond.getUserEvent().getParem2();
		detail.setDescript(descript);
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
		if (cond.getUserTask() == null) {
			applyUserTaskWithoutGroupid(cond);
		} else {
			applyUserTaskWithGroupid(cond);
		}
	}

	/**
	 * Apply user task with groupid.
	 *
	 * @param cond
	 *            the cond
	 */
	private void applyUserTaskWithGroupid(Conditions cond) {
		UserTask userTask = cond.getUserTask();
		userTask.setTaskStatus(userTask.getTaskStatus() + 1);
		userTask.setGainPoint(userTask.getGainPoint() + cond.getGaintPoint());
		userTask.setRemainPoint(userTask.getRemainPoint() - cond.getGaintPoint());
		userTask.setValidateTo(cond.getPeriodEnd());
		userTask.setLastModifyTime(ZonedDateTime.now());
		userTask.setLastModifyBy(MODIFIED_BY);
		userTaskRepository.save(userTask);
	}

	/**
	 * Apply user task without groupid.
	 *
	 * @param cond
	 *            the cond
	 */
	private void applyUserTaskWithoutGroupid(Conditions cond) {
		// no task group
		List<UserTask> lists = userTaskRepository.findByConditions(cond.getUserEvent().getUserid(),
				cond.getTaskDefine().getId(), ZonedDateTime.now());
		if (lists.isEmpty()) {
			// create new one
			UserTask userTask = new UserTask();
			userTask.setGainPoint(cond.getGaintPoint());
			userTask.setRemainPoint(cond.getTaskDefine().getTotalPoint() - cond.getGaintPoint());
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
			userTask.setGainPoint(userTask.getGainPoint() + cond.getGaintPoint());
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
		if (systotal == null || systotal.getTotalPoint() < cond.getGaintPoint()) {
			log.error("System has no point to apply now!!!");
			this.saveUnApplyUserEvent(cond.getUserEvent());
			return null;
		}
		systotal.setLastModifyBy(MODIFIED_BY);
		systotal.setLastModifyTime(ZonedDateTime.now());
		systotal.setTotalPoint(systotal.getTotalPoint() - cond.getGaintPoint());
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
		// List<UserTaskDTO> all = new ArrayList<>();
		// List<TaskDefine> taskList =
		// taskDefineRepository.findByStatusOrderByPriorityDesc(true);
		// for (TaskDefine taskDefine : taskList) {
		// List<UserTask> userTasks =
		// userTaskRepository.findByConditions(userid, taskDefine.getId(),
		// ZonedDateTime.now());
		// UserTaskDTO dto;
		// if (!userTasks.isEmpty()) {
		// dto = userTaskMapper.toDto(userTasks.get(0));
		// } else {
		// dto = new UserTaskDTO();
		// dto.setRemainPoint(taskDefine.getTotalPoint());
		// dto.setGainPoint(0);
		// dto.setTaskStatus(0);
		// dto.setUserid(userid);
		// }
		// dto.setMaxlimit(taskDefine.getMaxlimit());
		// dto.setTargetSystems(taskDefine.getTargetSystems());
		// dto.setTaskDefineName(taskDefine.getName());
		// dto.setEventType(taskDefine.getEventType());
		// dto.setTaskId(taskDefine.getId());
		// dto.setPeroid(taskDefine.getPeriod());
		// all.add(dto);
		// }
		List<TaskGroup> groups = this.taskGroupRepository.findAll();
		List<UserTask> tasks = userTaskRepository.findByUseridAndValidateToGreaterThanAndGroupidIsNotNull(userid,
				ZonedDateTime.now());
		Map<String, TaskGroup> groupids = new HashMap<>();
		for(UserTask task : tasks) {
			groupids.put(task.getGroupid(), null);
		}
		
		for(TaskGroup taskGroup : groups) {
			if(!groupids.containsKey(String.valueOf(taskGroup.getId()))) {
				// create new one
				UserTask userTask = new UserTask();
				userTask.setGainPoint(0);
				userTask.setRemainPoint(taskGroup.getMaxPoints());
				userTask.setTask(null);
				userTask.setTaskStatus(0);
				userTask.setUserid(userid);
				userTask.setGroupName(taskGroup.getGroupName());
				userTask.setGroupid(String.valueOf(taskGroup.getId()));
				
				// By default is Daily period
				userTask.setValidateTo(ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1));
				userTask.setCreateBy(MODIFIED_BY);
				
				userTask.setCreateTime(ZonedDateTime.now());
				userTaskRepository.save(userTask);
				tasks.add(userTask);
			}
		}
		
		List<UserTaskDTO> dtos = userTaskMapper.toDto(tasks);
		
		return dtos;
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
