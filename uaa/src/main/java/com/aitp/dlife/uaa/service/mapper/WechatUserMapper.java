package com.aitp.dlife.uaa.service.mapper;

import com.aitp.dlife.uaa.domain.*;
import com.aitp.dlife.uaa.service.dto.WechatUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WechatUser and its DTO WechatUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WechatUserMapper extends EntityMapper<WechatUserDTO, WechatUser> {



    default WechatUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        WechatUser wechatUser = new WechatUser();
        wechatUser.setId(id);
        return wechatUser;
    }
}
