package com.aitp.web.common.job;


import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.aitp.web.common.service.dto.WechatMessageData;
import com.alibaba.fastjson.JSONObject;

@Component
public class FitnessRemindJob {

	@Value("${rest_api_url}")
	private String restApiUrl;
	

	@Value("${wechat.messageTemp.Clock.id}")
	private String clockTempId;

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
			buildMessage(activityParticipationDTO,activityMessageDTO,userData.getString("openId"));
			messageService.SendMessage(activityMessageDTO);
		}
	}
	
    private void buildMessage(ActivityParticipationDTO activityParticipationDTO,ActivityMessageDTO activityMessageDTO,String openId){
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	activityMessageDTO.setTouser(openId);
		StringBuffer context = new StringBuffer();
		context.append("您所报名的");
		context.append(activityParticipationDTO.getActivityTitle());
		context.append("小目标还没有打卡，请尽快打卡哦！");
		activityMessageDTO.setTemplateID(clockTempId);
		activityMessageDTO.addMessageData(new WechatMessageData("first", context.toString(), "#FFFFFF"));
		activityMessageDTO.addMessageData(new WechatMessageData("keyword1", activityParticipationDTO.getNickName(), "#FFFFFF"));
		activityMessageDTO.addMessageData(new WechatMessageData("keyword2", simpleDateFormat.format(new Date()), "#170000"));
		activityMessageDTO.addMessageData(new WechatMessageData("keyword3", "未打卡", "#FFFFFF"));
    } 
	
	
}
