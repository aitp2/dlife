package com.aitp.dlife.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.response.ParticipationSummaryResponse;
import com.aitp.dlife.service.ActivityParticipationReferenceService;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ClockInActivityResource controller
 */
@Api(value = "小目标参与活动API", tags = "小目标参与活动API")
@RestController
@RequestMapping("/api/activity-participations")
public class ActivityParticipationReferenceResource {

	private final Logger log = LoggerFactory.getLogger(ActivityParticipationReferenceResource.class);


	@Autowired
	private ActivityParticipationReferenceService activityParticipationReferenceService;


	
	@GetMapping("/summary/{wechatUserId}/{activityId}")
	@ApiOperation(value = "查询用户参与活动情况", response = ParticipationSummaryResponse.class, produces = "application/json")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "wechatUserId", value = "用户Id", required = true),
		@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "activityId", value = "活动Id", required = true) })
	@Timed
	public ParticipationSummaryResponse getParticipationSummaryByWechatUserIdAndActivityId(
			@PathVariable("wechatUserId") String wechatUserId, @PathVariable("activityId") Long activityId) {
		log.debug("REST request to getParticipationSummaryByWechatUserIdAndActivityId : {},{}", wechatUserId,activityId);
		ParticipationSummaryResponse res = new ParticipationSummaryResponse();
		res = activityParticipationReferenceService.getParticipationSummaryByWechatUserIdAndActivityId(wechatUserId,activityId);
		return res;
	}

}
