package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.PinFanActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PinFanActivity and its DTO PinFanActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PinFanActivityMapper extends EntityMapper<PinFanActivityDTO, PinFanActivity> {


    @Mapping(target = "attendees", ignore = true)
    @Mapping(target = "pinfanPics", ignore = true)
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
