package com.aitp.dlife.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 健身活动信息
 */
@ApiModel(description = "健身活动信息")
@Entity
@Table(name = "fitness_activity")
public class FitnessActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 活动标题
     */
    @Size(max = 128)
    @ApiModelProperty(value = "活动标题")
    @Column(name = "title", length = 128)
    private String title;

    /**
     * 活动描述
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "活动描述")
    @Column(name = "descrption", length = 1024)
    private String descrption;

    /**
     * 用户id 创建人
     */
    @Size(max = 128)
    @ApiModelProperty(value = "用户id 创建人")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "头像")
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
     * 角色
     */
    @Size(max = 128)
    @ApiModelProperty(value = "角色")
    @Column(name = "company_role", length = 128)
    private String companyRole;

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

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量")
    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "modify_time")
    private Instant modifyTime;

    @Column(name = "reading_count")
    private Integer readingCount;

    @OneToMany(mappedBy = "activity")
    private Set<ActivityParticipation> activityParticipations = new HashSet<>();

    @OneToMany(mappedBy = "fitnessActivity")
    private Set<Pics> images = new HashSet<>();
    

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNickName() {
        return nickName;
    }

    public FitnessActivity nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public FitnessActivity avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProject() {
        return project;
    }

    public FitnessActivity project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCompanyRole() {
        return companyRole;
    }

    public FitnessActivity companyRole(String companyRole) {
        this.companyRole = companyRole;
        return this;
    }

    public void setCompanyRole(String companyRole) {
        this.companyRole = companyRole;
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

    public Integer getCommentCount() {
        return commentCount;
    }

    public FitnessActivity commentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public FitnessActivity modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getReadingCount() {
        return readingCount;
    }

    public FitnessActivity readingCount(Integer readingCount) {
        this.readingCount = readingCount;
        return this;
    }

    public void setReadingCount(Integer readingCount) {
        this.readingCount = readingCount;
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
            ", title='" + getTitle() + "'" +
            ", descrption='" + getDescrption() + "'" +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", project='" + getProject() + "'" +
            ", companyRole='" + getCompanyRole() + "'" +
            ", signStartTime='" + getSignStartTime() + "'" +
            ", signEndTime='" + getSignEndTime() + "'" +
            ", activityStartTime='" + getActivityStartTime() + "'" +
            ", activityEndTime='" + getActivityEndTime() + "'" +
            ", commentCount=" + getCommentCount() +
            ", modifyTime='" + getModifyTime() + "'" +
            ", readingCount=" + getReadingCount() +
            "}";
    }
}
