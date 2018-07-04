package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.PinfanPicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PinfanPics and its DTO PinfanPicsDTO.
 */
@Mapper(componentModel = "spring", uses = {PinFanActivityMapper.class,InstantMapper.class})
public interface PinfanPicsMapper extends EntityMapper<PinfanPicsDTO, PinfanPics> {

    @Mapping(source = "pinFanActivity.id", target = "pinFanActivityId")
    @Mapping(target = "createTime",expression = "java(InstantMapper.toDateString(pinfanPics.getCreateTime()))")
    PinfanPicsDTO toDto(PinfanPics pinfanPics);

    @Mapping(source = "pinFanActivityId", target = "pinFanActivity")
    @Mapping(target = "createTime",expression = "java(InstantMapper.fromString(pinfanPicsDTO.getCreateTime()))")
    PinfanPics toEntity(PinfanPicsDTO pinfanPicsDTO);

    default PinfanPics fromId(Long id) {
        if (id == null) {
            return null;
        }
        PinfanPics pinfanPics = new PinfanPics();
        pinfanPics.setId(id);
        return pinfanPics;
    }
}
