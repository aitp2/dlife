package com.aitp.dlife.pinfan.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RatesPics.
 */
@Entity
@Table(name = "rates_pics")
@Document(indexName = "ratespics")
public class RatesPics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 图片路径
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "图片路径")
    @Column(name = "oss_path", length = 1024)
    private String ossPath;

    @ManyToOne
    private Rates rate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOssPath() {
        return ossPath;
    }

    public RatesPics ossPath(String ossPath) {
        this.ossPath = ossPath;
        return this;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public Rates getRate() {
        return rate;
    }

    public RatesPics rate(Rates rates) {
        this.rate = rates;
        return this;
    }

    public void setRate(Rates rates) {
        this.rate = rates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RatesPics ratesPics = (RatesPics) o;
        if (ratesPics.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratesPics.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatesPics{" +
            "id=" + getId() +
            ", ossPath='" + getOssPath() + "'" +
            "}";
    }
}
