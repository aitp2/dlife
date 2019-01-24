package com.aitp.dlife.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.aitp.dlife.service.dto.EventMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.aitp.dlife.domain.ActivityParticipation;
import com.aitp.dlife.domain.ClockIn;
import com.aitp.dlife.domain.ClockinSummary;
import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.domain.Pics;
import com.aitp.dlife.domain.WechatUser;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.repository.ActivityParticipationRepository;
import com.aitp.dlife.repository.ClockInRepository;
import com.aitp.dlife.repository.ClockinSummaryRepository;
import com.aitp.dlife.repository.PicsRepository;
import com.aitp.dlife.repository.WechatUserRepository;
import com.aitp.dlife.request.ClockInRequest;
import com.aitp.dlife.request.PicsRequest;
import com.aitp.dlife.response.ClockInActivityResponse;
import com.aitp.dlife.service.mapper.ActivityParticipationMapper;
import com.aitp.dlife.service.mapper.ClockInMapper;
import com.aitp.dlife.service.mapper.InstantMapper;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HttpUtil;

@Service
@Transactional
public class ClockInActivityService {

	private final Logger log = LoggerFactory.getLogger(ClockInActivityService.class);

	@Autowired
	private ActivityParticipationRepository activityParticipationRepository;
	@Autowired
	private ClockInRepository clockInRepository;
	@Autowired
	private ClockinSummaryRepository clockinSummaryRepository;
	@Autowired
	private PicsRepository picsRepository;
	@Autowired
	private WechatUserRepository wechatUserRepository;
	@Autowired
	private ActivityParticipationMapper activityParticipationMapper;
	@Autowired
	private ClockInMapper clockInMapper;
	@Autowired
	private EventMessageService eventMessageService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PointService pointService;

	/**
	 * 用户打卡
	 *
	 * @param request
	 * @return
	 */
	public boolean clockIn(ClockInRequest request) {
		// 保存打卡记录
		log.debug("start to saveClockInRecord");
		saveClockInRecord(request);
		// save the event message
		log.debug("保存Event message记录");
		saveEnventMessage(request);
		// 更新打卡记录汇总
		log.debug("start to updateClockInSummary");
		updateClockInSummary(request);
		// 冗余打卡信息
		log.debug("start to updateActivityParticipation");
		updateActivityParticipation(request.getActivityParticipationId());
		// log for markting start
		log.debug("start to saveLogForMarking");
		saveLogForMarking(request);
		// log for markting end
		return true;
	}

	protected void saveEnventMessage(ClockInRequest request) {

		Optional<ActivityParticipation> activityParticipation = activityParticipationRepository
				.findById(request.getActivityParticipationId());

		if (activityParticipation.isPresent()) {
			// record the activity participation event start
            EventMessageDTO eventMessageDTO = eventMessageService.recordEventMessage(EventChannel.FITNESS, DateUtil.getYMDDateString(new Date()),
					EventType.CLOCKIN, request.getWechatUserId(),
					activityParticipation.get().getFitnessActivity().getTitle(),
					activityParticipation.get().getFitnessActivity().getId(), activityParticipation.get().getAvatar(),
					activityParticipation.get().getNickName(),request.getSignNote(),null);
            if (null!=eventMessageDTO.getId()){
                messageService.createMessageForEvent(eventMessageDTO);
            }
			// record the activity participation event end
		}

	}

	/**
	 * 根据用户参与活动关系查询活动完成用户的打卡情况
	 *
	 * @param activityParticipationId
	 * @return
	 */
	public ClockInActivityResponse getClockInResultByActivityParticipationId(Long activityParticipationId) {
		ClockInActivityResponse clockInActivityResponse = new ClockInActivityResponse();
		activityParticipationRepository.findById(activityParticipationId)
				.ifPresent(e -> populateClockInResult(clockInActivityResponse, e));
		return clockInActivityResponse;
	}

	/**
	 * 根据用户Id 活动Id 查询活动完成用户的打卡情况
	 *
	 * @param wechatUserId
	 * @param activityId
	 * @return
	 */
	public ClockInActivityResponse getClockInResultByWechatUserIdAndActivityId(String wechatUserId, Long activityId) {
		ClockInActivityResponse clockInActivityResponse = new ClockInActivityResponse();
		ActivityParticipation activityParticipationEntity = activityParticipationRepository
				.findByUidAndActivityId(activityId, wechatUserId);
		if (null != activityParticipationEntity) {
			populateClockInResult(clockInActivityResponse, activityParticipationEntity);

		} else {
			log.error("can not find activityParticipation info,wechatUserId:{},activityId{}：", wechatUserId,
					activityId);
		}

		return clockInActivityResponse;
	}

	/**
	 * 转换打卡结果
	 *
	 * @param clockInActivityResponse
	 * @param activityParticipationEntity
	 */
	private void populateClockInResult(ClockInActivityResponse clockInActivityResponse,
			ActivityParticipation activityParticipationEntity) {
		// 活动是否完成
		boolean completed = isCompleted(activityParticipationEntity);
		log.debug("活动是否已经完成：" + completed);
		if (completed) {
			// 最早打卡时间
			String earliestClockInTimeStr = getEarliestClockInTimeStr(activityParticipationEntity);
			// 最晚打卡时间
			String latestClockInTimeStr = getLatestClockInTimeStr(activityParticipationEntity);
			// 最长连续打卡
			String longestContinueDaysStr = String.valueOf(activityParticipationEntity.getLongestContinueDays());
			// 总共打卡天数
			String totalClockinDaysStr = String.valueOf(activityParticipationEntity.getTotalClockinDays());
			// 打卡排名
			String rankingStr = getRanking(activityParticipationEntity);
			clockInActivityResponse.setEarliestClockInTime(earliestClockInTimeStr);
			clockInActivityResponse.setLatestClockInTime(latestClockInTimeStr);
			clockInActivityResponse.setLongestContinueDays(longestContinueDaysStr);
			clockInActivityResponse.setTotalClockInDays(totalClockinDaysStr);
			clockInActivityResponse.setRanking(rankingStr);
		}
		clockInActivityResponse.setCompleted(completed);
	}

	/**
	 * @param request
	 */
	private void saveLogForMarking(ClockInRequest request) {
		Optional<WechatUser> wechatUser = wechatUserRepository.findById(Long.valueOf(request.getWechatUserId()));
		if (wechatUser.isPresent()) {
			WechatUser wechatUserEntity = wechatUser.get();
			String sexString = "";
			if (null != wechatUserEntity && null != wechatUserEntity.getSex()) {
				Integer sex = wechatUserEntity.getSex();
				if (sex == 1) {
					sexString = "male";
				} else if (sex == 2) {
					sexString = "female";
				} else {
					sexString = "";
				}
			}

			Optional<ActivityParticipation> activityParticipation = activityParticipationRepository
					.findById(request.getActivityParticipationId());
			FitnessActivity fitnessActivity = activityParticipation.map(ap -> ap.getFitnessActivity()).orElse(null);
			if (null != fitnessActivity) {
				log.debug(
						"module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}",
						"fit", fitnessActivity.getId(), HttpUtil.baseEncoder(fitnessActivity.getTitle()), "clock-in",
						DateUtil.getYMDDateString(new Date()), wechatUserEntity.getNickName(), sexString);
			}
		}
	}

	/**
	 * @param request
	 */
	private void updateClockInSummary(ClockInRequest request) {
		ClockinSummary clockinSummary = clockinSummaryRepository.findByWechatUserId(request.getWechatUserId());
		if (null == clockinSummary) {
			ClockinSummary insertClockinSummary = new ClockinSummary();
			insertClockinSummary.setLastClockInTime(Instant.now());
			insertClockinSummary.setWechatUserId(request.getWechatUserId());
			insertClockinSummary.setSerialCount(1);
			insertClockinSummary.setWeeklyCount(1);
			insertClockinSummary.setTotallyCount(1);
			clockinSummaryRepository.save(insertClockinSummary);
		} else if (!DateUtil
				.isToday(DateUtil.fromYDMStringDate(InstantMapper.toDateString(clockinSummary.getLastClockInTime())))) {

			ClockinSummary updateClockinSummary = new ClockinSummary();
			updateClockinSummary.setId(clockinSummary.getId());
			updateClockinSummary.setSerialCount(DateUtil.isYesterday(
					DateUtil.fromYDMStringDate(InstantMapper.toDateString(clockinSummary.getLastClockInTime())))
							? clockinSummary.getSerialCount() + 1
							: 1);
			updateClockinSummary
					.setWeeklyCount(DateUtil.isThisWeek(InstantMapper.toDateString(clockinSummary.getLastClockInTime()))
							? clockinSummary.getWeeklyCount() + 1
							: 1);
			updateClockinSummary.setTotallyCount(clockinSummary.getTotallyCount() + 1);
			updateClockinSummary.setWechatUserId(String.valueOf(request.getWechatUserId()));
			updateClockinSummary.setLastClockInTime(Instant.now());
			clockinSummaryRepository.save(updateClockinSummary);
		}
	}

	/**
	 * @param request
	 */
	private void saveClockInRecord(ClockInRequest request) {
		ClockIn clockIn = new ClockIn();
		clockIn.setActivityParticipation(activityParticipationMapper.fromId(request.getActivityParticipationId()));
		clockIn.setTitle(request.getTitle());
		clockIn.setSignNote(request.getSignNote());
		clockIn.setPunchDateTime(Instant.now());
		ActivityParticipation activityParticipation = activityParticipationRepository.getOne(request.getActivityParticipationId());
		clockIn.setActivityId(Integer.valueOf(activityParticipation.getFitnessActivity().getId().toString()));
		ClockIn clockInResult = clockInRepository.save(clockIn);
		// 保存打卡图片
		if (!CollectionUtils.isEmpty(request.getPics())) {
			for (PicsRequest picsDTO : request.getPics()) {
				Pics pics = new Pics();
				pics.setOssPath(picsDTO.getOssPath());
				pics.setClockIn(clockInMapper.fromId(clockInResult.getId()));
				pics.setCreateTime(Instant.now());
				picsRepository.save(pics);
			}
		}
		if(CollectionUtils.isEmpty(request.getPics())||StringUtils.isEmpty(request.getTitle()))
		{
			pointService.saveNewEvent(request.getWechatUserId(), "打卡", PointEventType.CARD, CommentChannel.FIT.toString(), activityParticipation.getFitnessActivity().getTitle());
		}
		else
		{
			pointService.saveNewEvent(request.getWechatUserId(), "打卡", PointEventType.CARDWITHIMAGE, CommentChannel.FIT.toString(), activityParticipation.getFitnessActivity().getTitle());
		}
	}

	/**
	 * 获取用户排名
	 *
	 * @param activityParticipationEntity
	 * @return
	 */
	private String getRanking(ActivityParticipation activityParticipationEntity) {
		FitnessActivity fitnessActivityEntity = activityParticipationEntity.getFitnessActivity();
		List<ActivityParticipation> result = new ArrayList<>(fitnessActivityEntity.getActivityParticipations());
		result.sort((b1, b2) -> b1.getClockIns().size() == b2.getClockIns().size() ? 0
				: (b1.getClockIns().size() > b2.getClockIns().size() ? -1 : 1));
		for (int i = 0; i < result.size(); i++) {
			if (activityParticipationEntity.getId() == result.get(i).getId()) {
				return i + 1 + "";
			}
		}

		return "未找到排名";
	}

	/**
	 * 获取最早打卡时间
	 *
	 * @param activityParticipationEntity
	 * @return
	 */
	private String getEarliestClockInTimeStr(ActivityParticipation activityParticipationEntity) {
		ClockIn earliestClockIn = activityParticipationEntity.getClockIns().stream()
				.min((p1, p2) -> (p1.getPunchDateTime().compareTo(p2.getPunchDateTime()))).get();
		return DateUtil.getDateString(DateUtil.YY_M_D_HH_mm, Date.from(earliestClockIn.getPunchDateTime()));
	}

	/**
	 * 获取最晚打卡时间
	 *
	 * @param activityParticipationEntity
	 * @return
	 */
	private String getLatestClockInTimeStr(ActivityParticipation activityParticipationEntity) {
		ClockIn earliestClockIn = activityParticipationEntity.getClockIns().stream()
				.max((p1, p2) -> (p1.getPunchDateTime().compareTo(p2.getPunchDateTime()))).get();
		return DateUtil.getDateString(DateUtil.YY_M_D_HH_mm, Date.from(earliestClockIn.getPunchDateTime()));
	}

	/**
	 * 活动是否完成
	 *
	 * @param activityParticipationEntity
	 * @return
	 */
	private boolean isCompleted(ActivityParticipation activityParticipationEntity) {
		FitnessActivity fitnessActivityEntity = activityParticipationEntity.getFitnessActivity();
		if (StringUtils.isEmpty(fitnessActivityEntity.getActivityEndTime())
				|| StringUtils.isEmpty(activityParticipationEntity.getLatestClockinTime())) {
			return false;
		}
		String activityEndTimeStr = DateUtil.getYYMMDDDateString(Date.from(fitnessActivityEntity.getActivityEndTime()));
		String lastClockInTimeStr = DateUtil
				.getYYMMDDDateString(Date.from(activityParticipationEntity.getLatestClockinTime()));
		return DateUtil.fromStringDate(DateUtil.YY_MM_DD, lastClockInTimeStr).getTime() >= DateUtil
				.fromStringDate(DateUtil.YY_MM_DD, activityEndTimeStr).getTime();
	}

	/**
	 * 更新用户参加活动的打卡情况
	 *
	 * @param activityParticipationId
	 */
	public void updateActivityParticipation(Long activityParticipationId) {
		Optional<ActivityParticipation> activityParticipation = activityParticipationRepository
				.findById(activityParticipationId);
		if (activityParticipation.isPresent()) {
			ActivityParticipation activityParticipationEntity = activityParticipation.get();
			// 总打卡天数
			Integer totalClockInDays = getTotalClockInCount(activityParticipationEntity);
			// 当前连续天数
			Integer currentContinueDays = getCurrentContinueDays(activityParticipationEntity);
			// 最长连续天数
			Integer longestContinueDays = getLongestContinueDays(activityParticipationEntity, currentContinueDays);

			activityParticipationEntity.setTotalClockinDays(totalClockInDays);
			activityParticipationEntity.setCurrentContinueDays(currentContinueDays);
			activityParticipationEntity.setLongestContinueDays(longestContinueDays);
			activityParticipationEntity.setLatestClockinTime(Instant.now());

			activityParticipationRepository.save(activityParticipationEntity);
		} else {
			log.error("do not find activityParticipation by activityParticipationId：" + activityParticipationId);
		}

	}

	/**
	 * 获取总打卡天数
	 *
	 * @param activityParticipationEntity
	 * @return
	 */
	private Integer getTotalClockInCount(ActivityParticipation activityParticipationEntity) {
		Integer totalClockInCount = null == activityParticipationEntity.getTotalClockinDays() ? 1
				: activityParticipationEntity.getTotalClockinDays() + 1;
		return totalClockInCount;
	}

	/**
	 * 当前连续打卡天数
	 *
	 * @param activityParticipationEntity
	 * @return
	 */
	private Integer getCurrentContinueDays(ActivityParticipation activityParticipationEntity) {
		Integer currentContinueDays = null == activityParticipationEntity.getCurrentContinueDays() ? 1
				: activityParticipationEntity.getCurrentContinueDays();
		// 不是今天
		if (null != activityParticipationEntity.getLatestClockinTime()
				&& !DateUtil.isToday(Date.from(activityParticipationEntity.getLatestClockinTime()))) {
			// 打卡时间是昨天，连续打卡天数加1
			if (DateUtil.isYesterday(Date.from(activityParticipationEntity.getLatestClockinTime()))) {
				currentContinueDays = currentContinueDays + 1;
			} else {
				// 打卡时间不是昨天，重置连续打卡天数
				currentContinueDays = 1;
			}
		}
		return currentContinueDays;
	}

	/**
	 * 最长连续天数
	 *
	 * @param activityParticipationEntity
	 * @param currentContinueDays
	 * @return
	 */
	private Integer getLongestContinueDays(ActivityParticipation activityParticipationEntity,
			Integer currentContinueDays) {
		Integer longestContinueDays = null == activityParticipationEntity.getLongestContinueDays() ? 1
				: activityParticipationEntity.getLongestContinueDays();
		if (currentContinueDays > longestContinueDays) {
			return currentContinueDays;
		} else {
			return longestContinueDays;
		}
	}

	/**
	 * 判断用户今天是否打过卡
	 *
	 * @param wechatUserId
	 * @param activityParticipationId
	 * @return true -> 打过卡 false -> 未打过卡
	 */
	public boolean isClockedIn(String wechatUserId, Long activityParticipationId) {
		String today = DateUtil.getYYMMDDDateString(new Date());
		List<ClockIn> clockInResults = clockInRepository.getClockinsByWechatUserIdAndDateAndActivityId(wechatUserId,
				activityParticipationId, today);
		if (CollectionUtils.isEmpty(clockInResults)) {
			return false;
		}
		return true;
	}

}
