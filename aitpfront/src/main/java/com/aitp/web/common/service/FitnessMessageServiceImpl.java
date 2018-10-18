package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.*;
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FitnessMessageServiceImpl implements FitnessMessageService{


    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public boolean sendUpdateMessage(String id) {
        FitnessMessageDTO dto = getFitnessByIdFromAPI(id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null!=dto.getTitle()){
            List<ActivityParticipationDTO> dtos  = getActivityParticipationByActivityIdFromAPI(id);
            for (ActivityParticipationDTO activityParticipationDTO:dtos) {
                final String restApiPath=env.getProperty("rest_api_url");
                JSONObject userData = userService.getUserByWechatUserId(restApiPath,activityParticipationDTO.getWechatUserId());
                if(null!= userData){
                    ActivityMessageDTO messageDTO = new ActivityMessageDTO();
                    messageDTO.setTemplateID(env.getProperty("wechat.messageTemp.update.id"));
                    final String frontUrl = env.getProperty("service.url")+"pages/fit/index.html/detail/"+id;
                    messageDTO.setUrl(frontUrl);
                    messageDTO.addMessageData(new WechatMessageData("first", "你报名的小目标已经被修改！", "#000000"));
                    messageDTO.addMessageData(new WechatMessageData("keyword1",dto.getTitle() ,"#000000"));
                    messageDTO.addMessageData(new WechatMessageData("keyword2", simpleDateFormat.format(new Date()), "#A4D3EE"));
                    messageDTO.addMessageData(new WechatMessageData("remark", "", "#000000"));
                    messageDTO.setTouser(userData.getString("openId"));
                    messageService.SendMessage(messageDTO);
                }
            }
            return true;
        }

        return false;
    }

    private FitnessMessageDTO getFitnessByIdFromAPI(String id){
        String token = userService.getAccessTokenForAdmin();
        final String restApiPath=env.getProperty("rest_api_url");
        FitnessMessageDTO fitnessMessageDTO = new FitnessMessageDTO();
        String fitnessInfo = HttpUtil.doGetJson(restApiPath+"/fitness-activities/"+id,token);
        if (StringUtils.isNotBlank(fitnessInfo)){
            Gson gson = new Gson();
            fitnessMessageDTO = gson.fromJson(fitnessInfo,FitnessMessageDTO.class);
        }
        return fitnessMessageDTO;
    }

    public List<ActivityParticipationDTO> getActivityParticipationByActivityIdFromAPI(String id){
        String token = userService.getAccessTokenForAdmin();
        final String restApiPath=env.getProperty("rest_api_url");
        List<ActivityParticipationDTO> dtos = new ArrayList<>();
        String participationInfo = HttpUtil.doGetJson(restApiPath+"/activity-participations/getParticipationsByActivityId?activityId="+id,token);
        if (StringUtils.isNotBlank(participationInfo)){
            Type listType = new TypeToken<ArrayList<ActivityParticipationDTO>>(){}.getType();
            Gson gson = new Gson();
            dtos = gson.fromJson(participationInfo,listType);
        }
        return dtos;
    }


}
