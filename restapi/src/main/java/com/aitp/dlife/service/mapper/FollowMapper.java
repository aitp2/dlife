package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.FollowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Follow and its DTO FollowDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class})
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {


    @Mapping(target = "createTime",expression = "java(InstantMapper.toDateString(follow.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.toDateString(follow.getModifyTime()))")
    FollowDTO toDto(Follow follow);

    @Mapping(target = "createTime",expression = "java(InstantMapper.fromString(followDTO.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.fromString(followDTO.getModifyTime()))")
    Follow toEntity(FollowDTO followDTO);

    default Follow fromId(Long id) {
        if (id == null) {
            return null;
        }
        Follow follow = new Follow();
        follow.setId(id);
        return follow;
    }
}
