package com.aitp.dlife.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.domain.enumeration.EventChannel;

/**
 * A DTO for the EventMessage entity.
 */
public class EventMessageDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String nickName;

    private EventType type;

    private EventChannel channel;

    private Long paragraphId;
    
    private Long objectId;

    @Size(max = 256)
    private String objectTitle;

    private Long replyId;
    
    private String createTime;

    private String content;

    
    
    

	public EventMessageDTO() {
		super();
	}

	

	public EventMessageDTO(@Size(max = 128) String wechatUserId, @Size(max = 1024) String avatar,
			@Size(max = 128) String nickName, EventType type, EventChannel channel, Long objectId,
			@Size(max = 256) String objectTitle, String createTime, String content, Long paragraphId) {
		super();
		this.wechatUserId = wechatUserId;
		this.avatar = avatar;
		this.nickName = nickName;
		this.type = type;
		this.channel = channel;
		this.paragraphId = paragraphId;
		this.objectId = objectId;
		this.objectTitle = objectTitle;
		this.createTime = createTime;
		this.content = content;
	}

	
	
    public EventMessageDTO( @Size(max = 128) String wechatUserId, @Size(max = 1024) String avatar,
			@Size(max = 128) String nickName, EventType type, EventChannel channel, Long objectId,
			@Size(max = 256) String objectTitle, String createTime, String content, Long paragraphId, Long replyId) {
		super();
		this.wechatUserId = wechatUserId;
		this.avatar = avatar;
		this.nickName = nickName;
		this.type = type;
		this.channel = channel;
		this.paragraphId = paragraphId;
		this.objectId = objectId;
		this.objectTitle = objectTitle;
		this.replyId = replyId;
		this.createTime = createTime;
		this.content = content;
	}
    
	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getParagraphId() {
		return paragraphId;
	}

	public void setParagraphId(Long paragraphId) {
		this.paragraphId = paragraphId;
	}

	public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventChannel getChannel() {
        return channel;
    }

    public void setChannel(EventChannel channel) {
        this.channel = channel;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public void setObjectTitle(String objectTitle) {
        this.objectTitle = objectTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventMessageDTO eventMessageDTO = (EventMessageDTO) o;
        if (eventMessageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventMessageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventMessageDTO{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", type='" + getType() + "'" +
            ", channel='" + getChannel() + "'" +
            ", objectId=" + getObjectId() +
            ", objectTitle='" + getObjectTitle() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
