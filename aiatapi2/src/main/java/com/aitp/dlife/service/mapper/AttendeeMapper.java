package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.AttendeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attendee and its DTO AttendeeDTO.
 */
@Mapper(componentModel = "spring", uses = {PinFanActivityMapper.class,InstantMapper.class})
public interface AttendeeMapper extends EntityMapper<AttendeeDTO, Attendee> {

    @Mapping(source = "pinFanActivity.id", target = "pinFanActivityId")
    @Mapping(target = "participationTime",expression = "java(InstantMapper.toDateString(attendee.getParticipationTime()))")
    AttendeeDTO toDto(Attendee attendee);

    @Mapping(source = "pinFanActivityId", target = "pinFanActivity")
    @Mapping(target = "participationTime",expression = "java(InstantMapper.fromString(attendeeDTO.getParticipationTime()))")
    Attendee toEntity(AttendeeDTO attendeeDTO);

    default Attendee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendee attendee = new Attendee();
        attendee.setId(id);
        return attendee;
    }
}
