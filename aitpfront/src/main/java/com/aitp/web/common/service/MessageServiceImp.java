package com.aitp.web.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aitp.web.common.service.beans.Message;
import com.aitp.web.common.service.beans.TempData;
import com.aitp.web.common.service.dto.ActivityMessageDTO;
import com.aitp.web.common.service.dto.WechatMessageData;
import com.aitp.web.common.service.wechat.MessageSender;

@Service
public class MessageServiceImp implements MessageService{

	@Autowired
	private MessageSender messageSender;

	
	@Override
	public boolean SendMessage(ActivityMessageDTO messageDto) {
		Message message = new Message();
		message.setTouser(messageDto.getTouser());
		message.setTemplate_id(messageDto.getTemplateID());
		for (WechatMessageData messageData : messageDto.getWechatMessageDatas()) {
			message.addTempData(messageData.getKeyName(),new TempData(messageData.getValue(),messageData.getColor()));
		}
		messageSender.sendMessage(message);
		return false;
		
	}

	
	
	
	
}
