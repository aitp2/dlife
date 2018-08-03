package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Pics entity.
 */
public class PicsDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String ossPath;

    private String createTime;

    private Long fitnessActivityId;

    private Long clockInId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

   
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getFitnessActivityId() {
        return fitnessActivityId;
    }

    public void setFitnessActivityId(Long fitnessActivityId) {
        this.fitnessActivityId = fitnessActivityId;
    }

    public Long getClockInId() {
        return clockInId;
    }

    public void setClockInId(Long clockInId) {
        this.clockInId = clockInId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PicsDTO picsDTO = (PicsDTO) o;
        if(picsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), picsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PicsDTO{" +
            "id=" + getId() +
            ", ossPath='" + getOssPath() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
