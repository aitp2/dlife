package com.aitp.web.aitpfront.controller;

import com.aitp.web.aitpfront.service.UserService;
import com.aitp.web.aitpfront.service.beans.AuthInfo;
import com.aitp.web.aitpfront.service.beans.Token;
import com.aitp.web.aitpfront.service.dto.WechatUserDTO;
import com.aitp.web.aitpfront.service.utils.HttpUtil;
import com.aitp.web.aitpfront.service.wechat.AuthClient;
import com.aitp.web.aitpfront.service.wechat.AuthToolNoCache;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
@CrossOrigin
@RestController
public class WeChatUser {
    Logger logger = LoggerFactory.getLogger(WeChatUser.class);
    @Autowired
    private Environment env;
    @Autowired
    AuthClient authClient;
    @Autowired
    UserService userService;

    @RequestMapping("/wechat_user.html")
    public WechatUserDTO toAccept(@RequestParam("code") String code, @RequestParam("state") String state){
        WechatUserDTO wechatUserDTO=new WechatUserDTO();
        String restApiPath=env.getProperty("rest_api_url");

        if(StringUtils.isNotBlank(code)){
            AuthInfo authInfo=new AuthInfo();
            authInfo.setAppid(env.getProperty("wechat_app_id"));
            authInfo.setAppsecret(env.getProperty("wechat_app_secret"));
            authInfo.setAccessTokenUrl(MessageFormat.format(env.getProperty("wechat_access_token_url"),authInfo.getAppid(),authInfo.getAppsecret(),code));

            Token token = authClient.loadAccessToken(authInfo);
            if(token!=null){
                if(StringUtils.isNotBlank(token.getAccess_token())&&StringUtils.isNotBlank(token.getOpenid())){
                    /**
                     * 用access token获取当前微信账号信息
                     */
                    String userInfoUrl=MessageFormat.format(env.getProperty("wechat_snsapi_userinfo_url"),token.getAccess_token(),token.getOpenid());
                    String userInfo = HttpUtil.doGetJson(userInfoUrl);
                    logger.info("userInfo:{}",userInfo);
                    if(StringUtils.isNotBlank(userInfo)){
                        JSONObject user = JSONObject.parseObject(userInfo);
                        wechatUserDTO.setUnionid(user.getString("unionid"));
                        wechatUserDTO.setUserName(user.getString("nickname"));
                        wechatUserDTO.setCity(user.getString("city"));
                        wechatUserDTO.setCountry(user.getString("country"));
                        wechatUserDTO.setHeadimgurl(user.getString("headimgurl"));
                        wechatUserDTO.setOpenId(user.getString("openid"));
                        wechatUserDTO.setPrivilege(user.getString("privilege"));
                        wechatUserDTO.setSex(user.getString("sex"));
                        wechatUserDTO.setProvince(user.getString("province"));

                        JSONObject userData = userService.getUserByOpenid(restApiPath, wechatUserDTO.getOpenId());
                        if(userData==null){//如果用户信息为空，则创建用户信息到数据库
                            JSONObject resultData = userService.createUser(restApiPath,wechatUserDTO);
                            if(resultData!=null){
                                wechatUserDTO.setUserId(resultData.getString("id"));
                            }
                        }

                    }
                }
            }
        }
        return wechatUserDTO;
    }

}
