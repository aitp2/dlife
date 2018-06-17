package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.ClockInDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClockIn and its DTO ClockInDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityParticipationMapper.class})
public interface ClockInMapper extends EntityMapper<ClockInDTO, ClockIn> {

    @Mapping(source = "activityParticipation.id", target = "activityParticipationId")
    ClockInDTO toDto(ClockIn clockIn);

    @Mapping(target = "pics", ignore = true)
    @Mapping(source = "activityParticipationId", target = "activityParticipation")
    ClockIn toEntity(ClockInDTO clockInDTO);

    default ClockIn fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClockIn clockIn = new ClockIn();
        clockIn.setId(id);
        return clockIn;
    }
}
