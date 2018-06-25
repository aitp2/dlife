package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the WechatUser entity.
 */
public class WechatUserDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String openId;

    @Size(max = 128)
    private String wechatCode;

    @Size(max = 128)
    private String userName;

    @Size(max = 128)
    private String nickName;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String mobileNum;

    @Size(max = 128)
    private String project;

    @Size(max = 128)
    private String seat;

    @Size(max = 1024)
    private String introduce;

    private Boolean sex;

    @Size(max = 255)
    private String companyRole;

    private Boolean cookFlag;

    @Size(max = 1024)
    private String intesting;

    @Size(max = 1024)
    private String skill;

    private String createTime;

    private String modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Boolean isSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getCompanyRole() {
        return companyRole;
    }

    public void setCompanyRole(String companyRole) {
        this.companyRole = companyRole;
    }

    public Boolean isCookFlag() {
        return cookFlag;
    }

    public void setCookFlag(Boolean cookFlag) {
        this.cookFlag = cookFlag;
    }

    public String getIntesting() {
        return intesting;
    }

    public void setIntesting(String intesting) {
        this.intesting = intesting;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
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

        WechatUserDTO wechatUserDTO = (WechatUserDTO) o;
        if(wechatUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wechatUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WechatUserDTO{" +
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
            ", sex='" + isSex() + "'" +
            ", companyRole='" + getCompanyRole() + "'" +
            ", cookFlag='" + isCookFlag() + "'" +
            ", intesting='" + getIntesting() + "'" +
            ", skill='" + getSkill() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
