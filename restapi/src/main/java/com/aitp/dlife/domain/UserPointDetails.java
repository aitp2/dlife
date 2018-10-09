package com.aitp.dlife.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.PointEventType;

/**
 * A UserPointDetails.
 */
@Entity
@Table(name = "user_point_details")
public class UserPointDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "userid", nullable = false)
    private String userid;

    @NotNull
    @Column(name = "apply_time", nullable = false)
    private ZonedDateTime applyTime;

    @NotNull
    @Column(name = "change_point", nullable = false)
    private Integer changePoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private PointEventType eventType;

    @NotNull
    @Column(name = "descript", nullable = false)
    private String descript;

    @Column(name = "target_system")
    private String targetSystem;

    @Column(name = "handle_by")
    private String handleBy;

    @Column(name = "total_point")
    private Integer totalPoint;

    @Column(name = "validate_string")
    private String validateString;

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

    public String getUserid() {
        return userid;
    }

    public UserPointDetails userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ZonedDateTime getApplyTime() {
        return applyTime;
    }

    public UserPointDetails applyTime(ZonedDateTime applyTime) {
        this.applyTime = applyTime;
        return this;
    }

    public void setApplyTime(ZonedDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getChangePoint() {
        return changePoint;
    }

    public UserPointDetails changePoint(Integer changePoint) {
        this.changePoint = changePoint;
        return this;
    }

    public void setChangePoint(Integer changePoint) {
        this.changePoint = changePoint;
    }

    public PointEventType getEventType() {
        return eventType;
    }

    public UserPointDetails eventType(PointEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(PointEventType eventType) {
        this.eventType = eventType;
    }

    public String getDescript() {
        return descript;
    }

    public UserPointDetails descript(String descript) {
        this.descript = descript;
        return this;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public UserPointDetails targetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
        return this;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    public String getHandleBy() {
        return handleBy;
    }

    public UserPointDetails handleBy(String handleBy) {
        this.handleBy = handleBy;
        return this;
    }

    public void setHandleBy(String handleBy) {
        this.handleBy = handleBy;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public UserPointDetails totalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
        return this;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getValidateString() {
        return validateString;
    }

    public UserPointDetails validateString(String validateString) {
        this.validateString = validateString;
        return this;
    }

    public void setValidateString(String validateString) {
        this.validateString = validateString;
    }

    public String getCreateBy() {
        return createBy;
    }

    public UserPointDetails createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public UserPointDetails createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public UserPointDetails lastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
        return this;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public UserPointDetails lastModifyTime(ZonedDateTime lastModifyTime) {
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
        UserPointDetails userPointDetails = (UserPointDetails) o;
        if (userPointDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPointDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPointDetails{" +
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
            "}";
    }
}
