package com.aitp.dlife.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 用户信息
 */
@ApiModel(description = "用户信息")
@Entity
@Table(name = "wechat_user")
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

    /**
     * 个人简介
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "个人简介")
    @Column(name = "introduce", length = 1024)
    private String introduce;

    /**
     * 性别
     * 值为1时是男性，值为2时是女性，值为0时是未知
     */
    @Max(value = 2)
    @ApiModelProperty(value = "性别 值为1时是男性，值为2时是女性，值为0时是未知")
    @Column(name = "sex")
    private Integer sex;

    /**
     * 角色
     */
    @Size(max = 255)
    @ApiModelProperty(value = "角色")
    @Column(name = "company_role", length = 255)
    private String companyRole;

    /**
     * 厨师标志
     * 值为1时是厨师，值为0时不是厨师
     */
    @Max(value = 2)
    @ApiModelProperty(value = "厨师标志 值为1时是厨师，值为0时不是厨师")
    @Column(name = "cook_flag")
    private Integer cookFlag;

    /**
     * 兴趣
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "兴趣")
    @Column(name = "intesting", length = 1024)
    private String intesting;

    /**
     * 擅长
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "擅长")
    @Column(name = "skill", length = 1024)
    private String skill;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Instant createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @Column(name = "modify_time")
    private Instant modifyTime;

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

    public String getIntroduce() {
        return introduce;
    }

    public WechatUser introduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getSex() {
        return sex;
    }

    public WechatUser sex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCompanyRole() {
        return companyRole;
    }

    public WechatUser companyRole(String companyRole) {
        this.companyRole = companyRole;
        return this;
    }

    public void setCompanyRole(String companyRole) {
        this.companyRole = companyRole;
    }

    public Integer getCookFlag() {
        return cookFlag;
    }

    public WechatUser cookFlag(Integer cookFlag) {
        this.cookFlag = cookFlag;
        return this;
    }

    public void setCookFlag(Integer cookFlag) {
        this.cookFlag = cookFlag;
    }

    public String getIntesting() {
        return intesting;
    }

    public WechatUser intesting(String intesting) {
        this.intesting = intesting;
        return this;
    }

    public void setIntesting(String intesting) {
        this.intesting = intesting;
    }

    public String getSkill() {
        return skill;
    }

    public WechatUser skill(String skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public WechatUser createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public WechatUser modifyTime(Instant modifyTime) {
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
            ", introduce='" + getIntroduce() + "'" +
            ", sex=" + getSex() +
            ", companyRole='" + getCompanyRole() + "'" +
            ", cookFlag=" + getCookFlag() +
            ", intesting='" + getIntesting() + "'" +
            ", skill='" + getSkill() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
