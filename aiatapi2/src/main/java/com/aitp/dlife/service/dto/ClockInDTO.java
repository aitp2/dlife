package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;

import com.aitp.dlife.domain.Pics;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ClockIn entity.
 */
public class ClockInDTO implements Serializable {

    private Long id;

    @Size(max = 64)
    private String title;

    private String wechatUserId;

    @Size(max = 1024)
    private String signNote;

    private String punchDateTime;

    private Long activityParticipationId;

    private Set<PicsDTO> pics = new HashSet<>();

    private String nickName;

    private String avatar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

	public Long getActivityParticipationId() {
        return activityParticipationId;
    }

    public void setActivityParticipationId(Long activityParticipationId) {
        this.activityParticipationId = activityParticipationId;
    }


    public Set<PicsDTO> getPics() {
		return pics;
	}

	public void setPics(Set<PicsDTO> pics) {
		this.pics = pics;
	}

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWechatUserId() {
		return wechatUserId;
	}

	public void setWechatUserId(String wechatUserId) {
		this.wechatUserId = wechatUserId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClockInDTO clockInDTO = (ClockInDTO) o;
        if(clockInDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clockInDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClockInDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", signNote='" + getSignNote() + "'" +
            ", punchDateTime='" + getPunchDateTime() + "'" +
            "}";
    }
}
