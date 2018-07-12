package com.aitp.web.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aitp.web.common.service.beans.Message;
import com.aitp.web.common.service.beans.TempData;
import com.aitp.web.common.service.dto.ActivityMessageDTO;
import com.aitp.web.common.service.wechat.MessageSender;

@Service
public class MessageServiceImp implements MessageService{

	@Autowired
	private MessageSender messageSender;

	@Value("${wechat.messageTemp.id}")
	private String templateID;
	
	@Override
	public boolean SendMessage(ActivityMessageDTO messageDto) {
		Message message = new Message();
		message.setTouser(messageDto.getTouser());
		message.setTemplate_id(templateID);
		message.addTempData(new TempData("title", messageDto.getTitle()));
		message.addTempData(new TempData("action",messageDto.getAction()));
		messageSender.sendMessage(message);
		return false;
		
	}

	
	
	
	
}
