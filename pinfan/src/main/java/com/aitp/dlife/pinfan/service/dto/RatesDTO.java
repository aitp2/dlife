package com.aitp.dlife.pinfan.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Rates entity.
 */
public class RatesDTO implements Serializable {

    private Long id;

    private Long wechatUserId;

    @Size(max = 128)
    private String nickName;

    @Size(max = 1024)
    private String avatar;

    private Long activityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatesDTO ratesDTO = (RatesDTO) o;
        if(ratesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatesDTO{" +
            "id=" + getId() +
            ", wechatUserId=" + getWechatUserId() +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", activityId=" + getActivityId() +
            "}";
    }
}
