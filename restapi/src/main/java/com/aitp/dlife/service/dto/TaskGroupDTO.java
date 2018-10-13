package com.aitp.dlife.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaskGroup entity.
 */
public class TaskGroupDTO implements Serializable {

    private Long id;

    private String groupName;

    private String description;

    @NotNull
    private Integer maxPoints;

    @NotNull
    private Integer maxCount;

    private String createBy;

    private ZonedDateTime createTime;

    private String lastModifyBy;

    private ZonedDateTime lastModifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskGroupDTO taskGroupDTO = (TaskGroupDTO) o;
        if (taskGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskGroupDTO{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", description='" + getDescription() + "'" +
            ", maxPoints=" + getMaxPoints() +
            ", maxCount=" + getMaxCount() +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            "}";
    }
}
