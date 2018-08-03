package com.aitp.dlife.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FitnessActivity entity.
 */
public class FitnessActivityDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String title;

    @Size(max = 1024)
    private String descrption;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 128)
    private String nickName;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String project;

    @Size(max = 128)
    private String companyRole;

    private Instant signStartTime;

    private Instant signEndTime;

    private Instant activityStartTime;

    private Instant activityEndTime;

    private Integer commentCount;

    private Instant modifyTime;

    private Integer readingCount;

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

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
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
        if (fitnessActivityDTO.getId() == null || getId() == null) {
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
            ", commentCount=" + getCommentCount() +
            ", modifyTime='" + getModifyTime() + "'" +
            ", readingCount=" + getReadingCount() +
            "}";
    }
}
