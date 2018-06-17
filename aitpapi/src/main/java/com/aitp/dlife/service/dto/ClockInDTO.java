package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ClockIn entity.
 */
public class ClockInDTO implements Serializable {

    private Long id;

    @Size(max = 1024)
    private String signNote;

    private Instant punchDateTime;

    private Long activityParticipationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignNote() {
        return signNote;
    }

    public void setSignNote(String signNote) {
        this.signNote = signNote;
    }

    public Instant getPunchDateTime() {
        return punchDateTime;
    }

    public void setPunchDateTime(Instant punchDateTime) {
        this.punchDateTime = punchDateTime;
    }

    public Long getActivityParticipationId() {
        return activityParticipationId;
    }

    public void setActivityParticipationId(Long activityParticipationId) {
        this.activityParticipationId = activityParticipationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClockInDTO clockInDTO = (ClockInDTO) o;
        if(clockInDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clockInDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClockInDTO{" +
            "id=" + getId() +
            ", signNote='" + getSignNote() + "'" +
            ", punchDateTime='" + getPunchDateTime() + "'" +
            "}";
    }
}
