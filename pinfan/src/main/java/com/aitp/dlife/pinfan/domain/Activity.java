package com.aitp.dlife.pinfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Document(indexName = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 活动类型
     */
    @ApiModelProperty(value = "活动类型")
    @Column(name = "activitiy_type")
    private Integer activitiyType;

    /**
     * 活动描述
     */
    @Size(max = 128)
    @ApiModelProperty(value = "活动描述")
    @Column(name = "descrption", length = 128)
    private String descrption;

    /**
     * 组织者
     */
    @Size(max = 128)
    @ApiModelProperty(value = "组织者")
    @Column(name = "organize_user", length = 128)
    private String organizeUser;

    /**
     * 封面图
     */
    @Size(max = 128)
    @ApiModelProperty(value = "封面图")
    @Column(name = "cover_picture", length = 128)
    private String coverPicture;

    /**
     * 约定时间
     */
    @ApiModelProperty(value = "约定时间")
    @Column(name = "appoint_datetime")
    private Instant appointDatetime;

    /**
     * 最少参与者
     */
    @ApiModelProperty(value = "最少参与者")
    @Column(name = "lower_limit")
    private Integer lowerLimit;

    /**
     * 最多参与者
     */
    @ApiModelProperty(value = "最多参与者")
    @Column(name = "upper_limit")
    private Integer upperLimit;

    /**
     * 支付类型
     */
    @Size(max = 32)
    @ApiModelProperty(value = "支付类型")
    @Column(name = "pay_type", length = 32)
    private String payType;

    /**
     * 截至日期
     */
    @ApiModelProperty(value = "截至日期")
    @Column(name = "deadline")
    private Instant deadline;

    /**
     * 备注
     */
    @Size(max = 500)
    @ApiModelProperty(value = "备注")
    @Column(name = "jhi_comment", length = 500)
    private String comment;

    /**
     * 是否生效
     */
    @ApiModelProperty(value = "是否生效")
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "activity")
    @JsonIgnore
    private Set<Attendee> attendees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActivitiyType() {
        return activitiyType;
    }

    public Activity activitiyType(Integer activitiyType) {
        this.activitiyType = activitiyType;
        return this;
    }

    public void setActivitiyType(Integer activitiyType) {
        this.activitiyType = activitiyType;
    }

    public String getDescrption() {
        return descrption;
    }

    public Activity descrption(String descrption) {
        this.descrption = descrption;
        return this;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getOrganizeUser() {
        return organizeUser;
    }

    public Activity organizeUser(String organizeUser) {
        this.organizeUser = organizeUser;
        return this;
    }

    public void setOrganizeUser(String organizeUser) {
        this.organizeUser = organizeUser;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public Activity coverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
        return this;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Instant getAppointDatetime() {
        return appointDatetime;
    }

    public Activity appointDatetime(Instant appointDatetime) {
        this.appointDatetime = appointDatetime;
        return this;
    }

    public void setAppointDatetime(Instant appointDatetime) {
        this.appointDatetime = appointDatetime;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public Activity lowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
        return this;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public Activity upperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
        return this;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getPayType() {
        return payType;
    }

    public Activity payType(String payType) {
        this.payType = payType;
        return this;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public Activity deadline(Instant deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public String getComment() {
        return comment;
    }

    public Activity comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Activity isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Attendee> getAttendees() {
        return attendees;
    }

    public Activity attendees(Set<Attendee> attendees) {
        this.attendees = attendees;
        return this;
    }

    public Activity addAttendees(Attendee attendee) {
        this.attendees.add(attendee);
        attendee.setActivity(this);
        return this;
    }

    public Activity removeAttendees(Attendee attendee) {
        this.attendees.remove(attendee);
        attendee.setActivity(null);
        return this;
    }

    public void setAttendees(Set<Attendee> attendees) {
        this.attendees = attendees;
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
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", activitiyType=" + getActivitiyType() +
            ", descrption='" + getDescrption() + "'" +
            ", organizeUser='" + getOrganizeUser() + "'" +
            ", coverPicture='" + getCoverPicture() + "'" +
            ", appointDatetime='" + getAppointDatetime() + "'" +
            ", lowerLimit=" + getLowerLimit() +
            ", upperLimit=" + getUpperLimit() +
            ", payType='" + getPayType() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", comment='" + getComment() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
