package com.aitp.dlife.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.response.ClockInNotesCalendarResponse;
import com.aitp.dlife.response.ClockInNotesResponse;
import com.aitp.dlife.service.ClockInNotesService;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * ClockInNotesResource controller
 */
@Api(value = "小目标打卡日记API", tags = "小目标打卡日记API")
@RestController
@RequestMapping("/api/clock-in-notes")
public class ClockInNotesResource {

	private final Logger log = LoggerFactory.getLogger(ClockInNotesResource.class);

	@Autowired
	private ClockInNotesService clockInNotesService;

	@GetMapping("/{wechatUserId}")
	@ApiOperation(value = "查询用户所有活动的打卡日记", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "wechatUserId", value = "用户id", required = true) })
	@Timed
	public List<ClockInNotesResponse> getColckInNotesByWechatUserId(Pageable pageable,
			@PathVariable("wechatUserId") String wechatUserId) {
		log.info("REST request to getColckInNotesByWechatUserId : {}", wechatUserId);
		List<ClockInNotesResponse> response = new ArrayList<ClockInNotesResponse>();
		response = clockInNotesService.getColckInNotesByWechatUserId(pageable, wechatUserId);
		return response;
	}

	@GetMapping("/{wechatUserId}/{activityId}")
	@ApiOperation(value = "查询用户指定活动的打卡日记", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "wechatUserId", value = "用户id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "activityId", value = "活动id", required = true) })
	@Timed
	public List<ClockInNotesResponse> getColckInNotesByWechatUserIdAndAcitivityId(Pageable pageable,
			@PathVariable("wechatUserId") String wechatUserId, @PathVariable("activityId") Long activityId) {
		log.info("REST request to getColckInNotesByWechatUserIdAndAcitivityId : {},{}", wechatUserId, activityId);
		List<ClockInNotesResponse> response = new ArrayList<ClockInNotesResponse>();
		response = clockInNotesService.getColckInNotesByWechatUserIdAndAcitivityId(pageable, wechatUserId, activityId);
		return response;
	}

	@ApiIgnore
	@GetMapping("/{wechatUserId}/{activityId}/{ymd}")
	@ApiOperation(value = "查询用户指定活动指定时间的打卡日记", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "wechatUserId", value = "用户id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "activityId", value = "活动id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "ymd", value = "月份(yyyy-MM-dd)", required = true) })
	@Timed
	public List<ClockInNotesResponse> getColckInNotesByWechatUserIdAndAcitivityIdAndYearMonthDay(Pageable pageable,
			@PathVariable("wechatUserId") String wechatUserId, @PathVariable("activityId") Long activityId,
			@PathVariable("ymd") String ymd) {
		log.info("REST request to getColckInNotesByWechatUserIdAndAcitivityIdAndYearMonthDay : {},{},{}", wechatUserId,
				activityId, ymd);
		List<ClockInNotesResponse> response = new ArrayList<ClockInNotesResponse>();
		response = clockInNotesService.getColckInNotesByWechatUserIdAndAcitivityIdAndYearMonthDay(wechatUserId,
				activityId, ymd);
		return response;
	}

	@GetMapping("/calendar/{wechatUserId}/{activityId}/{ym}")
	@ApiOperation(value = "查询用户指定活动指定月份的打卡日记日历", response = ClockInNotesCalendarResponse.class, produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "wechatUserId", value = "用户id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "activityId", value = "活动id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "ym", value = "月份(yyyy-MM)", required = true) })
	@Timed
	public ClockInNotesCalendarResponse getColckInCalendarByWechatUserIdAndAcitivityIdAndYearMonth(
			@PathVariable("wechatUserId") String wechatUserId, @PathVariable("activityId") Long activityId,
			@PathVariable("ym") String ym) {
		log.info("REST request to get clock-in-notes-calendar : {},{},{}", wechatUserId, activityId, ym);
		ClockInNotesCalendarResponse response = new ClockInNotesCalendarResponse();
		response.setClockInNotesCalendars(clockInNotesService
				.getColckInCalendarByWechatUserIdAndAcitivityIdAndYearMonth(wechatUserId, activityId, ym));
		return response;
	}
	
	@GetMapping("/calendar/{wechatUserId}/{activityId}")
	@ApiOperation(value = "查询用户指定活动的打卡日记日历", response = ClockInNotesCalendarResponse.class, produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "wechatUserId", value = "用户id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "activityId", value = "活动id", required = true)})
	@Timed
	public ClockInNotesCalendarResponse getColckInCalendarByWechatUserIdAndAcitivityId(
			@PathVariable("wechatUserId") String wechatUserId, @PathVariable("activityId") Long activityId) {
		log.info("REST request to get clock-in-notes-calendar : {},{}", wechatUserId, activityId);
		ClockInNotesCalendarResponse response = new ClockInNotesCalendarResponse();
		response.setClockInNotesCalendars(clockInNotesService
				.getColckInCalendarByWechatUserIdAndAcitivityId(wechatUserId, activityId));
		return response;
	}

}
