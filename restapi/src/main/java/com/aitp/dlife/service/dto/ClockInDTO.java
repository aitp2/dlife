package com.aitp.dlife.service.dto;


import javax.validation.constraints.*;


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

    private Integer activityId;
    
    private Integer thumbsUpCount;
    
    private Integer replyCount;
    
    private String nickName;

    private String avatar;
    
    private Set<ThumbsUpDTO> thumbsUpDTOs = new HashSet<>();

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

	
	
    public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getThumbsUpCount() {
		return thumbsUpCount;
	}

	public void setThumbsUpCount(Integer thumbsUpCount) {
		this.thumbsUpCount = thumbsUpCount;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	
    public Set<ThumbsUpDTO> getThumbsUpDTOs() {
		return thumbsUpDTOs;
	}

	public void setThumbsUpDTOs(Set<ThumbsUpDTO> thumbsUpDTOs) {
		this.thumbsUpDTOs = thumbsUpDTOs;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	@Override
	public String toString() {
		return "ClockInDTO [id=" + id + ", title=" + title + ", wechatUserId=" + wechatUserId + ", signNote=" + signNote
				+ ", punchDateTime=" + punchDateTime + ", activityParticipationId=" + activityParticipationId
				+ ", pics=" + pics + ", activityId=" + activityId + ", thumbsUpCount=" + thumbsUpCount + ", replyCount="
				+ replyCount + ", nickName=" + nickName + ", avatar=" + avatar + ", thumbsUpDTOs=" + thumbsUpDTOs + "]";
	}
}
