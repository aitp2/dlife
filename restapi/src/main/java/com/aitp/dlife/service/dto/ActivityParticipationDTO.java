package com.aitp.dlife.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Size(max = 128)
    private String project;

    private Instant participationTime;

    private Integer totalParticipateDays;

    private Integer totalClockinDays;

    private Integer currentContinueDays;

    private Integer longestContinueDays;

    private Instant latestClockinTime;

    private Long fitnessActivityId;

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

    public Instant getParticipationTime() {
        return participationTime;
    }

    public void setParticipationTime(Instant participationTime) {
        this.participationTime = participationTime;
    }

    public Integer getTotalParticipateDays() {
        return totalParticipateDays;
    }

    public void setTotalParticipateDays(Integer totalParticipateDays) {
        this.totalParticipateDays = totalParticipateDays;
    }

    public Integer getTotalClockinDays() {
        return totalClockinDays;
    }

    public void setTotalClockinDays(Integer totalClockinDays) {
        this.totalClockinDays = totalClockinDays;
    }

    public Integer getCurrentContinueDays() {
        return currentContinueDays;
    }

    public void setCurrentContinueDays(Integer currentContinueDays) {
        this.currentContinueDays = currentContinueDays;
    }

    public Integer getLongestContinueDays() {
        return longestContinueDays;
    }

    public void setLongestContinueDays(Integer longestContinueDays) {
        this.longestContinueDays = longestContinueDays;
    }

    public Instant getLatestClockinTime() {
        return latestClockinTime;
    }

    public void setLatestClockinTime(Instant latestClockinTime) {
        this.latestClockinTime = latestClockinTime;
    }

    public Long getFitnessActivityId() {
        return fitnessActivityId;
    }

    public void setFitnessActivityId(Long fitnessActivityId) {
        this.fitnessActivityId = fitnessActivityId;
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
        if (activityParticipationDTO.getId() == null || getId() == null) {
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
            ", project='" + getProject() + "'" +
            ", participationTime='" + getParticipationTime() + "'" +
            ", totalParticipateDays=" + getTotalParticipateDays() +
            ", totalClockinDays=" + getTotalClockinDays() +
            ", currentContinueDays=" + getCurrentContinueDays() +
            ", longestContinueDays=" + getLongestContinueDays() +
            ", latestClockinTime='" + getLatestClockinTime() + "'" +
            ", fitnessActivity=" + getFitnessActivityId() +
            "}";
    }
}
