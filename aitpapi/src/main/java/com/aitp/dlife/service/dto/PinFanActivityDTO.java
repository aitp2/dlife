package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PinFanActivity entity.
 */
public class PinFanActivityDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    private Integer activitiyType;

    @Size(max = 128)
    private String descrption;

    @Size(max = 128)
    private String organizeUser;

    @Size(max = 128)
    private String coverPicture;

    private Instant appointDatetime;

    private Integer lowerLimit;

    private Integer upperLimit;

    @Size(max = 32)
    private String payType;

    private Instant deadline;

    @Size(max = 500)
    private String comment;

    private Boolean isActive;

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

    public Integer getActivitiyType() {
        return activitiyType;
    }

    public void setActivitiyType(Integer activitiyType) {
        this.activitiyType = activitiyType;
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

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        if(pinFanActivityDTO.getId() == null || getId() == null) {
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
            ", activitiyType=" + getActivitiyType() +
            ", descrption='" + getDescrption() + "'" +
            ", organizeUser='" + getOrganizeUser() + "'" +
            ", coverPicture='" + getCoverPicture() + "'" +
            ", appointDatetime='" + getAppointDatetime() + "'" +
            ", lowerLimit=" + getLowerLimit() +
            ", upperLimit=" + getUpperLimit() +
            ", payType='" + getPayType() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", comment='" + getComment() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
