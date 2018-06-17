package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 拼饭活动
 */
@ApiModel(description = "拼饭活动")
@Entity
@Table(name = "pin_fan_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PinFanActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 发布者id
     */
    @Size(max = 128)
    @ApiModelProperty(value = "发布者id")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型")
    @Column(name = "activitiy_type")
    private Integer activitiyType;

    /**
     * 活动描述
     */
    @Size(max = 128)
    @ApiModelProperty(value = "活动描述")
    @Column(name = "descrption", length = 128)
    private String descrption;

    /**
     * 组织者
     */
    @Size(max = 128)
    @ApiModelProperty(value = "组织者")
    @Column(name = "organize_user", length = 128)
    private String organizeUser;

    /**
     * 封面图
     */
    @Size(max = 128)
    @ApiModelProperty(value = "封面图")
    @Column(name = "cover_picture", length = 128)
    private String coverPicture;

    /**
     * 约定时间
     */
    @ApiModelProperty(value = "约定时间")
    @Column(name = "appoint_datetime")
    private Instant appointDatetime;

    /**
     * 最少参与者
     */
    @ApiModelProperty(value = "最少参与者")
    @Column(name = "lower_limit")
    private Integer lowerLimit;

    /**
     * 最多参与者
     */
    @ApiModelProperty(value = "最多参与者")
    @Column(name = "upper_limit")
    private Integer upperLimit;

    /**
     * 支付类型
     */
    @Size(max = 32)
    @ApiModelProperty(value = "支付类型")
    @Column(name = "pay_type", length = 32)
    private String payType;

    /**
     * 截至日期
     */
    @ApiModelProperty(value = "截至日期")
    @Column(name = "deadline")
    private Instant deadline;

    /**
     * 备注
     */
    @Size(max = 500)
    @ApiModelProperty(value = "备注")
    @Column(name = "jhi_comment", length = 500)
    private String comment;

    /**
     * 是否生效
     */
    @ApiModelProperty(value = "是否生效")
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "pinFanActivity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attendee> attendees = new HashSet<>();

    @OneToMany(mappedBy = "pinFanActivity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PinfanPics> rinfanPics = new HashSet<>();

    @OneToMany(mappedBy = "pinFanActivity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rates> rates = new HashSet<>();

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

    public PinFanActivity wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Integer getActivitiyType() {
        return activitiyType;
    }

    public PinFanActivity activitiyType(Integer activitiyType) {
        this.activitiyType = activitiyType;
        return this;
    }

    public void setActivitiyType(Integer activitiyType) {
        this.activitiyType = activitiyType;
    }

    public String getDescrption() {
        return descrption;
    }

    public PinFanActivity descrption(String descrption) {
        this.descrption = descrption;
        return this;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getOrganizeUser() {
        return organizeUser;
    }

    public PinFanActivity organizeUser(String organizeUser) {
        this.organizeUser = organizeUser;
        return this;
    }

    public void setOrganizeUser(String organizeUser) {
        this.organizeUser = organizeUser;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public PinFanActivity coverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
        return this;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Instant getAppointDatetime() {
        return appointDatetime;
    }

    public PinFanActivity appointDatetime(Instant appointDatetime) {
        this.appointDatetime = appointDatetime;
        return this;
    }

    public void setAppointDatetime(Instant appointDatetime) {
        this.appointDatetime = appointDatetime;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public PinFanActivity lowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
        return this;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public PinFanActivity upperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
        return this;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getPayType() {
        return payType;
    }

    public PinFanActivity payType(String payType) {
        this.payType = payType;
        return this;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public PinFanActivity deadline(Instant deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public String getComment() {
        return comment;
    }

    public PinFanActivity comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public PinFanActivity isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Attendee> getAttendees() {
        return attendees;
    }

    public PinFanActivity attendees(Set<Attendee> attendees) {
        this.attendees = attendees;
        return this;
    }

    public PinFanActivity addAttendees(Attendee attendee) {
        this.attendees.add(attendee);
        attendee.setPinFanActivity(this);
        return this;
    }

    public PinFanActivity removeAttendees(Attendee attendee) {
        this.attendees.remove(attendee);
        attendee.setPinFanActivity(null);
        return this;
    }

    public void setAttendees(Set<Attendee> attendees) {
        this.attendees = attendees;
    }

    public Set<PinfanPics> getRinfanPics() {
        return rinfanPics;
    }

    public PinFanActivity rinfanPics(Set<PinfanPics> pinfanPics) {
        this.rinfanPics = pinfanPics;
        return this;
    }

    public PinFanActivity addRinfanPics(PinfanPics pinfanPics) {
        this.rinfanPics.add(pinfanPics);
        pinfanPics.setPinFanActivity(this);
        return this;
    }

    public PinFanActivity removeRinfanPics(PinfanPics pinfanPics) {
        this.rinfanPics.remove(pinfanPics);
        pinfanPics.setPinFanActivity(null);
        return this;
    }

    public void setRinfanPics(Set<PinfanPics> pinfanPics) {
        this.rinfanPics = pinfanPics;
    }

    public Set<Rates> getRates() {
        return rates;
    }

    public PinFanActivity rates(Set<Rates> rates) {
        this.rates = rates;
        return this;
    }

    public PinFanActivity addRates(Rates rates) {
        this.rates.add(rates);
        rates.setPinFanActivity(this);
        return this;
    }

    public PinFanActivity removeRates(Rates rates) {
        this.rates.remove(rates);
        rates.setPinFanActivity(null);
        return this;
    }

    public void setRates(Set<Rates> rates) {
        this.rates = rates;
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
        PinFanActivity pinFanActivity = (PinFanActivity) o;
        if (pinFanActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinFanActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PinFanActivity{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", activitiyType=" + getActivitiyType() +
            ", descrption='" + getDescrption() + "'" +
            ", organizeUser='" + getOrganizeUser() + "'" +
            ", coverPicture='" + getCoverPicture() + "'" +
            ", appointDatetime='" + getAppointDatetime() + "'" +
            ", lowerLimit=" + getLowerLimit() +
            ", upperLimit=" + getUpperLimit() +
            ", payType='" + getPayType() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", comment='" + getComment() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
