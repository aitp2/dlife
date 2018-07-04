package com.aitp.dlife.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 关注记录信息
 */
@ApiModel(description = "关注记录信息")
@Entity
@Table(name = "follow")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follow_user_id")
    private String followUserId;

    @Size(max = 128)
    @Column(name = "follow_user_nickname", length = 128)
    private String followUserNickname;

    @Size(max = 1024)
    @Column(name = "follow_useravatar", length = 1024)
    private String followUseravatar;

    @Column(name = "followed_user_id")
    private String followedUserId;

    @Size(max = 128)
    @Column(name = "followed_user_nickname", length = 128)
    private String followedUserNickname;

    @Size(max = 1024)
    @Column(name = "followed_useravatar", length = 1024)
    private String followedUseravatar;

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

    public String getFollowUserId() {
        return followUserId;
    }

    public Follow followUserId(String followUserId) {
        this.followUserId = followUserId;
        return this;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getFollowUserNickname() {
        return followUserNickname;
    }

    public Follow followUserNickname(String followUserNickname) {
        this.followUserNickname = followUserNickname;
        return this;
    }

    public void setFollowUserNickname(String followUserNickname) {
        this.followUserNickname = followUserNickname;
    }

    public String getFollowUseravatar() {
        return followUseravatar;
    }

    public Follow followUseravatar(String followUseravatar) {
        this.followUseravatar = followUseravatar;
        return this;
    }

    public void setFollowUseravatar(String followUseravatar) {
        this.followUseravatar = followUseravatar;
    }

    public String getFollowedUserId() {
        return followedUserId;
    }

    public Follow followedUserId(String followedUserId) {
        this.followedUserId = followedUserId;
        return this;
    }

    public void setFollowedUserId(String followedUserId) {
        this.followedUserId = followedUserId;
    }

    public String getFollowedUserNickname() {
        return followedUserNickname;
    }

    public Follow followedUserNickname(String followedUserNickname) {
        this.followedUserNickname = followedUserNickname;
        return this;
    }

    public void setFollowedUserNickname(String followedUserNickname) {
        this.followedUserNickname = followedUserNickname;
    }

    public String getFollowedUseravatar() {
        return followedUseravatar;
    }

    public Follow followedUseravatar(String followedUseravatar) {
        this.followedUseravatar = followedUseravatar;
        return this;
    }

    public void setFollowedUseravatar(String followedUseravatar) {
        this.followedUseravatar = followedUseravatar;
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
            ", followUserId='" + getFollowUserId() + "'" +
            ", followUserNickname='" + getFollowUserNickname() + "'" +
            ", followUseravatar='" + getFollowUseravatar() + "'" +
            ", followedUserId='" + getFollowedUserId() + "'" +
            ", followedUserNickname='" + getFollowedUserNickname() + "'" +
            ", followedUseravatar='" + getFollowedUseravatar() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
