package com.aitp.web.common.service;

import com.aitp.web.common.service.dto.ActivityMessageDTO;

public interface PointShopService {

	public boolean sendMessage(ActivityMessageDTO messageDto);
	
}
