package com.aitp.web.common.service.wechat;

import com.aitp.web.common.service.beans.Message;
import com.aitp.web.common.service.exception.WechatException;

public interface MessageSender {
	
	
	public boolean sendMessage(Message message) throws WechatException;
	
	
	
}
