package com.aitp.web.common.controller;

import com.aitp.web.common.service.PointShopService;
import com.aitp.web.common.service.dto.ActivityMessageDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@CrossOrigin
@RestController
@RequestMapping("/pointshop")
public class PointShopController {

   private static final  Logger logger = LoggerFactory.getLogger(PointShopController.class);

    @Autowired
    private PointShopService pointShopService;

    @RequestMapping(path="/sendMessage",method=RequestMethod.POST, produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String sendMessage(@RequestBody ActivityMessageDTO message){
        logger.info("request to send message for point user:{} type:{}",message.getTouser(),message.getType());

        pointShopService.sendMessage(message);
        return "ok";
    }
}
