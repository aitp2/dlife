package com.aitp.dlife.web.rest.vm;

public class FitnessActivityVM {

	private Long wechatUserId;
	
    private Integer	status;

    
    
	public FitnessActivityVM() {
		super();
	}

	public FitnessActivityVM(Long wechatUserId, Integer status) {
		super();
		this.wechatUserId = wechatUserId;
		this.status = status;
	}

	public Long getWechatUserId() {
		return wechatUserId;
	}

	public void setWechatUserId(Long wechatUserId) {
		this.wechatUserId = wechatUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		FitnessActivityVM other = (FitnessActivityVM) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		return "FitnessActivityVM [wechatUserId=" + wechatUserId + ", status=" + status + "]";
	}
    
    
    
    
}
