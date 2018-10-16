package com.aitp.dlife.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SystemTotalPoints.
 */
@Entity
@Table(name = "system_total_points")
public class SystemTotalPoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "system_id", nullable = false)
    private String systemId;

    @NotNull
    @Column(name = "total_point", nullable = false)
    private Integer totalPoint;

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

    public String getSystemId() {
        return systemId;
    }

    public SystemTotalPoints systemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public SystemTotalPoints totalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
        return this;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getCreateBy() {
        return createBy;
    }

    public SystemTotalPoints createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public SystemTotalPoints createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public SystemTotalPoints lastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
        return this;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public SystemTotalPoints lastModifyTime(ZonedDateTime lastModifyTime) {
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
        SystemTotalPoints systemTotalPoints = (SystemTotalPoints) o;
        if (systemTotalPoints.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemTotalPoints.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemTotalPoints{" +
            "id=" + getId() +
            ", systemId='" + getSystemId() + "'" +
            ", totalPoint=" + getTotalPoint() +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            "}";
    }
}
