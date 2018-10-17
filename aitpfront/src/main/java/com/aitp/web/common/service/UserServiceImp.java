package com.aitp.web.common.service;

import com.aitp.web.common.service.beans.IdToken;
import com.aitp.web.common.service.beans.UserPointAction;
import com.aitp.web.common.service.beans.UserToken;
import com.aitp.web.common.service.dto.WechatUserDTO;
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImp implements UserService{

    @Autowired
    private Environment env;
    
    @Override
    public JSONObject getUserByOpenid(String apiPath, String openid) {
        String userInfo = HttpUtil.doGetJson(apiPath+"/wechat-users/userinfo/"+openid);
        if(StringUtils.isNotBlank(userInfo)){
            return JSONObject.parseObject(userInfo);
        }
        return null;
    }

    @Override
    public JSONObject createUser(String apiPath, WechatUserDTO wechatUserDTO) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("avatar",wechatUserDTO.getHeadimgurl());
        jsonObject.put("openId",wechatUserDTO.getOpenId());
        jsonObject.put("nickName",HttpUtil.baseEncoder(wechatUserDTO.getUserName()));
        if (StringUtils.isNotBlank(wechatUserDTO.getSex())){
            jsonObject.put("sex",Integer.valueOf(wechatUserDTO.getSex()));
        }
        String resultData=HttpUtil.doPostJson(apiPath+"/wechat-users",jsonObject);
        if(StringUtils.isNotBlank(resultData)){
            return JSONObject.parseObject(resultData);
        }
        return null;
    }

    @Override
    public JSONObject getUserByWechatUserId(String apiPath, String wechatUserId) {
        String userInfo = HttpUtil.doGetJson(apiPath+"/wechat-users/"+wechatUserId);
        if(StringUtils.isNotBlank(userInfo)){
            return JSONObject.parseObject(userInfo);
        }
        return null;
    }

    public static void main(String[] args) {
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("avatar","http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJt9eFezfhFoxBxEV3oK9RxWJnibicl07jAUvxw797X2D8qvwNjykytAmZbtY1BpfQRdOT0SbpibkibuA/132");
//        jsonObject.put("openId","oeVW6wkkQg-qxgItc_162QSfprow");
//        jsonObject.put("nickName","TW9vb29vbg==");
//        jsonObject.put("sex",2);
//        String resultData=HttpUtil.doPostJson("http://newapi.aitpgroup.tech:8080/api/wechat-users",jsonObject);
//        if(StringUtils.isNotBlank(resultData)){
//            System.out.println(JSONObject.parseObject(resultData));
//        }
//    	WechatUserDTO wechatUserDTO=new WechatUserDTO();
//    	wechatUserDTO.setUserId("239");
//    	addPoint(wechatUserDTO);
    }

    @Override
	public void addPoint(WechatUserDTO wechatUserDTO) {
        String restApiPath=env.getProperty("rest_api_url");
        //String restApiPath="http://47.97.202.185:8080/api";
        UserToken userToken=new UserToken();
        userToken.setPassword("admin");
        userToken.setRememberMe("false");
        userToken.setUsername("admin");
        String result = HttpUtil.doPostJson(restApiPath+"/authenticate",userToken);
        String token=null;
        try
        {
        	token=JSONObject.parseObject(result, IdToken.class).getId_token();
        }
		catch (Exception e) 
        {
			return;
		}
    	UserPointAction userPointAction=new UserPointAction();
    	userPointAction.setEventName("登录");
    	userPointAction.setEventType("LOGIN");
    	userPointAction.setUuid(UUID.randomUUID().toString());
    	userPointAction.setTargetSystem("PLATFORM");
    	userPointAction.setEventTime(ZonedDateTime.now().toString());
    	userPointAction.setUserid(wechatUserDTO.getUserId());
    	HttpUtil.doPostJson(restApiPath+"/task-engine/event/new", userPointAction,token);
	}
}
