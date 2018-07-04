package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.CommentPicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommentPic and its DTO CommentPicDTO.
 */
@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface CommentPicMapper extends EntityMapper<CommentPicDTO, CommentPic> {

    @Mapping(source = "comment.id", target = "commentId")
    CommentPicDTO toDto(CommentPic commentPic);

    @Mapping(source = "commentId", target = "comment")
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
