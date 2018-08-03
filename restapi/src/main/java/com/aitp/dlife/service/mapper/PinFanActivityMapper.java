package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.PinFanActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PinFanActivity and its DTO PinFanActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class})
public interface PinFanActivityMapper extends EntityMapper<PinFanActivityDTO, PinFanActivity> {

    @Mapping(target = "appointDatetime",expression = "java(InstantMapper.toDateString(entity.getAppointDatetime()))")
    @Mapping(target = "appointEndDatetime",expression = "java(InstantMapper.toDateString(entity.getAppointEndDatetime()))")
    @Mapping(target = "deadline",expression = "java(InstantMapper.toDateString(entity.getDeadline()))")
    PinFanActivityDTO toDto(PinFanActivity entity);

    @Mapping(target = "attendees", ignore = true)
    @Mapping(target = "pinfanPics", ignore = true)
    @Mapping(target = "deadline",expression = "java(InstantMapper.fromString(pinFanActivityDTO.getDeadline()))")
    @Mapping(target = "appointDatetime",expression = "java(InstantMapper.fromString(pinFanActivityDTO.getAppointDatetime()))")
    @Mapping(target = "appointEndDatetime",expression = "java(InstantMapper.fromString(pinFanActivityDTO.getAppointEndDatetime()))")

    PinFanActivity toEntity(PinFanActivityDTO pinFanActivityDTO);

    default PinFanActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        PinFanActivity pinFanActivity = new PinFanActivity();
        pinFanActivity.setId(id);
        return pinFanActivity;
    }
}
