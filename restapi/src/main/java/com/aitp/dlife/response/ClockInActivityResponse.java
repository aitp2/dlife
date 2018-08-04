package com.aitp.dlife.response;

public class ClockInActivityResponse {
	
	/** 活动是否完成  **/
	private boolean completed;
	
	/** 活动排名 **/
	private String ranking;
	
	/** 最长连续打卡天数 **/
	private String longestContinueDays;
	
	/** 总共打卡天数 **/
	private String totalClockInDays;
	
	/** 最早打卡时间 **/
	private String earliestClockInTime;
	
	/** 最晚打卡时间 **/
	private String latestClockInTime;

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
	
	
}
