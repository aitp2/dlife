package com.aitp.dlife.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 提问人ID
     */
    @Size(max = 128)
    @ApiModelProperty(value = "提问人ID")
    @Column(name = "wechat_user_id", length = 128)
    private String wechatUserId;

    /**
     * 提问人昵称
     */
    @Size(max = 128)
    @ApiModelProperty(value = "提问人昵称")
    @Column(name = "nick_name", length = 128)
    private String nickName;

    /**
     * 提问人头像
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "提问人头像")
    @Column(name = "avatar", length = 1024)
    private String avatar;

    /**
     * 问题标题
     */
    @Size(max = 256)
    @ApiModelProperty(value = "问题标题")
    @Column(name = "title", length = 256)
    private String title;

    /**
     * 问题描述
     */
    @Size(max = 1024)
    @ApiModelProperty(value = "问题描述")
    @Column(name = "description", length = 1024)
    private String description;

    /**
     * 提问时间
     */
    @ApiModelProperty(value = "提问时间")
    @Column(name = "create_time")
    private Instant createTime;

    /**
     * 回答数
     */
    @ApiModelProperty(value = "回答数")
    @Column(name = "answer_count")
    private Integer answerCount;

    /**
     * 浏览数
     */
    @ApiModelProperty(value = "浏览数")
    @Column(name = "reading_count")
    private Integer readingCount;

    @OneToMany(mappedBy = "question")
    private Set<QuestionPic> questionPics = new HashSet<>();

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

    public Question wechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
        return this;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getNickName() {
        return nickName;
    }

    public Question nickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public Question avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public Question title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Question description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Question createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public Question answerCount(Integer answerCount) {
        this.answerCount = answerCount;
        return this;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getReadingCount() {
        return readingCount;
    }

    public Question readingCount(Integer readingCount) {
        this.readingCount = readingCount;
        return this;
    }

    public void setReadingCount(Integer readingCount) {
        this.readingCount = readingCount;
    }

    public Set<QuestionPic> getQuestionPics() {
        return questionPics;
    }

    public Question questionPics(Set<QuestionPic> questionPics) {
        this.questionPics = questionPics;
        return this;
    }

    public Question addQuestionPic(QuestionPic questionPic) {
        this.questionPics.add(questionPic);
        questionPic.setQuestion(this);
        return this;
    }

    public Question removeQuestionPic(QuestionPic questionPic) {
        this.questionPics.remove(questionPic);
        questionPic.setQuestion(null);
        return this;
    }

    public void setQuestionPics(Set<QuestionPic> questionPics) {
        this.questionPics = questionPics;
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
        Question question = (Question) o;
        if (question.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", wechatUserId='" + getWechatUserId() + "'" +
            ", nickName='" + getNickName() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", answerCount=" + getAnswerCount() +
            ", readingCount=" + getReadingCount() +
            "}";
    }
}
