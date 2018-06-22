package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 拼饭参与评价信息
 */
@ApiModel(description = "拼饭参与评价信息")
@Entity
@Table(name = "rates")
@Cache(usage =  CacheConcurrencyStrategy.NONE)
public class Rates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 发布者id
     */
    @Size(max = 128)
    @ApiModelProperty(value = "发布者id")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    @Size(max = 1024)
    @Column(name = "comments", length = 1024)
    private String comments;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "modify_time")
    private Instant modifyTime;

    @OneToMany(mappedBy = "rate")
//    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<PinfanPics> pinfanPics = new HashSet<>();

    @ManyToOne
    private PinFanActivity pinFanActivity;

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

    public Rates wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public Rates avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public Rates nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getComments() {
        return comments;
    }

    public Rates comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getRating() {
        return rating;
    }

    public Rates rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Rates createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public Rates modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Set<PinfanPics> getPinfanPics() {
        return pinfanPics;
    }

    public Rates pinfanPics(Set<PinfanPics> pinfanPics) {
        this.pinfanPics = pinfanPics;
        return this;
    }

    public Rates addPinfanPics(PinfanPics pinfanPics) {
        this.pinfanPics.add(pinfanPics);
        pinfanPics.setRate(this);
        return this;
    }

    public Rates removePinfanPics(PinfanPics pinfanPics) {
        this.pinfanPics.remove(pinfanPics);
        pinfanPics.setRate(null);
        return this;
    }

    public void setPinfanPics(Set<PinfanPics> pinfanPics) {
        this.pinfanPics = pinfanPics;
    }

    public PinFanActivity getPinFanActivity() {
        return pinFanActivity;
    }

    public Rates pinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
        return this;
    }

    public void setPinFanActivity(PinFanActivity pinFanActivity) {
        this.pinFanActivity = pinFanActivity;
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
        Rates rates = (Rates) o;
        if (rates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rates{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", comments='" + getComments() + "'" +
            ", rating=" + getRating() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
