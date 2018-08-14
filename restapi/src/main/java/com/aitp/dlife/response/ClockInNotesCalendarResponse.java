package com.aitp.dlife.response;

import java.io.Serializable;
import java.util.List;

public class ClockInNotesCalendarResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5683500143244272212L;

	/**
	 * 打卡日历
	 */
	private List<String> clockInNotesCalendars;

	public List<String> getClockInNotesCalendars() {
		return clockInNotesCalendars;
	}

	public void setClockInNotesCalendars(List<String> clockInNotesCalendars) {
		this.clockInNotesCalendars = clockInNotesCalendars;
	}

	@Override
	public String toString() {
		return "ClockInNotesCalendarResponse [clockInNotesCalendars=" + clockInNotesCalendars + "]";
	}
	
}
