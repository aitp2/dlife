package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
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
     * 头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型")
    @Column(name = "activitiy_type")
    private Integer activitiyType;

    /**
     * 活动名称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "活动名称")
    @Column(name = "activitiy_tile", length = 128)
    private String activitiyTile;

    /**
     * 人均预算
     */
    @ApiModelProperty(value = "人均预算")
    @Column(name = "budget", precision = 10, scale = 2)
    private BigDecimal budget;

    /**
     * 活动地址
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "活动地址")
    @Column(name = "activitiy_addre", length = 1024)
    private String activitiyAddre;

    /**
     * 活动描述
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "活动描述")
    @Column(name = "descrption", length = 1024)
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
     * 约定结束时间
     */
    @ApiModelProperty(value = "约定结束时间")
    @Column(name = "appoint_end_datetime")
    private Instant appointEndDatetime;

    /**
     * 商家URL
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "商家URL")
    @Column(name = "saler_url", length = 1024)
    private String salerUrl;

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
     * 活动状态 0-新建 1-完成 2-取消
     */
    @ApiModelProperty(value = "活动状态 0-新建 1-完成 2-取消")
    @Column(name = "status")
    private Integer status;

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量")
    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "reading_count")
    private Integer readingCount;

    @OneToMany(mappedBy = "pinFanActivity")
    private Set<Attendee> attendees = new HashSet<>();

    @OneToMany(mappedBy = "pinFanActivity")
    private Set<PinfanPics> pinfanPics = new HashSet<>();

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

    public String getAvatar() {
        return avatar;
    }

    public PinFanActivity avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public PinFanActivity nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getActivitiyTile() {
        return activitiyTile;
    }

    public PinFanActivity activitiyTile(String activitiyTile) {
        this.activitiyTile = activitiyTile;
        return this;
    }

    public void setActivitiyTile(String activitiyTile) {
        this.activitiyTile = activitiyTile;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public PinFanActivity budget(BigDecimal budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getActivitiyAddre() {
        return activitiyAddre;
    }

    public PinFanActivity activitiyAddre(String activitiyAddre) {
        this.activitiyAddre = activitiyAddre;
        return this;
    }

    public void setActivitiyAddre(String activitiyAddre) {
        this.activitiyAddre = activitiyAddre;
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

    public Instant getAppointEndDatetime() {
        return appointEndDatetime;
    }

    public PinFanActivity appointEndDatetime(Instant appointEndDatetime) {
        this.appointEndDatetime = appointEndDatetime;
        return this;
    }

    public void setAppointEndDatetime(Instant appointEndDatetime) {
        this.appointEndDatetime = appointEndDatetime;
    }

    public String getSalerUrl() {
        return salerUrl;
    }

    public PinFanActivity salerUrl(String salerUrl) {
        this.salerUrl = salerUrl;
        return this;
    }

    public void setSalerUrl(String salerUrl) {
        this.salerUrl = salerUrl;
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

    public Integer getStatus() {
        return status;
    }

    public PinFanActivity status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public PinFanActivity commentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getReadingCount() {
        return readingCount;
    }

    public PinFanActivity readingCount(Integer readingCount) {
        this.readingCount = readingCount;
        return this;
    }

    public void setReadingCount(Integer readingCount) {
        this.readingCount = readingCount;
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

    public Set<PinfanPics> getPinfanPics() {
        return pinfanPics;
    }

    public PinFanActivity pinfanPics(Set<PinfanPics> pinfanPics) {
        this.pinfanPics = pinfanPics;
        return this;
    }

    public PinFanActivity addPinfanPics(PinfanPics pinfanPics) {
        this.pinfanPics.add(pinfanPics);
        pinfanPics.setPinFanActivity(this);
        return this;
    }

    public PinFanActivity removePinfanPics(PinfanPics pinfanPics) {
        this.pinfanPics.remove(pinfanPics);
        pinfanPics.setPinFanActivity(null);
        return this;
    }

    public void setPinfanPics(Set<PinfanPics> pinfanPics) {
        this.pinfanPics = pinfanPics;
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
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", activitiyType=" + getActivitiyType() +
            ", activitiyTile='" + getActivitiyTile() + "'" +
            ", budget=" + getBudget() +
            ", activitiyAddre='" + getActivitiyAddre() + "'" +
            ", descrption='" + getDescrption() + "'" +
            ", organizeUser='" + getOrganizeUser() + "'" +
            ", coverPicture='" + getCoverPicture() + "'" +
            ", appointDatetime='" + getAppointDatetime() + "'" +
            ", appointEndDatetime='" + getAppointEndDatetime() + "'" +
            ", salerUrl='" + getSalerUrl() + "'" +
            ", lowerLimit=" + getLowerLimit() +
            ", upperLimit=" + getUpperLimit() +
            ", payType='" + getPayType() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", comment='" + getComment() + "'" +
            ", status=" + getStatus() +
            ", commentCount=" + getCommentCount() +
            ", readingCount=" + getReadingCount() +
            "}";
    }
}
