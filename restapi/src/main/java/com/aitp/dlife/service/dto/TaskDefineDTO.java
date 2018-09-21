package com.aitp.dlife.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;

import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.domain.enumeration.TaskPeriod;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaskDefine entity.
 */
public class TaskDefineDTO implements Serializable {

    private Long id;

    @NotNull
    private String taskCode;

    @NotNull
    private String name;

    @NotNull
    private Boolean status;

    private TaskPeriod period;
    
    @NotNull
    private String priority;

    private Integer maxlimit;

    private Integer totalPoint;

    private String targetSystems;

    private PointEventType eventType;

    private String conditions;

    private String strategy;

    private String applyStrategy;

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

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TaskPeriod getPeriod() {
        return period;
    }

    public void setPeriod(TaskPeriod period) {
        this.period = period;
    }

    public Integer getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(Integer maxlimit) {
        this.maxlimit = maxlimit;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getTargetSystems() {
        return targetSystems;
    }

    public void setTargetSystems(String targetSystems) {
        this.targetSystems = targetSystems;
    }

    public PointEventType getEventType() {
        return eventType;
    }

    public void setEventType(PointEventType eventType) {
        this.eventType = eventType;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getApplyStrategy() {
        return applyStrategy;
    }

    public void setApplyStrategy(String applyStrategy) {
        this.applyStrategy = applyStrategy;
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

        TaskDefineDTO taskDefineDTO = (TaskDefineDTO) o;
        if (taskDefineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskDefineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskDefineDTO{" +
            "id=" + getId() +
            ", taskCode='" + getTaskCode() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + isStatus() + "'" +
            ", period='" + getPeriod() + "'" +
            ", priority='" + getPriority() + "'" +
            ", maxlimit=" + getMaxlimit() +
            ", totalPoint=" + getTotalPoint() +
            ", targetSystems='" + getTargetSystems() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", conditions='" + getConditions() + "'" +
            ", strategy='" + getStrategy() + "'" +
            ", applyStrategy='" + getApplyStrategy() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            "}";
    }

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
}
