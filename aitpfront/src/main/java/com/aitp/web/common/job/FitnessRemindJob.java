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
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;

@Component
public class FitnessRemindJob {

	@Value("${rest_api_url}")
	private String restApiUrl;
	

	@Value("${wechat.messageTemp.clock.id}")
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
		if(!activityList.isEmpty()){
			ActivityMessageDTO activityMessageDTO = new ActivityMessageDTO();
			JSONObject userData =  userService.getUserByWechatUserId(restApiUrl, activityList.get(0).getWechatUserId());
			buildMessage(activityList.get(0),activityMessageDTO,userData.getString("openId"),activityList.size());
			messageService.SendMessage(activityMessageDTO);
		}
//		for (ActivityParticipationDTO activityParticipationDTO : activityList) {
//			ActivityMessageDTO activityMessageDTO = new ActivityMessageDTO();
//			JSONObject userData =  userService.getUserByWechatUserId(restApiUrl, activityParticipationDTO.getWechatUserId());
//			buildMessage(activityParticipationDTO,activityMessageDTO,userData.getString("openId"));
//			messageService.SendMessage(activityMessageDTO);
//		}
	}
	
    private void buildMessage(ActivityParticipationDTO activityParticipationDTO,ActivityMessageDTO activityMessageDTO,String openId,int size
    		){
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	activityMessageDTO.setTouser(openId);
		StringBuffer context = new StringBuffer();
		context.append("您还有");
		context.append(size);
		context.append("个参与的下下小目标尚未打卡，快去我完成今日的目标吧。");
		activityMessageDTO.setTemplateID(clockTempId);
		activityMessageDTO.addMessageData(new WechatMessageData("first", context.toString(), "#000000"));
		activityMessageDTO.addMessageData(new WechatMessageData("keyword1", HttpUtil.baseDecoder(activityParticipationDTO.getNickName()), "#000000"));
		activityMessageDTO.addMessageData(new WechatMessageData("keyword2", simpleDateFormat.format(new Date()), "#A4D3EE"));
		activityMessageDTO.addMessageData(new WechatMessageData("keyword3", "未打卡", "#000000"));
    } 
	
	
}
