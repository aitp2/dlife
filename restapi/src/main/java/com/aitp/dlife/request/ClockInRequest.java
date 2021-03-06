package com.aitp.dlife.request;


import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the ClockIn entity.
 */
public class ClockInRequest {


	/**
	 * 活动标题
	 */
	@Size(max = 64)
    private String title;

	/**
	 * 用户id
	 */
    private String wechatUserId;

    /**
     * 打卡日记
     */
    @Size(max = 1024)
    private String signNote;

    
    /**
     * 用户参与活动id
     */
    @NotNull
    private Long activityParticipationId;

    /**
     * 打卡图片
     */
    private Set<PicsRequest> pics = new HashSet<>();


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

	public Long getActivityParticipationId() {
        return activityParticipationId;
    }

    public void setActivityParticipationId(Long activityParticipationId) {
        this.activityParticipationId = activityParticipationId;
    }

    public Set<PicsRequest> getPics() {
		return pics;
	}

	public void setPics(Set<PicsRequest> pics) {
		this.pics = pics;
	}

    public String getWechatUserId() {
		return wechatUserId;
	}

	public void setWechatUserId(String wechatUserId) {
		this.wechatUserId = wechatUserId;
	}

}
