package com.aitp.dlife.pinfan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the RatesPics entity.
 */
public class RatesPicsDTO implements Serializable {

    private Long id;

    @Size(max = 1024)
    private String ossPath;

    private Long rateId;

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

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long ratesId) {
        this.rateId = ratesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatesPicsDTO ratesPicsDTO = (RatesPicsDTO) o;
        if(ratesPicsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratesPicsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatesPicsDTO{" +
            "id=" + getId() +
            ", ossPath='" + getOssPath() + "'" +
            "}";
    }
}
