package com.aitp.dlife.web.rest.vm;

import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.CommentModule;

import io.swagger.annotations.ApiModelProperty;

public class ReplyVM {

	@ApiModelProperty(value="渠道或APP CODE",required=true)
	private CommentChannel channel;

    private String wechatUserId;
    @ApiModelProperty(value="评论ID或动态（打卡）ID")
    private String parentId;

    @ApiModelProperty(value="模块类型（COMMENT-评论,ACTIVITY-动态）",required=true)
	private CommentModule model;


	public ReplyVM() {
		super();
	}


	public ReplyVM(CommentChannel channel, String wechatUserId, String parentId, CommentModule model) {
		super();
		this.channel = channel;
		this.wechatUserId = wechatUserId;
		this.parentId = parentId;
		this.model = model;
	}



	public CommentChannel getChannel() {
		return channel;
	}

	public void setChannel(CommentChannel channel) {
		this.channel = channel;
	}

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    
    
    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	

	public CommentModule getModel() {
		return model;
	}

	public void setModel(CommentModule model) {
		this.model = model;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
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
		ReplyVM other = (ReplyVM) obj;
		if (channel != other.channel)
			return false;
		if (model != other.model)
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (wechatUserId == null) {
			if (other.wechatUserId != null)
				return false;
		} else if (!wechatUserId.equals(other.wechatUserId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReplyVM [channel=" + channel + ", wechatUserId=" + wechatUserId + ", parentId=" + parentId + ", model="
				+ model + "]";
	}


}
