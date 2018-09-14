package com.aitp.dlife.response;


import java.io.Serializable;


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

	@Override
	public String toString() {
		return "ClockInResponse [clockInSuccess=" + clockInSuccess + "]";
	} 
	
}
