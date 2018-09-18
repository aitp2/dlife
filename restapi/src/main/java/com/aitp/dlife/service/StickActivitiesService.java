package com.aitp.dlife.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.repository.StickActivitiesRepository;
import com.aitp.dlife.response.StickActivitiesResponse;

@Service
@Transactional
public class StickActivitiesService {

	private final Logger log = LoggerFactory.getLogger(StickActivitiesService.class);

	@Autowired
	private StickActivitiesRepository stickActivitiesRepository;

	@Transactional(readOnly = true)
	public List<StickActivitiesResponse> getStickActivities() {
		return stickActivitiesRepository.getStickActivities();
	}

}
