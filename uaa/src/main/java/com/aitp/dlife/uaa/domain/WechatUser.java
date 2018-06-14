package com.aitp.dlife.uaa.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WechatUser.
 */
@Entity
@Table(name = "wechat_user")
@Document(indexName = "wechatuser")
public class WechatUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 微信openid
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "微信openid", required = true)
    @Column(name = "open_id", length = 255, nullable = false)
    private String openId;

    /**
     * 微信号
     */
    @Size(max = 128)
    @ApiModelProperty(value = "微信号")
    @Column(name = "wechat_code", length = 128)
    private String wechatCode;

    /**
     * 用户名
     */
    @Size(max = 128)
    @ApiModelProperty(value = "用户名")
    @Column(name = "user_name", length = 128)
    private String userName;

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
     * 手机号码
     */
    @Size(max = 128)
    @ApiModelProperty(value = "手机号码")
    @Column(name = "mobile_num", length = 128)
    private String mobileNum;

    /**
     * 项目名称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "项目名称")
    @Column(name = "project", length = 128)
    private String project;

    /**
     * 座位号
     */
    @Size(max = 128)
    @ApiModelProperty(value = "座位号")
    @Column(name = "seat", length = 128)
    private String seat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public WechatUser openId(String openId) {
        this.openId = openId;
        return this;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public WechatUser wechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
        return this;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getUserName() {
        return userName;
    }

    public WechatUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public WechatUser nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public WechatUser avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public WechatUser mobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
        return this;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getProject() {
        return project;
    }

    public WechatUser project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSeat() {
        return seat;
    }

    public WechatUser seat(String seat) {
        this.seat = seat;
        return this;
    }

    public void setSeat(String seat) {
        this.seat = seat;
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
        WechatUser wechatUser = (WechatUser) o;
        if (wechatUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatUser{" +
            "id=" + getId() +
            ", openId='" + getOpenId() + "'" +
            ", wechatCode='" + getWechatCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", mobileNum='" + getMobileNum() + "'" +
            ", project='" + getProject() + "'" +
            ", seat='" + getSeat() + "'" +
            "}";
    }
}
