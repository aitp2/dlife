package com.aitp.dlife.service.dto;


import javax.validation.constraints.*;

import com.aitp.dlife.service.enums.Status;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FitnessActivity entity.
 */
public class FitnessActivityDTO implements Serializable {

    private Long id;

    @Size(max = 64)
    private String title;

    @Size(max = 128)
    private String descrption;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 128)
    private String nickName;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String project;

    @Size(max = 255)
    private String companyRole;

    private String signStartTime;

    private String signEndTime;

    private String activityStartTime;

    private String activityEndTime;

    private Integer attendCount;

    private Integer status;

    private Set<ActivityParticipationDTO> activityParticipations;
    
    private Integer commentCount;
    
    private Integer readingCount;

    private Set<PicsDTO> images = new HashSet<>();

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

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
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

    public String getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(String companyRole) {
        this.companyRole = companyRole;
    }



    public String getSignStartTime() {
		return signStartTime;
	}

	public void setSignStartTime(String signStartTime) {
		this.signStartTime = signStartTime;
	}

	public String getSignEndTime() {
		return signEndTime;
	}

	public void setSignEndTime(String signEndTime) {
		this.signEndTime = signEndTime;
	}

	public String getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public String getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Integer getAttendCount() {
		return attendCount;
	}
	
	


	public Set<ActivityParticipationDTO> getActivityParticipations() {
		return activityParticipations;
	}

	public void setActivityParticipations(Set<ActivityParticipationDTO> activityParticipations) {
		this.activityParticipations = activityParticipations;
	}

	public void setAttendCount(Integer attendCount) {
		this.attendCount = attendCount;
	}

	public Set<PicsDTO> getImages() {
		return images;
	}

	public void setImages(Set<PicsDTO> images) {
		this.images = images;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getReadingCount() {
		return readingCount;
	}

	public void setReadingCount(Integer readingCount) {
		this.readingCount = readingCount;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FitnessActivityDTO fitnessActivityDTO = (FitnessActivityDTO) o;
        if(fitnessActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fitnessActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FitnessActivityDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", descrption='" + getDescrption() + "'" +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", project='" + getProject() + "'" +
            ", companyRole='" + getCompanyRole() + "'" +
            ", signStartTime='" + getSignStartTime() + "'" +
            ", signEndTime='" + getSignEndTime() + "'" +
            ", activityStartTime='" + getActivityStartTime() + "'" +
            ", activityEndTime='" + getActivityEndTime() + "'" +
            "}";
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}
