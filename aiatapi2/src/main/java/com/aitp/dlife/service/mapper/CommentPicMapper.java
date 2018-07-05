package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.CommentPicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommentPic and its DTO CommentPicDTO.
 */
@Mapper(componentModel = "spring", uses = {CommentMapper.class,InstantMapper.class})
public interface CommentPicMapper extends EntityMapper<CommentPicDTO, CommentPic> {

    @Mapping(source = "comment.id", target = "commentId")
    @Mapping(target = "createTime", expression = "java(InstantMapper.toDateString(commentPic.getCreateTime()))")
    CommentPicDTO toDto(CommentPic commentPic);

    @Mapping(source = "commentId", target = "comment")
    @Mapping(target = "createTime", expression = "java(InstantMapper.fromString(commentPicDTO.getCreateTime()))")
    CommentPic toEntity(CommentPicDTO commentPicDTO);

    default CommentPic fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommentPic commentPic = new CommentPic();
        commentPic.setId(id);
        return commentPic;
    }
}
