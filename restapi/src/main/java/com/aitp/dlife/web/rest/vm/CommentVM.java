package com.aitp.dlife.web.rest.vm;

import com.aitp.dlife.domain.enumeration.CommentChannel;

public class CommentVM {

	private String objectId;

	private CommentChannel channel;




	public CommentVM() {
		super();
	}

	public CommentVM(String objectId, CommentChannel channel) {
		super();
		this.objectId = objectId;
		this.channel = channel;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public CommentChannel getChannel() {
		return channel;
	}

	public void setChannel(CommentChannel channel) {
		this.channel = channel;
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channel == null) ? 0 : channel.hashCode());
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
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
		CommentVM other = (CommentVM) obj;
		if (channel == null) {
			if (other.channel != null)
				return false;
		} else if (!channel.equals(other.channel))
			return false;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentVM [objectId=" + objectId + ", channel=" + channel + "]";
	}


}
