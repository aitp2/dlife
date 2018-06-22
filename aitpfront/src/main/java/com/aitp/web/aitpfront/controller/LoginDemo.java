package com.aitp.web.aitpfront.controller;

import com.aitp.web.aitpfront.service.beans.AuthInfo;
import com.aitp.web.aitpfront.service.beans.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginDemo {
    Logger logger = LoggerFactory.getLogger(LoginDemo.class);
    @Autowired
    private Environment env;

    @RequestMapping("/home.html")
    public String toLogin(@RequestParam("code") String code, @RequestParam("state") String state, @ModelAttribute Code codeInfo){
        codeInfo.setCode(code);
        codeInfo.setState(state);
        return "home";
    }
    @RequestMapping("/login.html")
    public String toLogin(){
        return "login";
    }


}
