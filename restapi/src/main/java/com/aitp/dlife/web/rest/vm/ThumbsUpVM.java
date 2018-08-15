package com.aitp.dlife.web.rest.vm;

public class ThumbsUpVM {

	private String objectId;

	
	public ThumbsUpVM(String objectId) {
		super();
		this.objectId = objectId;
	}
	
	
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ThumbsUpVM [objectId=" + objectId + "]";
	}

	
}
