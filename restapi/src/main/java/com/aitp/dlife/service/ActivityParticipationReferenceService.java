package com.aitp.dlife.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.ActivityParticipation;
import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.repository.ActivityParticipationRepository;
import com.aitp.dlife.response.ParticipationSummaryResponse;
import com.aitp.dlife.web.rest.util.DateUtil;

@Service
@Transactional
public class ActivityParticipationReferenceService {

	private final Logger log = LoggerFactory.getLogger(ActivityParticipationReferenceService.class);

	@Autowired
	private ActivityParticipationRepository activityParticipationRepository;

	/**
	 * 查询用户参与活动情况
	 * 
	 * @param wechatUserId
	 * @param activityId
	 * @return
	 */
	@Transactional(readOnly = true)
	public ParticipationSummaryResponse getParticipationSummaryByWechatUserIdAndActivityId(String wechatUserId,
			Long activityId) {
		ParticipationSummaryResponse response = new ParticipationSummaryResponse();
		ActivityParticipation activityParticipationEntity = activityParticipationRepository
				.findByUidAndActivityId(activityId, wechatUserId);
		if (null != activityParticipationEntity) {
			populateParticipationSummary(response, activityParticipationEntity);
		} else {
			log.error("can not find activityParticipation info,wechatUserId:{},activityId{}：", wechatUserId,
					activityId);
		}
		return response;
	}

	/**
	 * entity -> response
	 * 
	 * @param response
	 * @param activityParticipationEntity
	 */
	private void populateParticipationSummary(ParticipationSummaryResponse response,
			ActivityParticipation activityParticipationEntity) {
		response.setTotalParticipateDays(getTotalParticipateDays(activityParticipationEntity));
		response.setTotalClockInDays(
				Optional.ofNullable(activityParticipationEntity.getTotalClockinDays()).orElse(0) + "");
		response.setCurrentContinueDays(
				Optional.ofNullable(activityParticipationEntity.getCurrentContinueDays()).orElse(0) + "");
		response.setLongestContinueDays(
				Optional.ofNullable(activityParticipationEntity.getLongestContinueDays()).orElse(0) + "");
	}

	/**
	 * 总共加入天数
	 * 
	 * @param activityParticipationEntity
	 * @return
	 */
	private String getTotalParticipateDays(ActivityParticipation activityParticipationEntity) {
		FitnessActivity fitnessActivity = activityParticipationEntity.getFitnessActivity();
		Instant endTime = fitnessActivity.getActivityEndTime().toEpochMilli() < Instant.now().toEpochMilli()
				? activityParticipationEntity.getFitnessActivity().getActivityEndTime()
				: Instant.now();
		int totalParticipateDays = DateUtil.differentDays(Date.from(fitnessActivity.getActivityStartTime()),
				Date.from(endTime)) + 1;
		return String.valueOf(totalParticipateDays);
	}

}
