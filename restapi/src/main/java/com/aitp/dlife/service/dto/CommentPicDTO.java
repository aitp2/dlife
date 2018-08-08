package com.aitp.dlife.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CommentPic entity.
 */
public class CommentPicDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String ossPath;

    private String createTime;

    private Long commentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOssPath() {
        return ossPath;
    }

    public void setOssPath(String ossPath) {
        this.ossPath = ossPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentPicDTO commentPicDTO = (CommentPicDTO) o;
        if(commentPicDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentPicDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentPicDTO{" +
            "id=" + getId() +
            ", ossPath='" + getOssPath() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
