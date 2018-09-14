package com.aitp.dlife.web.rest.vm;

import io.swagger.annotations.ApiParam;

public class ClockInVM {
	@ApiParam(value = "用户ID")
	private Long wechatUserId;
	@ApiParam(value = "报名ID")
	private Long activityParticipationId;
	@ApiParam(value = "活动ID")
	private Long activityId;

	
	public ClockInVM() {
		super();
	}

	public ClockInVM(Long wechatUserId, Long activityParticipationId, Long activityId) {
		super();
		this.wechatUserId = wechatUserId;
		this.activityParticipationId = activityParticipationId;
		this.activityId = activityId;
	}

	public Long getWechatUserId() {
		return wechatUserId;
	}

	public void setWechatUserId(Long wechatUserId) {
		this.wechatUserId = wechatUserId;
	}

	public Long getActivityParticipationId() {
		return activityParticipationId;
	}

	public void setActivityParticipationId(Long activityParticipationId) {
		this.activityParticipationId = activityParticipationId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	
	
	
	
	@Override
	public String toString() {
		return "ClockInVM [wechatUserId=" + wechatUserId + ", activityParticipationId=" + activityParticipationId
				+ ", activityId=" + activityId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityId == null) ? 0 : activityId.hashCode());
		result = prime * result + ((activityParticipationId == null) ? 0 : activityParticipationId.hashCode());
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
		ClockInVM other = (ClockInVM) obj;
		if (activityId == null) {
			if (other.activityId != null)
				return false;
		} else if (!activityId.equals(other.activityId))
			return false;
		if (activityParticipationId == null) {
			if (other.activityParticipationId != null)
				return false;
		} else if (!activityParticipationId.equals(other.activityParticipationId))
			return false;
		if (wechatUserId == null) {
			if (other.wechatUserId != null)
				return false;
		} else if (!wechatUserId.equals(other.wechatUserId))
			return false;
		return true;
	}
	
	
	
	
	
}
