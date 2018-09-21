package com.aitp.dlife.web.rest.vm;

import io.swagger.annotations.ApiParam;

public class ClockInVM {
	@ApiParam(value = "用户ID")
	private Long wechatUserId;
	@ApiParam(value = "报名ID")
	private Long activityParticipationId;
	@ApiParam(value = "活动ID")
	private Long activityId;
    @ApiParam(value = "动态开始时间（i.e. 2018-09-20 00:00:00）")
    private String startTime;
    @ApiParam(value = "动态结束时间（i.e. 2018-08-20 00:00:00）")
    private String endTime;


	public ClockInVM() {
		super();
	}

	public ClockInVM(Long wechatUserId, Long activityParticipationId, Long activityId, String startTime, String endTime) {
		super();
		this.wechatUserId = wechatUserId;
		this.activityParticipationId = activityParticipationId;
		this.activityId = activityId;
		this.startTime = startTime;
		this.endTime = endTime;
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


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
	public String toString() {
		return "ClockInVM [wechatUserId=" + wechatUserId + ", activityParticipationId=" + activityParticipationId
				+ ", activityId=" + activityId + ",startTime="+startTime+",endTime="+endTime+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityId == null) ? 0 : activityId.hashCode());
		result = prime * result + ((activityParticipationId == null) ? 0 : activityParticipationId.hashCode());
		result = prime * result + ((wechatUserId == null) ? 0 : wechatUserId.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
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
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
		return true;
	}





}
