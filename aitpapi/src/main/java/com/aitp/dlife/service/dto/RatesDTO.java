package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Rates entity.
 */
public class RatesDTO implements Serializable {

    private Long id;

    @Size(max = 1024)
    private String comments;

    @Max(value = 2)
    private Integer rating;

    private Instant createTime;

    private Instant modifyTime;

    private Long pinFanActivityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getPinFanActivityId() {
        return pinFanActivityId;
    }

    public void setPinFanActivityId(Long pinFanActivityId) {
        this.pinFanActivityId = pinFanActivityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatesDTO ratesDTO = (RatesDTO) o;
        if(ratesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatesDTO{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", rating=" + getRating() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
