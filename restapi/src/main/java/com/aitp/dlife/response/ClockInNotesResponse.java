package com.aitp.dlife.response;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ClockInNotesResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5230866376857642601L;
	
	/**
	 * 活动标题
	 */
	private String title;

	/**
	 * 打卡日记
	 */
	private String signNote;

	/**
	 * 打卡时间
	 */
	private String punchDateTime;

	/**
	 * 打卡图片
	 */
	private List<PicsResponse> pics;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSignNote() {
		return signNote;
	}

	public void setSignNote(String signNote) {
		this.signNote = signNote;
	}

	public String getPunchDateTime() {
		return punchDateTime;
	}

	public void setPunchDateTime(String punchDateTime) {
		this.punchDateTime = punchDateTime;
	}

	public List<PicsResponse> getPics() {
		return pics;
	}

	public void setPics(List<PicsResponse> pics) {
		this.pics = pics;
	}

	@Override
	public String toString() {
		return "ClockInNotesResponse [title=" + title + ", signNote=" + signNote + ", punchDateTime=" + punchDateTime
				+ ", pics=" + pics + "]";
	}


}
