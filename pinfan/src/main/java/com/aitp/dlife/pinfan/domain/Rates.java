package com.aitp.dlife.pinfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Rates.
 */
@Entity
@Table(name = "rates")
@Document(indexName = "rates")
public class Rates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 参与人ID
     */
    @ApiModelProperty(value = "参与人ID")
    @Column(name = "wechat_user_id")
    private Long wechatUserId;

    /**
     * 参与者昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "参与者昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 参与者头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "参与者头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    @Column(name = "activity_id")
    private Long activityId;

    @OneToMany(mappedBy = "rate")
    @JsonIgnore
    private Set<RatesPics> ratesPics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public Rates wechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public Rates nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public Rates avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Rates activityId(Long activityId) {
        this.activityId = activityId;
        return this;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Set<RatesPics> getRatesPics() {
        return ratesPics;
    }

    public Rates ratesPics(Set<RatesPics> ratesPics) {
        this.ratesPics = ratesPics;
        return this;
    }

    public Rates addRatesPics(RatesPics ratesPics) {
        this.ratesPics.add(ratesPics);
        ratesPics.setRate(this);
        return this;
    }

    public Rates removeRatesPics(RatesPics ratesPics) {
        this.ratesPics.remove(ratesPics);
        ratesPics.setRate(null);
        return this;
    }

    public void setRatesPics(Set<RatesPics> ratesPics) {
        this.ratesPics = ratesPics;
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
            ", wechatUserId=" + getWechatUserId() +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", activityId=" + getActivityId() +
            "}";
    }
}
