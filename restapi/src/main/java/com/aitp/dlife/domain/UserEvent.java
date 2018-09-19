package com.aitp.dlife.domain;

import com.aitp.dlife.domain.enumeration.PointEventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserEvent.
 */
@Entity
@Table(name = "user_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid")
    private String userid;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "target_system")
    private String targetSystem;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private PointEventType eventType;
    
    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_time")
    private ZonedDateTime eventTime;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "validate_to")
    private ZonedDateTime validateTo;

    @Column(name = "param_1")
    private Integer param1;

    @Column(name = "parem_2")
    private String parem2;

    @Column(name = "param_3")
    private String param3;

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
    private TaskDefine applyTask;

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

    public UserEvent userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUuid() {
        return uuid;
    }

    public UserEvent uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public UserEvent targetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
        return this;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    public PointEventType getEventType() {
        return eventType;
    }

    public UserEvent eventType(PointEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(PointEventType eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public UserEvent eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    public ZonedDateTime getEventTime() {
        return eventTime;
    }

    public UserEvent eventTime(ZonedDateTime eventTime) {
        this.eventTime = eventTime;
        return this;
    }

    public void setEventTime(ZonedDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public Boolean isStatus() {
        return status;
    }

    public UserEvent status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ZonedDateTime getValidateTo() {
        return validateTo;
    }

    public UserEvent validateTo(ZonedDateTime validateTo) {
        this.validateTo = validateTo;
        return this;
    }

    public void setValidateTo(ZonedDateTime validateTo) {
        this.validateTo = validateTo;
    }

    public Integer getParam1() {
        return param1;
    }

    public UserEvent param1(Integer param1) {
        this.param1 = param1;
        return this;
    }

    public void setParam1(Integer param1) {
        this.param1 = param1;
    }

    public String getParem2() {
        return parem2;
    }

    public UserEvent parem2(String parem2) {
        this.parem2 = parem2;
        return this;
    }

    public void setParem2(String parem2) {
        this.parem2 = parem2;
    }
    
    public String getParam3() {
        return param3;
    }

    public UserEvent param3(String param3) {
        this.param3 = param3;
        return this;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getCreateBy() {
        return createBy;
    }

    public UserEvent createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public UserEvent createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public UserEvent lastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
        return this;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public UserEvent lastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
        return this;
    }

    public void setLastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public TaskDefine getApplyTask() {
        return applyTask;
    }

    public UserEvent applyTask(TaskDefine taskDefine) {
        this.applyTask = taskDefine;
        return this;
    }

    public void setApplyTask(TaskDefine taskDefine) {
        this.applyTask = taskDefine;
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
        UserEvent userEvent = (UserEvent) o;
        if (userEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserEvent{" +
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
            "}";
    }
}
