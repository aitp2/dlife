package com.aitp.web.common.service.beans;

public class TempResponse extends Response{

	
	private String template_id;
	

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

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((template_id == null) ? 0 : template_id.hashCode());
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
		TempResponse other = (TempResponse) obj;
		if (template_id == null) {
			if (other.template_id != null)
				return false;
		} else if (!template_id.equals(other.template_id))
			return false;
		return true;
	}


	
	
}
