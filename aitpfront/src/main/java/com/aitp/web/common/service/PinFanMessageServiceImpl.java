package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.ActivityMessageDTO;
import com.aitp.web.common.service.dto.AttendeeDTO;
import com.aitp.web.common.service.dto.PinFanActivityMessageDTO;
import com.aitp.web.common.service.dto.WechatMessageData;
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PinFanMessageServiceImpl implements PinFanMessageService{

    Logger logger = LoggerFactory.getLogger(PinFanMessageServiceImpl.class);

    @Value("${wechat.messageTemp.Cancel.id}")
    private String cancelTempId;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public boolean sendUpdateMessage(String id) {
        PinFanActivityMessageDTO dto = getActivityByIdFromAPI(id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null!=dto.getAttendees()){
             (AttendeeDTO attendeeDTO:dto.getAttendees()) {
                final String restApiPath=env.getProperty("rest_api_url");
                JSONObject userData = userService.getUserByWechatUserId(restApiPath,attendeeDTO.getWechatUserId());
                if(null!= userData){
                    ActivityMessageDTO messageDTO = new ActivityMessageDTO();
                    messageDTO.addMessageData(new WechatMessageData("first", "你报名的小邀约已经被修改！", "#000000"));
                    messageDTO.addMessageData(new WechatMessageData("keyword1",HttpUtil.baseDecoder(userData.getString("nikeName")) ,"#000000"));
                    messageDTO.addMessageData(new WechatMessageData("keyword2", simpleDateFormat.format(new Date()), #000000));
                    messageDTO.setTouser(userData.getString("openId"));
                    boolean flag = messageService.SendMessage(messageDTO);
                    if (!flag){
                        logger.debug("send message to {} failed",userData.getString("nickName"));
                    }
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
                    List<WechatMessageData> data = new ArrayList<>();
                    StringBuffer contex = new StringBuffer();
                    contex.append("您所报名的小邀约").append(dto.getActivitiyTile()).append("已被取消");
                    WechatMessageData data1 = new WechatMessageData("first",contex.toString(),"#FFFFFF");
                    WechatMessageData data2 = new WechatMessageData("keyword1",dto.getActivitiyTile(),"#FFFFFF");
                    WechatMessageData data3 = new WechatMessageData("keyword2",dto.getAppointDatetime(),"#FFFFFF");
                    WechatMessageData data4 = new WechatMessageData("keyword3",dto.getActivitiyAddre(),"#FFFFFF");
                    WechatMessageData data5 = new WechatMessageData("remark","请注意查看活动最新动态","#FFFFFF");
                    data.add(data1);
                    data.add(data2);
                    data.add(data3);
                    data.add(data4);
                    data.add(data5);
                    messageDTO.setTouser(userData.getString("openId"));
                    messageDTO.setTemplateID(cancelTempId);
                    boolean flag = messageService.SendMessage(messageDTO);
                    if (!flag){
                        logger.debug("send message to {} failed",userData.getString("nickName"));
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean sendRemindMessage(PinFanActivityMessageDTO dto) {
        if (null!=dto.getAttendees()){
/*            for (AttendeeDTO attendeeDTO:dto.getAttendees()) {
                final String restApiPath=env.getProperty("rest_api_url");
                JSONObject userData = userService.getUserByWechatUserId(restApiPath,attendeeDTO.getWechatUserId());
                if(null!= userData){
                    ActivityMessageDTO messageDTO = new ActivityMessageDTO();
                    messageDTO.setAction("小邀约明天即将开始");
                    messageDTO.setTitle(dto.getActivitiyTile());
                    messageDTO.setTouser(userData.getString("openId"));
                    messageService.SendMessage(messageDTO);
                    boolean flag = messageService.SendMessage(messageDTO);
                    if (!flag){
                        logger.debug("send message to {} failed",userData.getString("nickName"));
                    }
                }
            }*/
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
