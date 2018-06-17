package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * 健身活动信息
 */
@ApiModel(description = "健身活动信息")
@Entity
@Table(name = "fitness_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FitnessActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id 创建人
     */
    @Size(max = 128)
    @ApiModelProperty(value = "用户id 创建人")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 活动标题
     */
    @Size(max = 64)
    @ApiModelProperty(value = "活动标题")
    @Column(name = "title", length = 64)
    private String title;

    /**
     * 活动描述
     */
    @Size(max = 128)
    @ApiModelProperty(value = "活动描述")
    @Column(name = "descrption", length = 128)
    private String descrption;

    /**
     * 报名开始时间
     */
    @ApiModelProperty(value = "报名开始时间")
    @Column(name = "sign_start_time")
    private Instant signStartTime;

    /**
     * 报名截至时间
     */
    @ApiModelProperty(value = "报名截至时间")
    @Column(name = "sign_end_time")
    private Instant signEndTime;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @Column(name = "activity_start_time")
    private Instant activityStartTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @Column(name = "activity_end_time")
    private Instant activityEndTime;

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActivityParticipation> activityParticipations = new HashSet<>();

    @OneToMany(mappedBy = "fitnessActivity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pics> images = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public FitnessActivity wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getTitle() {
        return title;
    }

    public FitnessActivity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public FitnessActivity descrption(String descrption) {
        this.descrption = descrption;
        return this;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public Instant getSignStartTime() {
        return signStartTime;
    }

    public FitnessActivity signStartTime(Instant signStartTime) {
        this.signStartTime = signStartTime;
        return this;
    }

    public void setSignStartTime(Instant signStartTime) {
        this.signStartTime = signStartTime;
    }

    public Instant getSignEndTime() {
        return signEndTime;
    }

    public FitnessActivity signEndTime(Instant signEndTime) {
        this.signEndTime = signEndTime;
        return this;
    }

    public void setSignEndTime(Instant signEndTime) {
        this.signEndTime = signEndTime;
    }

    public Instant getActivityStartTime() {
        return activityStartTime;
    }

    public FitnessActivity activityStartTime(Instant activityStartTime) {
        this.activityStartTime = activityStartTime;
        return this;
    }

    public void setActivityStartTime(Instant activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Instant getActivityEndTime() {
        return activityEndTime;
    }

    public FitnessActivity activityEndTime(Instant activityEndTime) {
        this.activityEndTime = activityEndTime;
        return this;
    }

    public void setActivityEndTime(Instant activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public Set<ActivityParticipation> getActivityParticipations() {
        return activityParticipations;
    }

    public FitnessActivity activityParticipations(Set<ActivityParticipation> activityParticipations) {
        this.activityParticipations = activityParticipations;
        return this;
    }

    public FitnessActivity addActivityParticipation(ActivityParticipation activityParticipation) {
        this.activityParticipations.add(activityParticipation);
        activityParticipation.setActivity(this);
        return this;
    }

    public FitnessActivity removeActivityParticipation(ActivityParticipation activityParticipation) {
        this.activityParticipations.remove(activityParticipation);
        activityParticipation.setActivity(null);
        return this;
    }

    public void setActivityParticipations(Set<ActivityParticipation> activityParticipations) {
        this.activityParticipations = activityParticipations;
    }

    public Set<Pics> getImages() {
        return images;
    }

    public FitnessActivity images(Set<Pics> pics) {
        this.images = pics;
        return this;
    }

    public FitnessActivity addImage(Pics pics) {
        this.images.add(pics);
        pics.setFitnessActivity(this);
        return this;
    }

    public FitnessActivity removeImage(Pics pics) {
        this.images.remove(pics);
        pics.setFitnessActivity(null);
        return this;
    }

    public void setImages(Set<Pics> pics) {
        this.images = pics;
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
        FitnessActivity fitnessActivity = (FitnessActivity) o;
        if (fitnessActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fitnessActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FitnessActivity{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", title='" + getTitle() + "'" +
            ", descrption='" + getDescrption() + "'" +
            ", signStartTime='" + getSignStartTime() + "'" +
            ", signEndTime='" + getSignEndTime() + "'" +
            ", activityStartTime='" + getActivityStartTime() + "'" +
            ", activityEndTime='" + getActivityEndTime() + "'" +
            "}";
    }
}
