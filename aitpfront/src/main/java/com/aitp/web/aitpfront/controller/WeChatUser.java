package com.aitp.web.aitpfront.controller;

import com.aitp.web.aitpfront.service.dto.WechatUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
public class WeChatUser {
    Logger logger = LoggerFactory.getLogger(WeChatUser.class);
    @Autowired
    private Environment env;

    @RequestMapping("/login.html")
    public String toLogin(){

        return "login";
    }
    @RequestMapping("/wechat_user.html")
    public WechatUserDTO toAccept(@RequestParam("code") String code, @RequestParam("state") String state){
        logger.info("--------------------------");
        logger.info(MessageFormat.format(env.getProperty("wechat_access_token_url"),"111","222","333"));
        logger.info(code);
        logger.info(state);
        logger.info("--------------------------");
        WechatUserDTO wechatUserDTO=new WechatUserDTO();
        wechatUserDTO.setUnionid("9999999999999");
        wechatUserDTO.setUserName("Tom");
        wechatUserDTO.setCity("China");
        return wechatUserDTO;
    }

}
