package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.MessageType;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 接收人id
     */
    @Size(max = 128)
    @ApiModelProperty(value = "接收人id")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 是否读取
     */
    @ApiModelProperty(value = "是否读取")
    @Column(name = "jhi_read")
    private Boolean read;

    /**
     * 消息类型
     */
    @ApiModelProperty(value = "消息类型")
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private EventMessage eventMessage;

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

    public Message wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Boolean isRead() {
        return read;
    }

    public Message read(Boolean read) {
        this.read = read;
        return this;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Message messageType(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public EventMessage getEventMessage() {
        return eventMessage;
    }

    public Message eventMessage(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
        return this;
    }

    public void setEventMessage(EventMessage eventMessage) {
        this.eventMessage = eventMessage;
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
        Message message = (Message) o;
        if (message.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", read='" + isRead() + "'" +
            ", messageType='" + getMessageType() + "'" +
            "}";
    }
}
