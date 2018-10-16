package com.aitp.web.common.controller;

import com.aitp.web.common.service.UserService;
import com.aitp.web.common.service.beans.AuthInfo;
import com.aitp.web.common.service.beans.Ticket;
import com.aitp.web.common.service.beans.Token;
import com.aitp.web.common.service.beans.UserPointAction;
import com.aitp.web.common.service.dto.JSSDKConfigDTO;
import com.aitp.web.common.service.dto.WechatUserDTO;
import com.aitp.web.common.service.utils.HttpUtil;
import com.aitp.web.common.service.utils.Sha1;
import com.aitp.web.common.service.wechat.AuthClient;
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

import java.security.DigestException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
                    logger.info("wechatUserInfo:{}",userInfo);
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
                            logger.info("----------Create wechate user---------");
                            JSONObject resultData = userService.createUser(restApiPath,wechatUserDTO);
                            logger.info("Save user result:{}", resultData);
                            if(resultData!=null){
                                wechatUserDTO.setUserId(resultData.getString("id"));
                            }
                        }else{
                            wechatUserDTO.setUserId(userData.getString("id"));
                        }
                    	UserPointAction userPointAction=new UserPointAction();
                    	userPointAction.setEventName("登录");
                    	userPointAction.setEventType("LOGIN");
                    	userPointAction.setUuid(UUID.randomUUID().toString());
                    	userPointAction.setTargetSystem("PLATFORM");
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    	String nowDateString = sdf.format(new Date());
                    	userPointAction.setEventTime(nowDateString);
                    	userPointAction.setUserid(wechatUserDTO.getUserId());
                    	HttpUtil.doPostJson(restApiPath+"/task-engine/event/new", userPointAction);
                    }
                    logger.info("wechatUserDTO:{}",JSONObject.toJSONString(wechatUserDTO));
                }
            }
        }
        return wechatUserDTO;
    }

    @RequestMapping("/wechat_signature.html")
    public JSSDKConfigDTO toSignature(@RequestParam("url") String url){
        JSSDKConfigDTO jssdkConfigDTO=new JSSDKConfigDTO();
        AuthInfo authInfo=new AuthInfo();
        authInfo.setAppid(env.getProperty("wechat_app_id"));
        authInfo.setAppsecret(env.getProperty("wechat_app_secret"));
        authInfo.setAccessTokenUrl(MessageFormat.format(env.getProperty("wechat_token_url"),authInfo.getAppid(),authInfo.getAppsecret()));
        Token token = authClient.loadToken(authInfo);
        if (token!=null){
            if (StringUtils.isNotBlank(token.getAccess_token())){
                authInfo.setJsapiTicketUrl(MessageFormat.format(env.getProperty("wechat_jsapi_ticket_url"),token.getAccess_token()));
                Ticket ticket = authClient.loadTicket(authInfo);

                String noncestr = UUID.randomUUID().toString();
                String timestamp = Long.toString(System.currentTimeMillis() / 1000);

                jssdkConfigDTO.setNonceStr(noncestr);
                jssdkConfigDTO.setTimestamp(timestamp);
                jssdkConfigDTO.setAppId(authInfo.getAppid());

                Map<String,Object> maps = new HashMap<String,Object>();
                maps.put("jsapi_ticket",ticket.getTicket());
                maps.put("noncestr",noncestr);
                maps.put("timestamp",timestamp);
                maps.put("url",url);
                try {
                    jssdkConfigDTO.setSignature(Sha1.SHA1(maps));
                } catch (DigestException e) {
                    e.printStackTrace();
                }
            }
        }
        return jssdkConfigDTO;
    }
}
