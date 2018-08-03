package com.aitp.dlife.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Follow entity.
 */
public class FollowDTO implements Serializable {

    private Long id;

    private String followUserId;

    @Size(max = 128)
    private String followUserNickname;

    @Size(max = 1024)
    private String followUseravatar;

    private String followedUserId;

    @Size(max = 128)
    private String followedUserNickname;

    @Size(max = 1024)
    private String followedUseravatar;

    private Instant createTime;

    private Instant modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getFollowUserNickname() {
        return followUserNickname;
    }

    public void setFollowUserNickname(String followUserNickname) {
        this.followUserNickname = followUserNickname;
    }

    public String getFollowUseravatar() {
        return followUseravatar;
    }

    public void setFollowUseravatar(String followUseravatar) {
        this.followUseravatar = followUseravatar;
    }

    public String getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(String followedUserId) {
        this.followedUserId = followedUserId;
    }

    public String getFollowedUserNickname() {
        return followedUserNickname;
    }

    public void setFollowedUserNickname(String followedUserNickname) {
        this.followedUserNickname = followedUserNickname;
    }

    public String getFollowedUseravatar() {
        return followedUseravatar;
    }

    public void setFollowedUseravatar(String followedUseravatar) {
        this.followedUseravatar = followedUseravatar;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FollowDTO followDTO = (FollowDTO) o;
        if (followDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), followDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FollowDTO{" +
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
