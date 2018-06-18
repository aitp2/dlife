package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;

import com.aitp.dlife.domain.ActivityParticipation;
import com.aitp.dlife.domain.Pics;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FitnessActivity entity.
 */
public class FitnessActivityDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 64)
    private String title;

    @Size(max = 128)
    private String descrption;

    private Instant signStartTime;

    private Instant signEndTime;

    private Instant activityStartTime;

    private Instant activityEndTime;
    
    private Set<ActivityParticipationDTO> activityParticipations = new HashSet<>();
    
    private Set<PicsDTO> images = new HashSet<>();

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

    public Instant getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(Instant signStartTime) {
        this.signStartTime = signStartTime;
    }

    public Instant getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(Instant signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Instant getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Instant activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Instant getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Instant activityEndTime) {
        this.activityEndTime = activityEndTime;
    }
    

    public Set<ActivityParticipationDTO> getActivityParticipations() {
		return activityParticipations;
	}

	public void setActivityParticipations(Set<ActivityParticipationDTO> activityParticipations) {
		this.activityParticipations = activityParticipations;
	}
	

	public Set<PicsDTO> getImages() {
		return images;
	}

	public void setImages(Set<PicsDTO> images) {
		this.images = images;
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
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", title='" + getTitle() + "'" +
            ", descrption='" + getDescrption() + "'" +
            ", signStartTime='" + getSignStartTime() + "'" +
            ", signEndTime='" + getSignEndTime() + "'" +
            ", activityStartTime='" + getActivityStartTime() + "'" +
            ", activityEndTime='" + getActivityEndTime() + "'" +
            "}";
    }
}
