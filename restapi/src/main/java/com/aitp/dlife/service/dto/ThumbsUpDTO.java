package com.aitp.dlife.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.aitp.dlife.domain.enumeration.ThumbsUpChannel;
import com.aitp.dlife.domain.enumeration.ThumbsUpModule;

/**
 * A DTO for the ThumbsUp entity.
 */
public class ThumbsUpDTO implements Serializable {

    private Long id;

    private Long objectId;

    private ThumbsUpChannel channel;

    private ThumbsUpModule module;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String nickName;

    private Instant createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public ThumbsUpChannel getChannel() {
        return channel;
    }

    public void setChannel(ThumbsUpChannel channel) {
        this.channel = channel;
    }

    public ThumbsUpModule getModule() {
        return module;
    }

    public void setModule(ThumbsUpModule module) {
        this.module = module;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThumbsUpDTO thumbsUpDTO = (ThumbsUpDTO) o;
        if (thumbsUpDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thumbsUpDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThumbsUpDTO{" +
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
