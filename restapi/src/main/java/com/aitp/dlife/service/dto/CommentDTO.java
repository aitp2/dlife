package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.aitp.dlife.domain.enumeration.CommentChannel;

/**
 * A DTO for the Comment entity.
 */
public class CommentDTO implements Serializable {

    private Long id;

    private Long parentId;

    private Long objectId;

    private CommentChannel channel;

    @Size(max = 128)
    private String wechatUserId;

    @Size(max = 1024)
    private String avatar;

    @Size(max = 128)
    private String nickName;

    @Size(max = 1024)
    private String content;

    private Integer rating1;

    private Integer rating2;

    private Integer rating3;

    private String createTime;

    private String modifyTime;

    private Set<CommentPicDTO> commentPics = new HashSet<>();;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public CommentChannel getChannel() {
        return channel;
    }

    public void setChannel(CommentChannel channel) {
        this.channel = channel;
    }

    public String getWechatUserId() {
        return wechatUserId;
    }

    public void setWechatUserId(String wechatUserId) {
        this.wechatUserId = wechatUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating1() {
        return rating1;
    }

    public void setRating1(Integer rating1) {
        this.rating1 = rating1;
    }

    public Integer getRating2() {
        return rating2;
    }

    public void setRating2(Integer rating2) {
        this.rating2 = rating2;
    }

    public Integer getRating3() {
        return rating3;
    }

    public void setRating3(Integer rating3) {
        this.rating3 = rating3;
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

    public Set<CommentPicDTO> getCommentPics() {
        return commentPics;
    }

    public void setCommentPics(Set<CommentPicDTO> commentPics) {
        this.commentPics = commentPics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if(commentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
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
