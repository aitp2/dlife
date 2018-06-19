package com.aitp.dlife.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PinfanPics.
 */
@Entity
@Table(name = "pinfan_pics")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class PinfanPics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图片路径
     */
    @Size(max = 255)
    @ApiModelProperty(value = "图片路径")
    @Column(name = "oss_path", length = 255)
    private String ossPath;

    @Column(name = "create_time")
    private Instant createTime;

    @ManyToOne
    private PinFanActivity pinFanActivity;

    @ManyToOne
    private Rates rate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOssPath() {
        return ossPath;
    }

    public PinfanPics ossPath(String ossPath) {
        this.ossPath = ossPath;
        return this;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public PinfanPics createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public PinFanActivity getPinFanActivity() {
        return pinFanActivity;
    }

    public PinfanPics pinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
        return this;
    }

    public void setPinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
    }

    public Rates getRate() {
        return rate;
    }

    public PinfanPics rate(Rates rates) {
        this.rate = rates;
        return this;
    }

    public void setRate(Rates rates) {
        this.rate = rates;
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
        PinfanPics pinfanPics = (PinfanPics) o;
        if (pinfanPics.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinfanPics.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PinfanPics{" +
            "id=" + getId() +
            ", ossPath='" + getOssPath() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
