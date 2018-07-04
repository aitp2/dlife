package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.WechatUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatUser and its DTO WechatUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WechatUserMapper extends EntityMapper<WechatUserDTO, WechatUser> {

    @Mapping(target = "createTime",expression = "java(InstantMapper.toDateString(wechatUser.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.toDateString(wechatUser.getModifyTime()))")
    WechatUserDTO toDto(WechatUser wechatUser);

    @Mapping(target = "createTime",expression = "java(InstantMapper.fromString(wechatUserDTO.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.fromString(wechatUserDTO.getModifyTime()))")
    WechatUser toEntity(WechatUserDTO wechatUserDTO);

    default WechatUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatUser wechatUser = new WechatUser();
        wechatUser.setId(id);
        return wechatUser;
    }
}
