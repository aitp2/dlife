package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 拼饭参与评价信息
 */
@ApiModel(description = "拼饭参与评价信息")
@Entity
@Table(name = "rates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1024)
    @Column(name = "comments", length = 1024)
    private String comments;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "modify_time")
    private Instant modifyTime;

    @OneToMany(mappedBy = "rate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PinfanPics> pinfanPics = new HashSet<>();

    @ManyToOne
    private PinFanActivity pinFanActivity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public Rates comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getRating() {
        return rating;
    }

    public Rates rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Rates createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public Rates modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Set<PinfanPics> getPinfanPics() {
        return pinfanPics;
    }

    public Rates pinfanPics(Set<PinfanPics> pinfanPics) {
        this.pinfanPics = pinfanPics;
        return this;
    }

    public Rates addPinfanPics(PinfanPics pinfanPics) {
        this.pinfanPics.add(pinfanPics);
        pinfanPics.setRate(this);
        return this;
    }

    public Rates removePinfanPics(PinfanPics pinfanPics) {
        this.pinfanPics.remove(pinfanPics);
        pinfanPics.setRate(null);
        return this;
    }

    public void setPinfanPics(Set<PinfanPics> pinfanPics) {
        this.pinfanPics = pinfanPics;
    }

    public PinFanActivity getPinFanActivity() {
        return pinFanActivity;
    }

    public Rates pinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
        return this;
    }

    public void setPinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
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
        Rates rates = (Rates) o;
        if (rates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rates{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", rating=" + getRating() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
