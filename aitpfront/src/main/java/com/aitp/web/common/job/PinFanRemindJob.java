package com.aitp.web.common.job;


import com.aitp.web.common.service.PinFanMessageService;
import com.aitp.web.common.service.UserService;
import com.aitp.web.common.service.dto.PinFanActivityMessageDTO;
import com.aitp.web.common.service.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class PinFanRemindJob {

	@Autowired
	private Environment env;

	@Autowired
	private PinFanMessageService pinFanMessageService;

	@Autowired
	private UserService userService;


	@Scheduled(cron="${cron.pinfan.remind}")
	public void start(){

		List<PinFanActivityMessageDTO> dtos = getActivityByStartTimeFromAPI();
		if (!CollectionUtils.isEmpty(dtos)){
			for(PinFanActivityMessageDTO dto:dtos){
				pinFanMessageService.sendRemindMessage(dto);
			}
		}

	}

	private List<PinFanActivityMessageDTO> getActivityByStartTimeFromAPI(){
		String token = userService.getAccessTokenForAdmin();
		final String restApiPath=env.getProperty("rest_api_url");
		String activityInfos = HttpUtil.doGetJson(restApiPath+"/pin-fan-activities/getActivityForTomorrow",token);
		Type listType = new TypeToken<ArrayList<PinFanActivityMessageDTO>>(){}.getType();
		Gson gson = new Gson();
		return gson.fromJson(activityInfos,listType);
	}
	
	
}
