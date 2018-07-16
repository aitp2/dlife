package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.*;
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        if (null!=dto.getTitle()){
            List<ActivityParticipationDTO> dtos  = getActivityParticipationByActivityIdFromAPI(id);
            for (ActivityParticipationDTO activityParticipationDTO:dtos) {
                final String restApiPath=env.getProperty("rest_api_url");
                JSONObject userData = userService.getUserByWechatUserId(restApiPath,activityParticipationDTO.getWechatUserId());
                if(null!= userData){
                    ActivityMessageDTO messageDTO = new ActivityMessageDTO();
                    messageDTO.setAction("小目标已被发起人修改");
                    messageDTO.setTitle(dto.getTitle());
                    messageDTO.setTouser(userData.getString("openId"));
                    messageService.SendMessage(messageDTO);
                }
            }
            return true;
        }

        return false;
    }

    private FitnessMessageDTO getFitnessByIdFromAPI(String id){
        final String restApiPath=env.getProperty("rest_api_url");
        FitnessMessageDTO fitnessMessageDTO = new FitnessMessageDTO();
        String fitnessInfo = HttpUtil.doGetJson(restApiPath+"/fitness-activities/"+id);
        if (StringUtils.isNotBlank(fitnessInfo)){
            Gson gson = new Gson();
            fitnessMessageDTO = gson.fromJson(fitnessInfo,FitnessMessageDTO.class);
        }
        return fitnessMessageDTO;
    }

    public List<ActivityParticipationDTO> getActivityParticipationByActivityIdFromAPI(String id){
        final String restApiPath=env.getProperty("rest_api_url");
        List<ActivityParticipationDTO> dtos = new ArrayList<>();
        String participationInfo = HttpUtil.doGetJson(restApiPath+"/fitness-activities/"+id);
        if (StringUtils.isNotBlank(participationInfo)){
            Type listType = new TypeToken<ArrayList<ActivityParticipationDTO>>(){}.getType();
            Gson gson = new Gson();
            dtos = gson.fromJson(participationInfo,listType);
        }
        return dtos;
    }


}