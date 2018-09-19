package com.aitp.dlife.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.domain.enumeration.TaskPeriod;

/**
 * A DTO for the UserTask entity.
 */
public class UserTaskDTO implements Serializable {

    private Long id;

    private String userid;

    private Integer taskStatus;

    private Integer gainPoint;

    private Integer remainPoint;

    private ZonedDateTime validateTo;

    private String createBy;

    private ZonedDateTime createTime;

    private String lastModifyBy;

    private ZonedDateTime lastModifyTime;

    private Long taskId;
    
    private String taskDefineName;
    
    private TaskPeriod peroid;
    
    private Integer maxlimit;
    
    private String targetSystems;

    private PointEventType eventType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getGainPoint() {
        return gainPoint;
    }

    public void setGainPoint(Integer gainPoint) {
        this.gainPoint = gainPoint;
    }

    public Integer getRemainPoint() {
        return remainPoint;
    }

    public void setRemainPoint(Integer remainPoint) {
        this.remainPoint = remainPoint;
    }

    public ZonedDateTime getValidateTo() {
        return validateTo;
    }

    public void setValidateTo(ZonedDateTime validateTo) {
        this.validateTo = validateTo;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public ZonedDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(ZonedDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskDefineId) {
        this.taskId = taskDefineId;
    }

    public String getTaskDefineName() {
		return taskDefineName;
	}

	public void setTaskDefineName(String taskDefineName) {
		this.taskDefineName = taskDefineName;
	}

	public TaskPeriod getPeroid() {
		return peroid;
	}

	public void setPeroid(TaskPeriod peroid) {
		this.peroid = peroid;
	}

	public Integer getMaxlimit() {
		return maxlimit;
	}

	public void setMaxlimit(Integer maxlimit) {
		this.maxlimit = maxlimit;
	}

	public String getTargetSystems() {
		return targetSystems;
	}

	public void setTargetSystems(String targetSystems) {
		this.targetSystems = targetSystems;
	}

	public PointEventType getEventType() {
		return eventType;
	}

	public void setEventType(PointEventType eventType) {
		this.eventType = eventType;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserTaskDTO userTaskDTO = (UserTaskDTO) o;
        if (userTaskDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userTaskDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserTaskDTO{" +
            "id=" + getId() +
            ", userid='" + getUserid() + "'" +
            ", taskStatus=" + getTaskStatus() +
            ", gainPoint=" + getGainPoint() +
            ", remainPoint=" + getRemainPoint() +
            ", validateTo='" + getValidateTo() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", lastModifyBy='" + getLastModifyBy() + "'" +
            ", lastModifyTime='" + getLastModifyTime() + "'" +
            ", task=" + getTaskId() +
            "}";
    }
}
