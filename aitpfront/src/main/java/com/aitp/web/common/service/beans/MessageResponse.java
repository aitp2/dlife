package com.aitp.web.common.service.beans;

public class MessageResponse extends Response{

	
	private Integer msgid;
	

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Integer getMsgid() {
		return msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errcode == null) ? 0 : errcode.hashCode());
		result = prime * result + ((errmsg == null) ? 0 : errmsg.hashCode());
		result = prime * result + ((msgid == null) ? 0 : msgid.hashCode());
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
		MessageResponse other = (MessageResponse) obj;
		if (errcode == null) {
			if (other.errcode != null)
				return false;
		} else if (!errcode.equals(other.errcode))
			return false;
		if (errmsg == null) {
			if (other.errmsg != null)
				return false;
		} else if (!errmsg.equals(other.errmsg))
			return false;
		if (msgid == null) {
			if (other.msgid != null)
				return false;
		} else if (!msgid.equals(other.msgid))
			return false;
		return true;
	}

	
}
