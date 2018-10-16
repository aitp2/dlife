package com.aitp.dlife.web.rest;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.request.ClockInRequest;
import com.aitp.dlife.response.ClockInActivityResponse;
import com.aitp.dlife.response.ClockInResponse;
import com.aitp.dlife.service.ClockInActivityService;
import com.aitp.dlife.service.TaskEngineService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ClockInActivityResource controller
 */
@Api(value = "小目标打卡API", tags = "小目标打卡API")
@RestController
@RequestMapping("/api/clock-in-activity")
public class ClockInActivityResource {

	private final Logger log = LoggerFactory.getLogger(ClockInActivityResource.class);

	private static final String ENTITY_NAME = "clock-in-activity";

	@Autowired
	private ClockInActivityService clockInActivityService;
	
	@Autowired
	private TaskEngineService taskEngineService;

	@PostMapping("/clock-ins")
	@ApiOperation(value = "用户打卡", response = ClockInResponse.class, produces = "application/json")
	@Timed
	public ClockInResponse createClockIn(@Valid @RequestBody ClockInRequest request) {
		log.debug("REST request to save ClockIn : {}", request);
		ClockInResponse response = new ClockInResponse();
		if (clockInActivityService.isClockedIn(request.getWechatUserId(), request.getActivityParticipationId())) {
			throw new BadRequestAlertException("A new clockIn cannot already have clockin today", ENTITY_NAME,
					"请不要重复打卡哦");
		}
		response.setClockInSuccess(clockInActivityService.clockIn(request));
		if(CollectionUtils.isEmpty(request.getPics()))
		{
			taskEngineService.saveNewEventWithComment(request.getWechatUserId(), "打卡", PointEventType.CARD, CommentChannel.FIT.toString(), request.getActivityParticipationId());
		}
		else
		{
			taskEngineService.saveNewEventWithComment(request.getWechatUserId(), "打卡", PointEventType.CARDWITHIMAGE, CommentChannel.FIT.toString(), request.getActivityParticipationId());
		}
		return response;
	}


	@GetMapping("/clock-in-result/{activityParticipationId}")
	@ApiOperation(value = "查询活动结束用户的打卡情况", response = ClockInActivityResponse.class, produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "activityParticipationId", value = "用户参与活动的Id", required = true) })
	@Timed
	public ClockInActivityResponse getClockInResultByActivityParticipationId(
			@PathVariable("activityParticipationId") Long activityParticipationId) {
		log.debug("REST request to getClockInResultByActivityParticipationId : {}", activityParticipationId);
		ClockInActivityResponse res = new ClockInActivityResponse();
		res = clockInActivityService.getClockInResultByActivityParticipationId(activityParticipationId);
		return res;
	}
	
	@GetMapping("/clock-in-result/{wechatUserId}/{activityId}")
	@ApiOperation(value = "查询活动结束用户的打卡情况", response = ClockInActivityResponse.class, produces = "application/json")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "wechatUserId", value = "用户Id", required = true),
		@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "activityId", value = "活动Id", required = true) })
	@Timed
	public ClockInActivityResponse getClockInResultByWechatUserIdAndActivityId(
			@PathVariable("wechatUserId") String wechatUserId, @PathVariable("activityId") Long activityId) {
		log.debug("REST request to getClockInResultByWechatUserIdAndActivityId : {},{}", wechatUserId,activityId);
		ClockInActivityResponse res = new ClockInActivityResponse();
		res = clockInActivityService.getClockInResultByWechatUserIdAndActivityId(wechatUserId,activityId);
		return res;
	}

}
