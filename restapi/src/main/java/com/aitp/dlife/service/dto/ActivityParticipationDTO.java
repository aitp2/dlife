package com.aitp.dlife.service.dto;



import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActivityParticipation entity.
 */
public class ActivityParticipationDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 128)
    private String nickName;

    @Size(max=128)
    private String activityTitle;
    
    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String project;

    private String participationTime;

    private Long activityId;

    private Integer clockinCount;

    private Integer attendStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }


    public String getParticipationTime() {
		return participationTime;
	}

	public void setParticipationTime(String participationTime) {
		this.participationTime = participationTime;
	}

	public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long fitnessActivityId) {
        this.activityId = fitnessActivityId;
    }


    
    
    public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public Integer getClockinCount() {
		return clockinCount;
	}

	public void setClockinCount(Integer clockinCount) {
		this.clockinCount = clockinCount;
	}

    public Integer getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(Integer attendStatus) {
        this.attendStatus = attendStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityParticipationDTO activityParticipationDTO = (ActivityParticipationDTO) o;
        if(activityParticipationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityParticipationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ActivityParticipationDTO [id=" + id + ", wechatUserId=" + wechatUserId + ", nickName=" + nickName
				+ ", activityTitle=" + activityTitle + ", avatar=" + avatar + ", project=" + project
				+ ", participationTime=" + participationTime + ", activityId=" + activityId + ", clockinCount="
				+ clockinCount + ", attendStatus=" + attendStatus + "]";
	}

 
}
