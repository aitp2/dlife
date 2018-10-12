package com.aitp.dlife.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.aitp.dlife.domain.enumeration.PointEventType;

/**
 * A DTO for the UserPointDetails entity.
 */
public class UserPointDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String userid;

    @NotNull
    private ZonedDateTime applyTime;

    @NotNull
    private Integer changePoint;

    private PointEventType eventType;

    @NotNull
    private String descript;

    private String targetSystem;

    private String handleBy;

    private Integer totalPoint;

    private String validateString;

    private String createBy;

    private ZonedDateTime createTime;

    private String lastModifyBy;

    private ZonedDateTime lastModifyTime;

    private String eventName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ZonedDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(ZonedDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getChangePoint() {
        return changePoint;
    }

    public void setChangePoint(Integer changePoint) {
        this.changePoint = changePoint;
    }

    public PointEventType getEventType() {
        return eventType;
    }

    public void setEventType(PointEventType eventType) {
        this.eventType = eventType;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    public String getHandleBy() {
        return handleBy;
    }

    public void setHandleBy(String handleBy) {
        this.handleBy = handleBy;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getValidateString() {
        return validateString;
    }

    public void setValidateString(String validateString) {
        this.validateString = validateString;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPointDetailsDTO userPointDetailsDTO = (UserPointDetailsDTO) o;
        if (userPointDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPointDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPointDetailsDTO{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            ", applyTime='" + getApplyTime() + "'" +
            ", changePoint=" + getChangePoint() +
            ", eventType='" + getEventType() + "'" +
            ", descript='" + getDescript() + "'" +
            ", targetSystem='" + getTargetSystem() + "'" +
            ", handleBy='" + getHandleBy() + "'" +
            ", totalPoint=" + getTotalPoint() +
            ", validateString='" + getValidateString() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            ", eventName='" + getEventName() + "'" +
            "}";
    }
}
