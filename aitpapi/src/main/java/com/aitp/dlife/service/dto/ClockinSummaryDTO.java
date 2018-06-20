package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ClockinSummary entity.
 */
public class ClockinSummaryDTO implements Serializable {

    private Long id;

    @Size(max = 128)
    private String wechatUserId;

    private Integer totallyCount;

    private Integer weeklyCount;

    private Integer serialCount;

    private String lastClockInTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public Integer getTotallyCount() {
        return totallyCount;
    }

    public void setTotallyCount(Integer totallyCount) {
        this.totallyCount = totallyCount;
    }

    public Integer getWeeklyCount() {
        return weeklyCount;
    }

    public void setWeeklyCount(Integer weeklyCount) {
        this.weeklyCount = weeklyCount;
    }

    public Integer getSerialCount() {
        return serialCount;
    }

    public void setSerialCount(Integer serialCount) {
        this.serialCount = serialCount;
    }


    public String getLastClockInTime() {
		return lastClockInTime;
	}

	public void setLastClockInTime(String lastClockInTime) {
		this.lastClockInTime = lastClockInTime;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClockinSummaryDTO clockinSummaryDTO = (ClockinSummaryDTO) o;
        if(clockinSummaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clockinSummaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClockinSummaryDTO{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", totallyCount=" + getTotallyCount() +
            ", weeklyCount=" + getWeeklyCount() +
            ", serialCount=" + getSerialCount() +
            ", lastClockInTime='" + getLastClockInTime() + "'" +
            "}";
    }
}
