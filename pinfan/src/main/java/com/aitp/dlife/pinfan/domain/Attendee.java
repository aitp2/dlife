package com.aitp.dlife.pinfan.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Attendee.
 */
@Entity
@Table(name = "attendee")
@Document(indexName = "attendee")
public class Attendee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 参与人ID
     */
    @ApiModelProperty(value = "参与人ID")
    @Column(name = "wechat_user_id")
    private Long wechatUserId;

    /**
     * 参与者昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "参与者昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 参与者头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "参与者头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    @ManyToOne
    private Activity activity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWechatUserId() {
        return wechatUserId;
    }

    public Attendee wechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(Long wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public Attendee nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public Attendee avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Activity getActivity() {
        return activity;
    }

    public Attendee activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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
        Attendee attendee = (Attendee) o;
        if (attendee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attendee{" +
            "id=" + getId() +
            ", wechatUserId=" + getWechatUserId() +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            "}";
    }
}
