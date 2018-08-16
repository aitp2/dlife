package com.aitp.dlife.request;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty
    private Long activityParticipationId;

    /**
     * 打卡图片
     */
    private List<PicsRequest> pics = new ArrayList<>();


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


    public List<PicsRequest> getPics() {
		return pics;
	}

	public void setPics(List<PicsRequest> pics) {
		this.pics = pics;
	}

	public String getWechatUserId() {
		return wechatUserId;
	}

	public void setWechatUserId(String wechatUserId) {
		this.wechatUserId = wechatUserId;
	}

}
