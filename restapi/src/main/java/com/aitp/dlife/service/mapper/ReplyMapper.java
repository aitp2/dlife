package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.ReplyDTO;

import org.mapstruct.*;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class})
public interface ReplyMapper extends EntityMapper<ReplyDTO, Comment> {
	
	
	
    @Mapping(target = "createTime", expression = "java(InstantMapper.toDateString(comment.getCreateTime()))")
    ReplyDTO toDto(Comment comment);

    @Mapping(target = "channel", ignore = true)
	@Mapping(target = "modifyTime", ignore = true)
	@Mapping(target = "commentPics", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "objectId", ignore = true)
	@Mapping(target = "rating1", ignore = true)
	@Mapping(target = "rating2", ignore = true)
	@Mapping(target = "rating3", ignore = true)
	@Mapping(target = "replyCount", ignore = true)
    @Mapping(target = "createTime", expression = "java(InstantMapper.fromString(replyDTO.getCreateTime()))")
    Comment toEntity(ReplyDTO replyDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
