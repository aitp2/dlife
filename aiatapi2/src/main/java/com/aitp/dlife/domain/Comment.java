package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.aitp.dlife.domain.enumeration.CommentChannel;

/**
 * 评论信息
 */
@ApiModel(description = "评论信息")
@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上一级评论id
     */
    @ApiModelProperty(value = "上一级评论id")
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 评论对象id
     */
    @ApiModelProperty(value = "评论对象id")
    @Column(name = "object_id")
    private Long objectId;

    /**
     * 评论渠道
     */
    @ApiModelProperty(value = "评论渠道")
    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private CommentChannel channel;

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

    /**
     * 评论内容
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "评论内容")
    @Column(name = "content", length = 1024)
    private String content;

    /**
     * 评价级别 1
     */
    @ApiModelProperty(value = "评价级别 1")
    @Column(name = "rating_1")
    private Integer rating1;

    /**
     * 评价级别 2
     */
    @ApiModelProperty(value = "评价级别 2")
    @Column(name = "rating_2")
    private Integer rating2;

    /**
     * 评价级别 3
     */
    @ApiModelProperty(value = "评价级别 3")
    @Column(name = "rating_3")
    private Integer rating3;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "modify_time")
    private Instant modifyTime;

    @OneToMany(mappedBy = "comment")
    @JsonIgnore
    private Set<CommentPic> commentPics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public Comment parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public Comment objectId(Long objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public CommentChannel getChannel() {
        return channel;
    }

    public Comment channel(CommentChannel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(CommentChannel channel) {
        this.channel = channel;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public Comment wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public Comment avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public Comment nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating1() {
        return rating1;
    }

    public Comment rating1(Integer rating1) {
        this.rating1 = rating1;
        return this;
    }

    public void setRating1(Integer rating1) {
        this.rating1 = rating1;
    }

    public Integer getRating2() {
        return rating2;
    }

    public Comment rating2(Integer rating2) {
        this.rating2 = rating2;
        return this;
    }

    public void setRating2(Integer rating2) {
        this.rating2 = rating2;
    }

    public Integer getRating3() {
        return rating3;
    }

    public Comment rating3(Integer rating3) {
        this.rating3 = rating3;
        return this;
    }

    public void setRating3(Integer rating3) {
        this.rating3 = rating3;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Comment createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getModifyTime() {
        return modifyTime;
    }

    public Comment modifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Set<CommentPic> getCommentPics() {
        return commentPics;
    }

    public Comment commentPics(Set<CommentPic> commentPics) {
        this.commentPics = commentPics;
        return this;
    }

    public Comment addCommentPic(CommentPic commentPic) {
        this.commentPics.add(commentPic);
        commentPic.setComment(this);
        return this;
    }

    public Comment removeCommentPic(CommentPic commentPic) {
        this.commentPics.remove(commentPic);
        commentPic.setComment(null);
        return this;
    }

    public void setCommentPics(Set<CommentPic> commentPics) {
        this.commentPics = commentPics;
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
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", objectId=" + getObjectId() +
            ", channel='" + getChannel() + "'" +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", content='" + getContent() + "'" +
            ", rating1=" + getRating1() +
            ", rating2=" + getRating2() +
            ", rating3=" + getRating3() +
            ", createTime='" + getCreateTime() + "'" +
            ", modifyTime='" + getModifyTime() + "'" +
            "}";
    }
}
