package com.aitp.dlife.service.mapper;

import java.time.Instant;

import com.aitp.dlife.service.enums.Status;

public class FitnessActivityUtil {

	
	public static Integer getStatus(Instant start, Instant end){
		  Instant now = Instant.now();
        if (start.isBefore(now) && end.isAfter(now)) {
            return Status.IN_PROGRESS.getValue();
        }
        else if (end.isBefore(now)){
        	 return Status.END.getValue();
        }else
        {
        	 return Status.OPEND.getValue();
        }
	}
}
