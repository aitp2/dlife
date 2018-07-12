package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.ActivityMessageDTO;

public interface MessageService {

	public boolean SendMessage(ActivityMessageDTO messageDto);
	
}
