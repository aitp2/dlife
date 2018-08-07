package com.aitp.dlife.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.ThumbsUpChannel;

import com.aitp.dlife.domain.enumeration.ThumbsUpModule;

/**
 * 点赞信息
 */
@ApiModel(description = "点赞信息")
@Entity
@Table(name = "thumbs_up")
public class ThumbsUp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 点赞对象id
     */
    @ApiModelProperty(value = "点赞对象id")
    @Column(name = "object_id")
    private Long objectId;

    /**
     * 点赞渠道
     */
    @ApiModelProperty(value = "点赞渠道")
    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private ThumbsUpChannel channel;

    /**
     * 点赞模块
     */
    @ApiModelProperty(value = "点赞模块")
    @Enumerated(EnumType.STRING)
    @Column(name = "module")
    private ThumbsUpModule module;

    /**
     * 点赞人id
     */
    @Size(max = 128)
    @ApiModelProperty(value = "点赞人id")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 点赞人头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "点赞人头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 点赞人昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "点赞人昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Instant createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectId() {
        return objectId;
    }

    public ThumbsUp objectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public ThumbsUpChannel getChannel() {
        return channel;
    }

    public ThumbsUp channel(ThumbsUpChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(ThumbsUpChannel channel) {
        this.channel = channel;
    }

    public ThumbsUpModule getModule() {
        return module;
    }

    public ThumbsUp module(ThumbsUpModule module) {
        this.module = module;
        return this;
    }

    public void setModule(ThumbsUpModule module) {
        this.module = module;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public ThumbsUp wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public ThumbsUp avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public ThumbsUp nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public ThumbsUp createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
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
        ThumbsUp thumbsUp = (ThumbsUp) o;
        if (thumbsUp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thumbsUp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThumbsUp{" +
            "id=" + getId() +
            ", objectId=" + getObjectId() +
            ", channel='" + getChannel() + "'" +
            ", module='" + getModule() + "'" +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
