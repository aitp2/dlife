package com.aitp.dlife.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PinfanPics entity.
 */
public class PinfanPicsDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String ossPath;

    private Instant createTime;

    private Long pinFanActivityId;

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

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
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

        PinfanPicsDTO pinfanPicsDTO = (PinfanPicsDTO) o;
        if (pinfanPicsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinfanPicsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PinfanPicsDTO{" +
            "id=" + getId() +
            ", ossPath='" + getOssPath() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", pinFanActivity=" + getPinFanActivityId() +
            "}";
    }
}
