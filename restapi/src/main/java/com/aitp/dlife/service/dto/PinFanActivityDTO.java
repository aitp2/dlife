package com.aitp.dlife.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the PinFanActivity entity.
 */
public class PinFanActivityDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String nickName;

    private Integer activitiyType;

    @Size(max = 128)
    private String activitiyTile;

    private BigDecimal budget;

    @Size(max = 1024)
    private String activitiyAddre;

    @Size(max = 1024)
    private String descrption;

    @Size(max = 128)
    private String organizeUser;

    @Size(max = 128)
    private String coverPicture;

    private Instant appointDatetime;

    private Instant appointEndDatetime;

    @Size(max = 1024)
    private String salerUrl;

    private Integer lowerLimit;

    private Integer upperLimit;

    @Size(max = 32)
    private String payType;

    private Instant deadline;

    @Size(max = 500)
    private String comment;

    private Integer status;

    private Integer commentCount;

    private Integer readingCount;

    private Instant modifyTime;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getActivitiyType() {
        return activitiyType;
    }

    public void setActivitiyType(Integer activitiyType) {
        this.activitiyType = activitiyType;
    }

    public String getActivitiyTile() {
        return activitiyTile;
    }

    public void setActivitiyTile(String activitiyTile) {
        this.activitiyTile = activitiyTile;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getActivitiyAddre() {
        return activitiyAddre;
    }

    public void setActivitiyAddre(String activitiyAddre) {
        this.activitiyAddre = activitiyAddre;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getOrganizeUser() {
        return organizeUser;
    }

    public void setOrganizeUser(String organizeUser) {
        this.organizeUser = organizeUser;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Instant getAppointDatetime() {
        return appointDatetime;
    }

    public void setAppointDatetime(Instant appointDatetime) {
        this.appointDatetime = appointDatetime;
    }

    public Instant getAppointEndDatetime() {
        return appointEndDatetime;
    }

    public void setAppointEndDatetime(Instant appointEndDatetime) {
        this.appointEndDatetime = appointEndDatetime;
    }

    public String getSalerUrl() {
        return salerUrl;
    }

    public void setSalerUrl(String salerUrl) {
        this.salerUrl = salerUrl;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getReadingCount() {
        return readingCount;
    }

    public void setReadingCount(Integer readingCount) {
        this.readingCount = readingCount;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PinFanActivityDTO pinFanActivityDTO = (PinFanActivityDTO) o;
        if (pinFanActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinFanActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PinFanActivityDTO{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", activitiyType=" + getActivitiyType() +
            ", activitiyTile='" + getActivitiyTile() + "'" +
            ", budget=" + getBudget() +
            ", activitiyAddre='" + getActivitiyAddre() + "'" +
            ", descrption='" + getDescrption() + "'" +
            ", organizeUser='" + getOrganizeUser() + "'" +
            ", coverPicture='" + getCoverPicture() + "'" +
            ", appointDatetime='" + getAppointDatetime() + "'" +
            ", appointEndDatetime='" + getAppointEndDatetime() + "'" +
            ", salerUrl='" + getSalerUrl() + "'" +
            ", lowerLimit=" + getLowerLimit() +
            ", upperLimit=" + getUpperLimit() +
            ", payType='" + getPayType() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", comment='" + getComment() + "'" +
            ", status=" + getStatus() +
            ", commentCount=" + getCommentCount() +
            ", readingCount=" + getReadingCount() +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
