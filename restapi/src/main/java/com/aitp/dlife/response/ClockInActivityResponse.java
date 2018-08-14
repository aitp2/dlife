package com.aitp.dlife.response;

import java.io.Serializable;

public class ClockInActivityResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6547652834940893058L;

	/** 活动是否完成  **/
	private boolean completed;
	
	/** 活动排名 **/
	private String ranking = "0";
	
	/** 最长连续打卡天数 **/
	private String longestContinueDays = "0";
	
	/** 总共打卡天数 **/
	private String totalClockInDays = "0";
	
	/** 最早打卡时间 **/
	private String earliestClockInTime = "无";
	
	/** 最晚打卡时间 **/
	private String latestClockInTime = "无";

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public String getLongestContinueDays() {
		return longestContinueDays;
	}

	public void setLongestContinueDays(String longestContinueDays) {
		this.longestContinueDays = longestContinueDays;
	}

	public String getTotalClockInDays() {
		return totalClockInDays;
	}

	public void setTotalClockInDays(String totalClockInDays) {
		this.totalClockInDays = totalClockInDays;
	}

	public String getEarliestClockInTime() {
		return earliestClockInTime;
	}

	public void setEarliestClockInTime(String earliestClockInTime) {
		this.earliestClockInTime = earliestClockInTime;
	}

	public String getLatestClockInTime() {
		return latestClockInTime;
	}

	public void setLatestClockInTime(String latestClockInTime) {
		this.latestClockInTime = latestClockInTime;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "ClockInActivityResponse [completed=" + completed + ", ranking=" + ranking + ", longestContinueDays="
				+ longestContinueDays + ", totalClockInDays=" + totalClockInDays + ", earliestClockInTime="
				+ earliestClockInTime + ", latestClockInTime=" + latestClockInTime + "]";
	}
	
	
}
