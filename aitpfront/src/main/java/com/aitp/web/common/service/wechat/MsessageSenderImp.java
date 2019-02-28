package com.aitp.web.common.service.wechat;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aitp.web.common.service.beans.AuthInfo;
import com.aitp.web.common.service.beans.Message;
import com.aitp.web.common.service.beans.MessageResponse;
import com.aitp.web.common.service.beans.TempResponse;
import com.aitp.web.common.service.beans.Token;
import com.aitp.web.common.service.enums.WeChatErrCode;
import com.aitp.web.common.service.exception.WechatException;
import com.aitp.web.common.service.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

@Component
public class MsessageSenderImp implements MessageSender{
 
	@Value("${wechat.messageTemp.sendUrl}")
	private String  sendMessageUrl;
	
	@Value("${wechat.messageTemp.getCodeUrl}")
	private String  getCodeUrl;
	
	@Value("${wechat_app_id.dlife}")
	private String wechatAppId;
  
	@Value("${wechat_app_secret.dlife}")
	private String wechatAppSecret;
	
	
	@Value("${wechat_token_url}")
	private String wechatTokenUrl;
	
  
	
    @Autowired
    AuthClient authClient;
	
	public final static ThreadLocal<Integer> CALL_SIZE = new ThreadLocal<Integer>();
	
	
	@Override
	public boolean sendMessage(Message message)  {
		  Gson gson = new Gson();
		  String url = MessageFormat.format(sendMessageUrl, getAuthToken().getAccess_token());
		  String response = HttpUtil.doPostJson(url, message);
		  MessageResponse response1 = gson.fromJson(response, MessageResponse.class);
		 	WeChatErrCode weChatErrCode = WeChatErrCode.valueof(response1.getErrcode());
			 switch (weChatErrCode) {
				case zero:	
					  return true;
				 default:
					 return false;
				}
	}
	
	
//	public String getTempId(String tempCode,String authToken){
//		Gson gson = new Gson();
//		String url = MessageFormat.format(getCodeUrl, authToken);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("template_id_short", tempId);
//		  String response = HttpUtil.doPostJson(url, jsonObject);
//		  TempResponse repsonse = gson.fromJson(response, TempResponse.class);
//		return repsonse.getTemplate_id();
//	}

	private Token getAuthToken(){
        AuthInfo authInfo=new AuthInfo();
        authInfo.setAppid(wechatAppId);
        authInfo.setAppsecret(wechatAppSecret);
        authInfo.setAccessTokenUrl(MessageFormat.format(wechatTokenUrl,authInfo.getAppid(),authInfo.getAppsecret()));
        return authClient.loadToken(authInfo);
	}
	

}
