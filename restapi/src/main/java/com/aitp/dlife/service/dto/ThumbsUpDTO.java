package com.aitp.dlife.service.dto;

import java.time.Instant;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.aitp.dlife.domain.enumeration.ThumbsUpChannel;
import com.aitp.dlife.domain.enumeration.ThumbsUpModule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the ThumbsUp entity.
 */
@ApiModel(value="ThumbsUp", description="点赞对象")		 
public class ThumbsUpDTO implements Serializable {

    private Long id;
    @ApiModelProperty(value="评论id",required=true)
    private Long objectId;
    
    @ApiModelProperty(value="通道含有( COOK, FIT, PIN, FAQS)",required=true)
    private ThumbsUpChannel channel;

    @ApiModelProperty(value="通道含有( COMMENT, ACTIVITY, USERCENTER)",required=true)
    private ThumbsUpModule module;

    @ApiModelProperty(value="用户wechatID", required=true)
    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    @ApiModelProperty(value="昵称", required=true)
    private String nickName;
    @ApiModelProperty(hidden=true)
    private Instant createTime = Instant.now();
    @ApiModelProperty(hidden=true)
    private String keyName_1;
    @ApiModelProperty(hidden=true)
    private String keyName_2;
    
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

    
    
   
	public String getKeyName_1() {
		return keyName_1;
	}

	public void setKeyName_1(String keyName_1) {
		this.keyName_1 = keyName_1;
	}

	public String getKeyName_2() {
		return keyName_2;
	}

	public void setKeyName_2(String keyName_2) {
		this.keyName_2 = keyName_2;
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
