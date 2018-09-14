package com.aitp.dlife.response;

import java.io.Serializable;

public class ParticipationSummaryResponse implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3714519476896697448L;

	/** 总共加入天数 **/
	private String totalParticipateDays;
	
	/** 总共打卡天数 **/
	private String totalClockInDays;
	
	/** 当前连续天数 **/
	private String currentContinueDays;
	
	/** 最长连续打卡天数 **/
	private String longestContinueDays;
	
	
	public String getTotalParticipateDays() {
		return totalParticipateDays;
	}

	public void setTotalParticipateDays(String totalParticipateDays) {
		this.totalParticipateDays = totalParticipateDays;
	}

	public String getTotalClockInDays() {
		return totalClockInDays;
	}

	public void setTotalClockInDays(String totalClockInDays) {
		this.totalClockInDays = totalClockInDays;
	}

	public String getCurrentContinueDays() {
		return currentContinueDays;
	}

	public void setCurrentContinueDays(String currentContinueDays) {
		this.currentContinueDays = currentContinueDays;
	}

	public String getLongestContinueDays() {
		return longestContinueDays;
	}

	public void setLongestContinueDays(String longestContinueDays) {
		this.longestContinueDays = longestContinueDays;
	}

	@Override
	public String toString() {
		return "ParticipationSummaryResponse [totalParticipateDays=" + totalParticipateDays + ", totalClockInDays="
				+ totalClockInDays + ", currentContinueDays=" + currentContinueDays + ", longestContinueDays="
				+ longestContinueDays + "]";
	}

}
