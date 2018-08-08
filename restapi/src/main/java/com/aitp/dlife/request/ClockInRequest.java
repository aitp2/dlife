package com.aitp.dlife.request;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

/**
 * A DTO for the ClockIn entity.
 */
public class ClockInRequest implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -5231225359327519297L;

	@Size(max = 64)
    private String title;

    private String wechatUserId;

    @Size(max = 1024)
    private String signNote;

    private Long activityParticipationId;

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
