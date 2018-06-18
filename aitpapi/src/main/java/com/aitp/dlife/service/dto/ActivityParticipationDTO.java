package com.aitp.dlife.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Size;

import com.aitp.dlife.domain.FitnessActivity;

/**
 * A DTO for the ActivityParticipation entity.
 */
public class ActivityParticipationDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 128)
    private String nickName;

    @Size(max = 1024)
    private String avatar;

    private Instant participationTime;

    private Long activityId;
    
    private Set<PicsDTO> images = new HashSet<>();
    
    private Set<ClockInDTO> clockIns = new HashSet<>();

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

    public Instant getParticipationTime() {
        return participationTime;
    }

    public void setParticipationTime(Instant participationTime) {
        this.participationTime = participationTime;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long fitnessActivityId) {
        this.activityId = fitnessActivityId;
    }
    

    public Set<ClockInDTO> getClockIns() {
		return clockIns;
	}

	public void setClockIns(Set<ClockInDTO> clockIns) {
		this.clockIns = clockIns;
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
        return "ActivityParticipationDTO{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", participationTime='" + getParticipationTime() + "'" +
            "}";
    }
}
