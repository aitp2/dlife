package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    /**
     * 总共加入天数
     */
    @ApiModelProperty(value = "总共加入天数")
    @Column(name = "total_participate_days")
    private Integer totalParticipateDays;

    /**
     * 总共打卡天数
     */
    @ApiModelProperty(value = "总共打卡天数")
    @Column(name = "total_clockin_days")
    private Integer totalClockinDays;

    /**
     * 当前连续天数
     */
    @ApiModelProperty(value = "当前连续天数")
    @Column(name = "current_continue_days")
    private Integer currentContinueDays;

    /**
     * 最长连续天数
     */
    @ApiModelProperty(value = "最长连续天数")
    @Column(name = "longest_continue_days")
    private Integer longestContinueDays;

    /**
     * 最新打卡时间
     */
    @ApiModelProperty(value = "最新打卡时间")
    @Column(name = "latest_clockin_time")
    private Instant latestClockinTime;

    @OneToMany(mappedBy = "activityParticipation")
    private Set<ClockIn> clockIns = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("activityParticipations")
    private FitnessActivity fitnessActivity;

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

    public Integer getTotalParticipateDays() {
        return totalParticipateDays;
    }

    public ActivityParticipation totalParticipateDays(Integer totalParticipateDays) {
        this.totalParticipateDays = totalParticipateDays;
        return this;
    }

    public void setTotalParticipateDays(Integer totalParticipateDays) {
        this.totalParticipateDays = totalParticipateDays;
    }

    public Integer getTotalClockinDays() {
        return totalClockinDays;
    }

    public ActivityParticipation totalClockinDays(Integer totalClockinDays) {
        this.totalClockinDays = totalClockinDays;
        return this;
    }

    public void setTotalClockinDays(Integer totalClockinDays) {
        this.totalClockinDays = totalClockinDays;
    }

    public Integer getCurrentContinueDays() {
        return currentContinueDays;
    }

    public ActivityParticipation currentContinueDays(Integer currentContinueDays) {
        this.currentContinueDays = currentContinueDays;
        return this;
    }

    public void setCurrentContinueDays(Integer currentContinueDays) {
        this.currentContinueDays = currentContinueDays;
    }

    public Integer getLongestContinueDays() {
        return longestContinueDays;
    }

    public ActivityParticipation longestContinueDays(Integer longestContinueDays) {
        this.longestContinueDays = longestContinueDays;
        return this;
    }

    public void setLongestContinueDays(Integer longestContinueDays) {
        this.longestContinueDays = longestContinueDays;
    }

    public Instant getLatestClockinTime() {
        return latestClockinTime;
    }

    public ActivityParticipation latestClockinTime(Instant latestClockinTime) {
        this.latestClockinTime = latestClockinTime;
        return this;
    }

    public void setLatestClockinTime(Instant latestClockinTime) {
        this.latestClockinTime = latestClockinTime;
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

    public FitnessActivity getFitnessActivity() {
        return fitnessActivity;
    }

    public ActivityParticipation fitnessActivity(FitnessActivity fitnessActivity) {
        this.fitnessActivity = fitnessActivity;
        return this;
    }

    public void setFitnessActivity(FitnessActivity fitnessActivity) {
        this.fitnessActivity = fitnessActivity;
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
            ", totalParticipateDays=" + getTotalParticipateDays() +
            ", totalClockinDays=" + getTotalClockinDays() +
            ", currentContinueDays=" + getCurrentContinueDays() +
            ", longestContinueDays=" + getLongestContinueDays() +
            ", latestClockinTime='" + getLatestClockinTime() + "'" +
            "}";
    }
}
