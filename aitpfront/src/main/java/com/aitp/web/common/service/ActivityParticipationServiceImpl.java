package com.aitp.web.common.service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.aitp.web.common.service.dto.ActivityParticipationDTO;
import com.aitp.web.common.service.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ActivityParticipationServiceImpl implements ActivityParticipationService{

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;
	
	@Override
	public List<ActivityParticipationDTO> getNonClockParticipation() {
	    String token = userService.getAccessTokenForAdmin();
	   final String restApiPath=env.getProperty("rest_api_url");
	   List<ActivityParticipationDTO> activityParticipationDTOs = new ArrayList<ActivityParticipationDTO>();
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer url =  new StringBuffer().append(restApiPath).append("/activity-participations/getNonClock?clockinDate=");
		url.append(simpleDateFormat.format(new Date()));
		url.append("&isClockIn=0");
		String fitnessInfo = HttpUtil.doGetJson(url.toString(),token);
		if (StringUtils.isNotBlank(fitnessInfo)){
			Gson gson = new Gson();
			Type type = new TypeToken<ArrayList<ActivityParticipationDTO>>(){}.getType();
			activityParticipationDTOs = gson.fromJson(fitnessInfo,type);
		}
		return activityParticipationDTOs;
	}
}
