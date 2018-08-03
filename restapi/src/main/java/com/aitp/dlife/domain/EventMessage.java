package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.EventType;

import com.aitp.dlife.domain.enumeration.EventChannel;

/**
 * 事件消息
 */
@ApiModel(description = "事件消息")
@Entity
@Table(name = "event_message")
public class EventMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 触发人id
     */
    @Size(max = 128)
    @ApiModelProperty(value = "触发人id")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 触发人头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "触发人头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 触发人昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "触发人昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 事件类型
     */
    @ApiModelProperty(value = "事件类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private EventType type;

    /**
     * 事件渠道
     */
    @ApiModelProperty(value = "事件渠道")
    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private EventChannel channel;

    /**
     * 触发对象id
     */
    @ApiModelProperty(value = "触发对象id")
    @Column(name = "object_id")
    private Long objectId;

    /**
     * 触发对象标题
     */
    @Size(max = 256)
    @ApiModelProperty(value = "触发对象标题")
    @Column(name = "object_title", length = 256)
    private String objectTitle;

    /**
     * 触发时间
     */
    @ApiModelProperty(value = "触发时间")
    @Column(name = "create_time")
    private Instant createTime;

    @OneToMany(mappedBy = "eventMessage")
    private Set<Message> messages = new HashSet<>();

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

    public EventMessage wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public EventMessage avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public EventMessage nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public EventType getType() {
        return type;
    }

    public EventMessage type(EventType type) {
        this.type = type;
        return this;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventChannel getChannel() {
        return channel;
    }

    public EventMessage channel(EventChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(EventChannel channel) {
        this.channel = channel;
    }

    public Long getObjectId() {
        return objectId;
    }

    public EventMessage objectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public EventMessage objectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
        return this;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public EventMessage createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public EventMessage messages(Set<Message> messages) {
        this.messages = messages;
        return this;
    }

    public EventMessage addMessages(Message message) {
        this.messages.add(message);
        message.setEventMessage(this);
        return this;
    }

    public EventMessage removeMessages(Message message) {
        this.messages.remove(message);
        message.setEventMessage(null);
        return this;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
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
        EventMessage eventMessage = (EventMessage) o;
        if (eventMessage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventMessage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventMessage{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", type='" + getType() + "'" +
            ", channel='" + getChannel() + "'" +
            ", objectId=" + getObjectId() +
            ", objectTitle='" + getObjectTitle() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
