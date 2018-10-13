package com.aitp.dlife.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserTask entity.
 */
public class UserTaskDTO implements Serializable {

    private Long id;

    private String userid;

    private Integer taskStatus;

    private Integer gainPoint;

    private Integer remainPoint;

    private ZonedDateTime validateTo;

    private String createBy;

    private ZonedDateTime createTime;

    private String lastModifyBy;

    private ZonedDateTime lastModifyTime;

    private String groupid;

    private String groupName;

    private Long taskId;

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

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getGainPoint() {
        return gainPoint;
    }

    public void setGainPoint(Integer gainPoint) {
        this.gainPoint = gainPoint;
    }

    public Integer getRemainPoint() {
        return remainPoint;
    }

    public void setRemainPoint(Integer remainPoint) {
        this.remainPoint = remainPoint;
    }

    public ZonedDateTime getValidateTo() {
        return validateTo;
    }

    public void setValidateTo(ZonedDateTime validateTo) {
        this.validateTo = validateTo;
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

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskDefineId) {
        this.taskId = taskDefineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserTaskDTO userTaskDTO = (UserTaskDTO) o;
        if (userTaskDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userTaskDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserTaskDTO{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            ", taskStatus=" + getTaskStatus() +
            ", gainPoint=" + getGainPoint() +
            ", remainPoint=" + getRemainPoint() +
            ", validateTo='" + getValidateTo() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            ", groupid='" + getGroupid() + "'" +
            ", groupName='" + getGroupName() + "'" +
            ", task=" + getTaskId() +
            "}";
    }
}
