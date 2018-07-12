package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.AttendeeDTO;
import com.aitp.web.common.service.dto.PinFanActivityMessageDTO;
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PinFanMessageServiceImpl implements PinFanMessageService{


    @Autowired
    private Environment env;

    @Override
    public boolean sendUpdateMessage(String id) {
        PinFanActivityMessageDTO dto = getActivityByIdFromAPI(id);

        return false;
    }

    @Override
    public boolean sendCancelMessage(String id) {
        return false;
    }


    private PinFanActivityMessageDTO getActivityByIdFromAPI(String id){
        final String restApiPath=env.getProperty("rest_api_url");
        String pinfanInfo = HttpUtil.doGetJson(restApiPath+"/pin-fan-activities/"+id);
        if (StringUtils.isNotBlank(pinfanInfo)){
            PinFanActivityMessageDTO dto = (PinFanActivityMessageDTO)JSONObject.parse(pinfanInfo);
            if (StringUtils.isNotBlank(pinfanInfo.get))
            AttendeeDTO attendeeDTO =
        }
        return null;
    }
}
