package com.aitp.dlife.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.response.ParticipationSummaryResponse;
import com.aitp.dlife.response.StickActivitiesResponse;
import com.aitp.dlife.service.StickActivitiesService;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ClockInActivityResource controller
 */
@Api(value = "大平台", tags = "大平台API")
@RestController
@RequestMapping("/api/dlife")
public class DlifeResource {

	private final Logger log = LoggerFactory.getLogger(DlifeResource.class);


	@Autowired
	private StickActivitiesService stickActivitiesService;



	@GetMapping("/stick/activities")
	@ApiOperation(value = "查询置顶活动", produces = "application/json")
	@Timed
	public List<StickActivitiesResponse> getStickActivities() {
		log.debug("REST request to getStickActivities");
		return stickActivitiesService.getStickActivities();
	}

}
