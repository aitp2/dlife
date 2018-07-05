package com.aitp.dlife.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 打卡汇总表
 */
@ApiModel(description = "打卡汇总表")
@Entity
@Table(name = "clockin_summary")
public class ClockinSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    @Size(max = 128)
    @ApiModelProperty(value = "用户id")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 总打卡天数
     */
    @ApiModelProperty(value = "总打卡天数")
    @Column(name = "totally_count")
    private Integer totallyCount;

    /**
     * 周打卡天数
     */
    @ApiModelProperty(value = "周打卡天数")
    @Column(name = "weekly_count")
    private Integer weeklyCount;

    /**
     * 连续打卡天数
     */
    @ApiModelProperty(value = "连续打卡天数")
    @Column(name = "serial_count")
    private Integer serialCount;

    /**
     * 最新打卡时间
     */
    @ApiModelProperty(value = "最新打卡时间")
    @Column(name = "last_clock_in_time")
    private Instant lastClockInTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public ClockinSummary wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Integer getTotallyCount() {
        return totallyCount;
    }

    public ClockinSummary totallyCount(Integer totallyCount) {
        this.totallyCount = totallyCount;
        return this;
    }

    public void setTotallyCount(Integer totallyCount) {
        this.totallyCount = totallyCount;
    }

    public Integer getWeeklyCount() {
        return weeklyCount;
    }

    public ClockinSummary weeklyCount(Integer weeklyCount) {
        this.weeklyCount = weeklyCount;
        return this;
    }

    public void setWeeklyCount(Integer weeklyCount) {
        this.weeklyCount = weeklyCount;
    }

    public Integer getSerialCount() {
        return serialCount;
    }

    public ClockinSummary serialCount(Integer serialCount) {
        this.serialCount = serialCount;
        return this;
    }

    public void setSerialCount(Integer serialCount) {
        this.serialCount = serialCount;
    }

    public Instant getLastClockInTime() {
        return lastClockInTime;
    }

    public ClockinSummary lastClockInTime(Instant lastClockInTime) {
        this.lastClockInTime = lastClockInTime;
        return this;
    }

    public void setLastClockInTime(Instant lastClockInTime) {
        this.lastClockInTime = lastClockInTime;
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
        ClockinSummary clockinSummary = (ClockinSummary) o;
        if (clockinSummary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clockinSummary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClockinSummary{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", totallyCount=" + getTotallyCount() +
            ", weeklyCount=" + getWeeklyCount() +
            ", serialCount=" + getSerialCount() +
            ", lastClockInTime='" + getLastClockInTime() + "'" +
            "}";
    }
}
