package com.aitp.dlife.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.PointEventType;

/**
 * A DTO for the UserEvent entity.
 */
public class UserEventDTO implements Serializable {

    private Long id;

    private String userid;

    private String uuid;

    private String targetSystem;

    private PointEventType eventType;

	private String eventName;

    private ZonedDateTime eventTime;

    private Boolean status;

    private ZonedDateTime validateTo;

    private Integer param1;

    private String parem2;

    private String param3;

    private String createBy;

    private ZonedDateTime createTime;

    private String lastModifyBy;

    private ZonedDateTime lastModifyTime;

    private Long applyTaskId;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    public PointEventType getEventType() {
        return eventType;
    }

    public void setEventType(PointEventType eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
    public ZonedDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(ZonedDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ZonedDateTime getValidateTo() {
        return validateTo;
    }

    public void setValidateTo(ZonedDateTime validateTo) {
        this.validateTo = validateTo;
    }

    public Integer getParam1() {
        return param1;
    }

    public void setParam1(Integer param1) {
        this.param1 = param1;
    }

    public String getParem2() {
        return parem2;
    }

    public void setParem2(String parem2) {
        this.parem2 = parem2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
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

    public Long getApplyTaskId() {
        return applyTaskId;
    }

    public void setApplyTaskId(Long taskDefineId) {
        this.applyTaskId = taskDefineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEventDTO userEventDTO = (UserEventDTO) o;
        if (userEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userEventDTO.getId());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserEventDTO{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", targetSystem='" + getTargetSystem() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", eventName='" + getEventName() + "'" +
            ", eventTime='" + getEventTime() + "'" +
            ", status='" + isStatus() + "'" +
            ", validateTo='" + getValidateTo() + "'" +
            ", param1=" + getParam1() +
            ", parem2='" + getParem2() + "'" +
            ", param3='" + getParam3() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            ", applyTask=" + getApplyTaskId() +
            "}";
    }
}
