package com.aitp.web.common.job;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class PinFanRemindJob {

	
	@Scheduled(cron="${cron.pinfan.remind}")
	public void start(){
		
		System.out.println("11111");
		
	}
	
	
}
