package com.aitp.dlife.response;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import com.aitp.dlife.service.dto.PicsDTO;


public class ClockInResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6922221859152952542L;
	private boolean clockInSuccess;

	public boolean isClockInSuccess() {
		return clockInSuccess;
	}

	public void setClockInSuccess(boolean clockInSuccess) {
		this.clockInSuccess = clockInSuccess;
	} 
	
	
}
