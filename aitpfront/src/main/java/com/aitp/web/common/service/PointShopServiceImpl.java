package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.ActivityMessageDTO;
import com.aitp.web.common.service.dto.WechatMessageData;

import java.text.MessageFormat;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;
import java.util.Date;

@Service
public class PointShopServiceImpl implements PointShopService {

	private static final Logger logger = LoggerFactory.getLogger(PointShopServiceImpl.class);
	private static final String EVENT_TYPE_RECHARGE_POINT="RECHARGE_POINT";
	private static final String EVENT_TYPE_ORDER_PICKPACK="ORDER_PICKPACK";

	private static final String MESSAGE_EVENT_ORDER_PICKPACK ="您的积分兑换订单已经拣配完成，请在15分钟内前往提取点【{0}】提取您兑换的商品，谢谢！\n";
	private static final String MESSAGE_EVENT_RECHARGE_POINT ="【dLife积分商城】您已成功充值积分{0}，可前往您的个人中心查看！";
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private Environment env;

	public boolean sendMessage(ActivityMessageDTO messageDTO){
		final String restApiPath=env.getProperty("rest_api_url");
		JSONObject userData = userService.getUserByWechatUserId(restApiPath,messageDTO.getTouser());
		logger.info(restApiPath+" query via restapi:"+userData);
		if(null!= userData){
			messageDTO.setTouser(userData.getString("openId"));

			String templateID = "";
			switch (messageDTO.getType()) {
				case EVENT_TYPE_RECHARGE_POINT:
					templateID= env.getProperty("recharge_message_templateID");
					WechatMessageData pointMessage = messageDTO.getWechatMessageDatas().stream().filter(parameterData->"point".equals(parameterData.getKeyName())).findAny().orElse(null);
					List<WechatMessageData> messageList = messageDTO.getWechatMessageDatas().stream().filter(parameterData->!"point".equals(parameterData.getKeyName())).collect(Collectors.toList());
					messageList.stream().forEach(parameterData->{parameterData.setColor("#000000");});
					messageDTO.setWechatMessageDatas(messageList);
					messageDTO.addMessageData(new WechatMessageData("first", MessageFormat.format(MESSAGE_EVENT_RECHARGE_POINT,pointMessage.getValue()), "#000000"));
					messageDTO.addMessageData(new WechatMessageData("accountType", "手机号码", "#000000"));
					messageDTO.addMessageData(new WechatMessageData("account", userData.getString("mobileNum"), "#000000"));
					break;
				case EVENT_TYPE_ORDER_PICKPACK:
					templateID= env.getProperty("pickpack_message_templateID");
					//WechatMessageData orderMessage = messageDTO.getWechatMessageDatas().stream().filter(parameterData->"OrderSn".equals(parameterData.getKeyName())).findAny().orElse(null);
					WechatMessageData orderAddress = messageDTO.getWechatMessageDatas().stream().filter(parameterData->"address".equals(parameterData.getKeyName())).findAny().orElse(null);
					messageList = messageDTO.getWechatMessageDatas().stream().filter(parameterData->!"address".equals(parameterData.getKeyName())).collect(Collectors.toList());
					messageList.stream().forEach(parameterData->{parameterData.setColor("#000000");});
					messageDTO.setWechatMessageDatas(messageList);
					messageDTO.addMessageData(new WechatMessageData("first", MessageFormat.format(MESSAGE_EVENT_ORDER_PICKPACK,orderAddress.getValue()), "#000000"));
					break;
			}
			messageDTO.setTemplateID(templateID);
			logger.info("send message:{}",messageDTO);
			boolean flag = messageService.SendMessage(messageDTO);
			if (!flag){
				logger.debug("send message to {} failed",userData.getString("nickName"));
			}
		}else{

			return false;
		}
		return true;
	}
	
}
