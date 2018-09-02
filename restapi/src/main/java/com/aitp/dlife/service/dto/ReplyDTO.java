package com.aitp.dlife.service.dto;



import javax.validation.constraints.*;

import com.aitp.dlife.domain.enumeration.CommentModule;

import java.io.Serializable;

/**
 * A DTO for the Comment entity.
 */
public class ReplyDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long parentId;

    private CommentModule module;
    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String nickName;

    private Long rpWechatUserId;

    private String rpAvatar;

    private String rpNickName;
    
    private String content;

    private String createTime;
    

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((rpAvatar == null) ? 0 : rpAvatar.hashCode());
		result = prime * result + ((rpNickName == null) ? 0 : rpNickName.hashCode());
		result = prime * result + ((rpWechatUserId == null) ? 0 : rpWechatUserId.hashCode());
		result = prime * result + ((wechatUserId == null) ? 0 : wechatUserId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReplyDTO other = (ReplyDTO) obj;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (module != other.module)
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (rpAvatar == null) {
			if (other.rpAvatar != null)
				return false;
		} else if (!rpAvatar.equals(other.rpAvatar))
			return false;
		if (rpNickName == null) {
			if (other.rpNickName != null)
				return false;
		} else if (!rpNickName.equals(other.rpNickName))
			return false;
		if (rpWechatUserId == null) {
			if (other.rpWechatUserId != null)
				return false;
		} else if (!rpWechatUserId.equals(other.rpWechatUserId))
			return false;
		if (wechatUserId == null) {
			if (other.wechatUserId != null)
				return false;
		} else if (!wechatUserId.equals(other.wechatUserId))
			return false;
		return true;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getRpWechatUserId() {
		return rpWechatUserId;
	}

	public void setRpWechatUserId(Long rpWechatUserId) {
		this.rpWechatUserId = rpWechatUserId;
	}

	public String getRpAvatar() {
		return rpAvatar;
	}

	public void setRpAvatar(String rpAvatar) {
		this.rpAvatar = rpAvatar;
	}

	public String getRpNickName() {
		return rpNickName;
	}

	public void setRpNickName(String rpNickName) {
		this.rpNickName = rpNickName;
	}

	public CommentModule getModule() {
		return module;
	}

	public void setModule(CommentModule module) {
		this.module = module;
	}

   

}
