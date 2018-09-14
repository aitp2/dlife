package com.aitp.dlife.web.rest.vm;

import com.aitp.dlife.domain.enumeration.ThumbsUpModule;

import io.swagger.annotations.ApiModelProperty;

public class ThumbsUpVM {

    @ApiModelProperty(value = "活动ID")
	private Long objectId;
    
    @ApiModelProperty(value = "活动类型")
	private ThumbsUpModule model;


	public ThumbsUpVM(Long objectId) {
		super();
		this.objectId = objectId;
	}


	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	
	

	public ThumbsUpModule getModel() {
		return model;
	}


	public ThumbsUpVM(Long objectId, ThumbsUpModule model) {
		super();
		this.objectId = objectId;
		this.model = model;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
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
		ThumbsUpVM other = (ThumbsUpVM) obj;
		if (model != other.model)
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
		return "ThumbsUpVM [objectId=" + objectId + ", model=" + model + "]";
	}


}
