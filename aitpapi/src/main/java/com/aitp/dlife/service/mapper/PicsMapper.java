package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.PicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pics and its DTO PicsDTO.
 */
@Mapper(componentModel = "spring", uses = {FitnessActivityMapper.class, ClockInMapper.class})
public interface PicsMapper extends EntityMapper<PicsDTO, Pics> {

    @Mapping(source = "fitnessActivity.id", target = "fitnessActivityId")
    @Mapping(source = "clockIn.id", target = "clockInId")
    @Mapping(target = "createTime",expression = "java(InstantMapper.toDateString(pics.getCreateTime()))")
    PicsDTO toDto(Pics pics);

    @Mapping(source = "fitnessActivityId", target = "fitnessActivity")
    @Mapping(source = "clockInId", target = "clockIn")
    @Mapping(target = "createTime",expression = "java(InstantMapper.fromString(picsDTO.getCreateTime()))")
    Pics toEntity(PicsDTO picsDTO);

    default Pics fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pics pics = new Pics();
        pics.setId(id);
        return pics;
    }
}
