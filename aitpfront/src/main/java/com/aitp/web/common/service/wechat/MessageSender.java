package com.aitp.web.common.service.wechat;

import com.aitp.web.common.service.beans.Message;
import com.aitp.web.common.service.beans.MessageResponse;

public interface MessageSender {
	/**
	 *  会进行三次重试的 模板消息发送
	 * @param message
	 * @param authToken
	 * @return
	 * @throws InterruptedException
	 */
	public boolean sendMessage(Message message) ;
	
	
	
}
