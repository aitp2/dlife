package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 健身活动参与信息
 */
@ApiModel(description = "健身活动参与信息")
@Entity
@Table(name = "activity_participation")
public class ActivityParticipation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 参与人ID
     */
    @Size(max = 128)
    @ApiModelProperty(value = "参与人ID")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

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
     * 项目名称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "项目名称")
    @Column(name = "project", length = 128)
    private String project;

    /**
     * 参与时间
     */
    @ApiModelProperty(value = "参与时间")
    @Column(name = "participation_time")
    private Instant participationTime;

    @OneToMany(mappedBy = "activityParticipation")
    @JsonIgnore
    private Set<ClockIn> clockIns = new HashSet<>();

    @ManyToOne
    private FitnessActivity activity;

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

    public ActivityParticipation wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public ActivityParticipation nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public ActivityParticipation avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProject() {
        return project;
    }

    public ActivityParticipation project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Instant getParticipationTime() {
        return participationTime;
    }

    public ActivityParticipation participationTime(Instant participationTime) {
        this.participationTime = participationTime;
        return this;
    }

    public void setParticipationTime(Instant participationTime) {
        this.participationTime = participationTime;
    }

    public Set<ClockIn> getClockIns() {
        return clockIns;
    }

    public ActivityParticipation clockIns(Set<ClockIn> clockIns) {
        this.clockIns = clockIns;
        return this;
    }

    public ActivityParticipation addClockIn(ClockIn clockIn) {
        this.clockIns.add(clockIn);
        clockIn.setActivityParticipation(this);
        return this;
    }

    public ActivityParticipation removeClockIn(ClockIn clockIn) {
        this.clockIns.remove(clockIn);
        clockIn.setActivityParticipation(null);
        return this;
    }

    public void setClockIns(Set<ClockIn> clockIns) {
        this.clockIns = clockIns;
    }

    public FitnessActivity getActivity() {
        return activity;
    }

    public ActivityParticipation activity(FitnessActivity fitnessActivity) {
        this.activity = fitnessActivity;
        return this;
    }

    public void setActivity(FitnessActivity fitnessActivity) {
        this.activity = fitnessActivity;
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
        ActivityParticipation activityParticipation = (ActivityParticipation) o;
        if (activityParticipation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityParticipation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityParticipation{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", project='" + getProject() + "'" +
            ", participationTime='" + getParticipationTime() + "'" +
            "}";
    }
}
