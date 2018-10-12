package com.aitp.dlife.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TaskGroup.
 */
@Entity
@Table(name = "task_group")
public class TaskGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "max_points", nullable = false)
    private Integer maxPoints;

    @NotNull
    @Column(name = "max_count", nullable = false)
    private Integer maxCount;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "last_modify_by")
    private String lastModifyBy;

    @Column(name = "last_modify_time")
    private ZonedDateTime lastModifyTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public TaskGroup groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public TaskGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public TaskGroup maxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
        return this;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public TaskGroup maxCount(Integer maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public String getCreateBy() {
        return createBy;
    }

    public TaskGroup createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public TaskGroup createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public TaskGroup lastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
        return this;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public TaskGroup lastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        return this;
    }

    public void setLastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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
        TaskGroup taskGroup = (TaskGroup) o;
        if (taskGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskGroup{" +
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
