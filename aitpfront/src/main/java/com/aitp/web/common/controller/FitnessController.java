package com.aitp.web.common.controller;

import com.aitp.web.common.service.FitnessMessageService;
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
@RequestMapping("/fitness")
public class FitnessController {

    Logger logger = LoggerFactory.getLogger(FitnessController.class);

    @Autowired
    private FitnessMessageService fitnessMessageService;

    @RequestMapping("/sendFitnessMessage")
    public String sendFitnessMessage(@RequestParam("id") String id, @RequestParam("state") String state){
        logger.info("request to send fitness message for activity id:{} and status: {}",id,state);
        if (StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(state)){
            if ("update".equals(state.toLowerCase())){
                fitnessMessageService.sendUpdateMessage(id);
            }
        }
        return "ok";
    }
}
