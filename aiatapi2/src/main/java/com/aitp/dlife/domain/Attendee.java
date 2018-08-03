package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 拼饭活动参与信息
 */
@ApiModel(description = "拼饭活动参与信息")
@Entity
@Table(name = "attendee")
public class Attendee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 参与人ID
     */
    @Size(max = 128)
    @ApiModelProperty(value = "参与人ID")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 参与者昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "参与者昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 参与者头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "参与者头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 参与时间
     */
    @ApiModelProperty(value = "参与时间")
    @Column(name = "participation_time")
    private Instant participationTime;

    /**
     * 活动名称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "活动名称")
    @Column(name = "activitiy_tile", length = 128)
    private String activitiyTile;

    @ManyToOne
    @JsonIgnoreProperties("attendees")
    private PinFanActivity pinFanActivity;

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

    public Attendee wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public Attendee nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public Attendee avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Instant getParticipationTime() {
        return participationTime;
    }

    public Attendee participationTime(Instant participationTime) {
        this.participationTime = participationTime;
        return this;
    }

    public void setParticipationTime(Instant participationTime) {
        this.participationTime = participationTime;
    }

    public String getActivitiyTile() {
        return activitiyTile;
    }

    public Attendee activitiyTile(String activitiyTile) {
        this.activitiyTile = activitiyTile;
        return this;
    }

    public void setActivitiyTile(String activitiyTile) {
        this.activitiyTile = activitiyTile;
    }

    public PinFanActivity getPinFanActivity() {
        return pinFanActivity;
    }

    public Attendee pinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
        return this;
    }

    public void setPinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
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
        Attendee attendee = (Attendee) o;
        if (attendee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attendee{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", participationTime='" + getParticipationTime() + "'" +
            ", activitiyTile='" + getActivitiyTile() + "'" +
            "}";
    }
}
