package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.request.ClockInRequest;
import com.aitp.dlife.service.dto.ClockInDTO;

import java.time.Instant;

import org.mapstruct.*;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ClockIn and its DTO ClockInDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityParticipationMapper.class,InstantMapper.class})
public interface ClockInForRequestMapper extends EntityMapper<ClockInRequest, ClockIn> {

  
    @Mapping(target = "activityParticipationId", ignore = true)
	@Mapping(target = "wechatUserId", ignore = true)
	ClockInRequest toDto(ClockIn clockIn);


    @Mapping(target = "activityId", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "pics", ignore = true)
    @Mapping(source = "activityParticipationId", target = "activityParticipation")
    @Mapping(target = "punchDateTime", expression = "java(Instant.now())")
    ClockIn toEntity(ClockInRequest clockInRequest);

    default ClockIn fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClockIn clockIn = new ClockIn();
        clockIn.setId(id);
        return clockIn;
    }
}
