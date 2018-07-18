package com.aitp.web.common.job;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aitp.web.common.service.ActivityParticipationService;
import com.aitp.web.common.service.MessageService;
import com.aitp.web.common.service.UserService;
import com.aitp.web.common.service.dto.ActivityMessageDTO;
import com.aitp.web.common.service.dto.ActivityParticipationDTO;
import com.alibaba.fastjson.JSONObject;

@Component
public class FitnessRemindJob {

	@Value("${rest_api_url}")
	private String restApiUrl;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActivityParticipationService activityParticipationService;

	@Scheduled(cron="${cron.fitness.remind}")
	public void start(){
		List<ActivityParticipationDTO> activityList = activityParticipationService.getNonClockParticipation();
		for (ActivityParticipationDTO activityParticipationDTO : activityList) {
			ActivityMessageDTO activityMessageDTO = new ActivityMessageDTO();
			JSONObject userData =  userService.getUserByWechatUserId(restApiUrl, activityParticipationDTO.getWechatUserId());
			activityMessageDTO.setTouser(userData.getString("openId"));
			activityMessageDTO.setTitle(activityParticipationDTO.getActivityTitle());
			activityMessageDTO.setAction("小目标今天还未打卡！");
			messageService.SendMessage(activityMessageDTO);
		}
	}
	
	
	
	
}
