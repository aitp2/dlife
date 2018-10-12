package com.aitp.dlife.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.TaskPeriod;

import com.aitp.dlife.domain.enumeration.PointEventType;

/**
 * A TaskDefine.
 */
@Entity
@Table(name = "task_define")
public class TaskDefine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task_code", nullable = false)
    private String taskCode;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "period")
    private TaskPeriod period;

    @Column(name = "maxlimit")
    private Integer maxlimit;

    @Column(name = "total_point")
    private Integer totalPoint;

    @Column(name = "target_systems")
    private String targetSystems;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private PointEventType eventType;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "strategy")
    private String strategy;

    @Column(name = "apply_strategy")
    private String applyStrategy;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "last_modify_by")
    private String lastModifyBy;

    @Column(name = "last_modify_time")
    private ZonedDateTime lastModifyTime;

    @Column(name = "groupid")
    private String groupid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public TaskDefine taskCode(String taskCode) {
        this.taskCode = taskCode;
        return this;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getName() {
        return name;
    }

    public TaskDefine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isStatus() {
        return status;
    }

    public TaskDefine status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TaskPeriod getPeriod() {
        return period;
    }

    public TaskDefine period(TaskPeriod period) {
        this.period = period;
        return this;
    }

    public void setPeriod(TaskPeriod period) {
        this.period = period;
    }

    public Integer getMaxlimit() {
        return maxlimit;
    }

    public TaskDefine maxlimit(Integer maxlimit) {
        this.maxlimit = maxlimit;
        return this;
    }

    public void setMaxlimit(Integer maxlimit) {
        this.maxlimit = maxlimit;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public TaskDefine totalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
        return this;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getTargetSystems() {
        return targetSystems;
    }

    public TaskDefine targetSystems(String targetSystems) {
        this.targetSystems = targetSystems;
        return this;
    }

    public void setTargetSystems(String targetSystems) {
        this.targetSystems = targetSystems;
    }

    public PointEventType getEventType() {
        return eventType;
    }

    public TaskDefine eventType(PointEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(PointEventType eventType) {
        this.eventType = eventType;
    }

    public String getConditions() {
        return conditions;
    }

    public TaskDefine conditions(String conditions) {
        this.conditions = conditions;
        return this;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getStrategy() {
        return strategy;
    }

    public TaskDefine strategy(String strategy) {
        this.strategy = strategy;
        return this;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getApplyStrategy() {
        return applyStrategy;
    }

    public TaskDefine applyStrategy(String applyStrategy) {
        this.applyStrategy = applyStrategy;
        return this;
    }

    public void setApplyStrategy(String applyStrategy) {
        this.applyStrategy = applyStrategy;
    }

    public Integer getPriority() {
        return priority;
    }

    public TaskDefine priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCreateBy() {
        return createBy;
    }

    public TaskDefine createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public TaskDefine createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public TaskDefine lastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
        return this;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public TaskDefine lastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        return this;
    }

    public void setLastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getGroupid() {
        return groupid;
    }

    public TaskDefine groupid(String groupid) {
        this.groupid = groupid;
        return this;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskDefine taskDefine = (TaskDefine) o;
        if (taskDefine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskDefine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskDefine{" +
            "id=" + getId() +
            ", taskCode='" + getTaskCode() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + isStatus() + "'" +
            ", period='" + getPeriod() + "'" +
            ", maxlimit=" + getMaxlimit() +
            ", totalPoint=" + getTotalPoint() +
            ", targetSystems='" + getTargetSystems() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", conditions='" + getConditions() + "'" +
            ", strategy='" + getStrategy() + "'" +
            ", applyStrategy='" + getApplyStrategy() + "'" +
            ", priority=" + getPriority() +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            ", groupid='" + getGroupid() + "'" +
            "}";
    }
}
