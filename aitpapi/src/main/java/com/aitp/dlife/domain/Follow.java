package com.aitp.dlife.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 关注记录信息
 */
@ApiModel(description = "关注记录信息")
@Entity
@Table(name = "follow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follow_user_id")
    private Long followUserId;

    @Column(name = "followed_user_id")
    private Long followedUserId;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "modify_time")
    private Instant modifyTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public Follow followUserId(Long followUserId) {
        this.followUserId = followUserId;
        return this;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Long getFollowedUserId() {
        return followedUserId;
    }

    public Follow followedUserId(Long followedUserId) {
        this.followedUserId = followedUserId;
        return this;
    }

    public void setFollowedUserId(Long followedUserId) {
        this.followedUserId = followedUserId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Follow createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public Follow modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
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
        Follow follow = (Follow) o;
        if (follow.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), follow.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Follow{" +
            "id=" + getId() +
            ", followUserId=" + getFollowUserId() +
            ", followedUserId=" + getFollowedUserId() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
