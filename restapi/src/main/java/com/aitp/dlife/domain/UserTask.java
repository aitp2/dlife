package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserTask.
 */
@Entity
@Table(name = "user_task")
public class UserTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private String userid;

    @Column(name = "task_status")
    private Integer taskStatus;

    @Column(name = "gain_point")
    private Integer gainPoint;

    @Column(name = "remain_point")
    private Integer remainPoint;

    @Column(name = "validate_to")
    private ZonedDateTime validateTo;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "last_modify_by")
    private String lastModifyBy;

    @Column(name = "last_modify_time")
    private ZonedDateTime lastModifyTime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TaskDefine task;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public UserTask userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public UserTask taskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getGainPoint() {
        return gainPoint;
    }

    public UserTask gainPoint(Integer gainPoint) {
        this.gainPoint = gainPoint;
        return this;
    }

    public void setGainPoint(Integer gainPoint) {
        this.gainPoint = gainPoint;
    }

    public Integer getRemainPoint() {
        return remainPoint;
    }

    public UserTask remainPoint(Integer remainPoint) {
        this.remainPoint = remainPoint;
        return this;
    }

    public void setRemainPoint(Integer remainPoint) {
        this.remainPoint = remainPoint;
    }

    public ZonedDateTime getValidateTo() {
        return validateTo;
    }

    public UserTask validateTo(ZonedDateTime validateTo) {
        this.validateTo = validateTo;
        return this;
    }

    public void setValidateTo(ZonedDateTime validateTo) {
        this.validateTo = validateTo;
    }

    public String getCreateBy() {
        return createBy;
    }

    public UserTask createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public UserTask createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public UserTask lastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
        return this;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public UserTask lastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        return this;
    }

    public void setLastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public TaskDefine getTask() {
        return task;
    }

    public UserTask task(TaskDefine taskDefine) {
        this.task = taskDefine;
        return this;
    }

    public void setTask(TaskDefine taskDefine) {
        this.task = taskDefine;
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
        UserTask userTask = (UserTask) o;
        if (userTask.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserTask{" +
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
            "}";
    }
}
