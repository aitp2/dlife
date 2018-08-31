package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.CommentDTO;

import org.mapstruct.*;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class,CommentPicMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
	
	
	
	@Mapping(target = "thumbsUpDTOs", ignore = true)
	@Mapping(source="reply_count",target = "replyCount")
    @Mapping(target = "createTime", expression = "java(InstantMapper.toDateString(comment.getCreateTime()))")
    @Mapping(target = "modifyTime", expression = "java(InstantMapper.toDateString(comment.getModifyTime()))")
    CommentDTO toDto(Comment comment);

    @Mapping(target = "module", ignore = true)
	@Mapping(target = "parentId", ignore = true)
	@Mapping(target = "rp_avatar", ignore = true)
	@Mapping(target = "rp_nick_name", ignore = true)
	@Mapping(target = "rp_wechat_user_id", ignore = true)
	@Mapping(target = "commentPics", ignore = true)
	@Mapping(target="reply_count",source = "replyCount")
    @Mapping(target = "createTime", expression = "java(InstantMapper.fromString(commentDTO.getCreateTime()))")
    @Mapping(target = "modifyTime", expression = "java(InstantMapper.fromString(commentDTO.getModifyTime()))")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
