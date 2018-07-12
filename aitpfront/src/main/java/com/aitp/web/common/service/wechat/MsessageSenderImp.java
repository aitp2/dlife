package com.aitp.web.common.service.wechat;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aitp.web.common.service.beans.Message;
import com.aitp.web.common.service.beans.MessageResponse;
import com.aitp.web.common.service.beans.TempResponse;
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
	
	@Value("${wechat.messageTemp.id}")
	private String tempId;
	
	
	public final static ThreadLocal<Integer> RETRY_LENGHT = new ThreadLocal<Integer>();
	
	
	
	
	
	@Override
	public boolean sendMessage(Message message,String authToken)  {
		  Gson gson = new Gson();
		  String url = MessageFormat.format(sendMessageUrl, authToken);
		  String response = HttpUtil.doPostJson(url, message);
		  MessageResponse response1 = gson.fromJson(response, MessageResponse.class);
		 	WeChatErrCode weChatErrCode = WeChatErrCode.valueof(response1.getErrcode());
			 switch (weChatErrCode) {
				case zero:	
					  return true;
				 default:
					throw new WechatException(response1.getErrcode(),response1.getErrmsg());
				}
	}
	
	
	public String getTempId(String tempCode,String authToken){
		Gson gson = new Gson();
		String url = MessageFormat.format(getCodeUrl, authToken);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("template_id_short", tempId);
		  String response = HttpUtil.doPostJson(url, jsonObject);
		  TempResponse repsonse = gson.fromJson(response, TempResponse.class);
		return repsonse.getTemplate_id();
		
	}




}
