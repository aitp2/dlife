package com.aitp.dlife.web.rest.vm;

import io.swagger.annotations.ApiModelProperty;

public class FitnessActivityVM {

	private Integer participantId;
	
	@ApiModelProperty(value="状态（0-结束，1-未开始,2-进行中）")
    private Integer	status;
    
    private Integer createId;

    
    
    
	
	
	public FitnessActivityVM() {
		super();
	}






	public FitnessActivityVM(Integer participantId, Integer status, Integer createId) {
		super();
		this.participantId = participantId;
		this.status = status;
		this.createId = createId;
	}






	public Integer getParticipantId() {
		return participantId;
	}






	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}






	public Integer getStatus() {
		return status;
	}






	public void setStatus(Integer status) {
		this.status = status;
	}






	public Integer getCreateId() {
		return createId;
	}






	public void setCreateId(Integer createId) {
		this.createId = createId;
	}






	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createId == null) ? 0 : createId.hashCode());
		result = prime * result + ((participantId == null) ? 0 : participantId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (createId == null) {
			if (other.createId != null)
				return false;
		} else if (!createId.equals(other.createId))
			return false;
		if (participantId == null) {
			if (other.participantId != null)
				return false;
		} else if (!participantId.equals(other.participantId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}






	@Override
	public String toString() {
		return "FitnessActivityVM [participantId=" + participantId + ", status=" + status + ", createId=" + createId
				+ "]";
	}

	



}
