package com.aitp.web.common.service;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aitp.web.common.service.beans.Message;
import com.aitp.web.common.service.beans.TempData;
import com.aitp.web.common.service.dto.ActivityMessageDTO;
import com.aitp.web.common.service.dto.WechatMessageData;
import com.aitp.web.common.service.exception.WechatException;
import com.aitp.web.common.service.wechat.MessageSender;

@Service
public class MessageServiceImp implements MessageService{

	@Autowired
	private MessageSender messageSender;

	
	public final static ThreadLocal<Integer> CALL_SIZE = new ThreadLocal<Integer>();
	/**
	 *  会进行三次重试的 模板消息发送
	 * @param message
	 * @param authToken
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	public boolean SendMessage(ActivityMessageDTO messageDto) {
		Message message = new Message();
		message.setTouser(messageDto.getTouser());
		message.setTemplate_id(messageDto.getTemplateID());
		for (WechatMessageData messageData : messageDto.getWechatMessageDatas()) {
			message.addTempData(messageData.getKeyName(),new TempData(messageData.getValue(),messageData.getColor()));
		}
		  if(!messageSender.sendMessage(message)){
			 Integer callLenght =  CALL_SIZE.get();
			  if(callLenght==null){
				  callLenght = 0;
			  }
			  callLenght++;
			  CALL_SIZE.set(callLenght);
			  if(callLenght<3){
			  this.SendMessage(messageDto);
			  }
		  }else{
			  return true;
		  }
		return false;
	}

	
	
	
	
}
