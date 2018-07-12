package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.ActivityMessageDTO;
import com.aitp.web.common.service.dto.AttendeeDTO;
import com.aitp.web.common.service.dto.PinFanActivityMessageDTO;
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PinFanMessageServiceImpl implements PinFanMessageService{


    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public boolean sendUpdateMessage(String id) {
        PinFanActivityMessageDTO dto = getActivityByIdFromAPI(id);
        if (null!=dto.getAttendees()){
            for (AttendeeDTO attendeeDTO:dto.getAttendees()) {
                final String restApiPath=env.getProperty("rest_api_url");
                JSONObject userData = userService.getUserByWechatUserId(restApiPath,attendeeDTO.getWechatUserId());
                if(null!= userData){
                    ActivityMessageDTO messageDTO = new ActivityMessageDTO();
                    messageDTO.setAction("已被发起人修改");
                    messageDTO.setTitle(dto.getActivitiyTile());
                    messageDTO.setTouser(userData.getString("openId"));
                    messageService.SendMessage(messageDTO);
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean sendCancelMessage(String id) {
        PinFanActivityMessageDTO dto = getActivityByIdFromAPI(id);
        if (null!=dto.getAttendees()){
            for (AttendeeDTO attendeeDTO:dto.getAttendees()) {
                final String restApiPath=env.getProperty("rest_api_url");
                JSONObject userData = userService.getUserByWechatUserId(restApiPath,attendeeDTO.getWechatUserId());
                if(null!= userData){
                    ActivityMessageDTO messageDTO = new ActivityMessageDTO();
                    messageDTO.setAction("小邀约已被发起人取消");
                    messageDTO.setTitle(dto.getActivitiyTile());
                    messageDTO.setTouser(userData.getString("openId"));
                    messageService.SendMessage(messageDTO);
                }
            }
            return true;
        }
        return false;
    }


    private PinFanActivityMessageDTO getActivityByIdFromAPI(String id){
        final String restApiPath=env.getProperty("rest_api_url");
        PinFanActivityMessageDTO pinFanActivityMessageDTO = new PinFanActivityMessageDTO();
        String pinfanInfo = HttpUtil.doGetJson(restApiPath+"/pin-fan-activities/"+id);
        if (StringUtils.isNotBlank(pinfanInfo)){
            Gson gson = new Gson();
            pinFanActivityMessageDTO = gson.fromJson(pinfanInfo,PinFanActivityMessageDTO.class);
        }
        return pinFanActivityMessageDTO;
    }


}
