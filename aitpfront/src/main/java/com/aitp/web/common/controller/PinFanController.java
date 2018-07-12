package com.aitp.web.common.controller;

import com.aitp.web.common.service.PinFanMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/pinfan")
public class PinFanController {

    Logger logger = LoggerFactory.getLogger(PinFanController.class);

    @Autowired
    private PinFanMessageService pinFanMessageService;

    @RequestMapping("/sendPinFanMessage")
    public String sendPinFanMessage(@RequestParam("id") String id, @RequestParam("state") String state){
        if (StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(state)){
            if ("update".equals(state.toLowerCase())){
                pinFanMessageService.sendUpdateMessage(id);
            }else if ("cancel".equals(state.toLowerCase())){
                pinFanMessageService.sendCancelMessage(id);
            }
        }
        return "ok";
    }
}
