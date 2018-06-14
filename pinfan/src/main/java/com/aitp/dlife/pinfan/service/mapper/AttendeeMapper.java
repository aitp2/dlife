package com.aitp.dlife.pinfan.service.mapper;

import com.aitp.dlife.pinfan.domain.*;
import com.aitp.dlife.pinfan.service.dto.AttendeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attendee and its DTO AttendeeDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class})
public interface AttendeeMapper extends EntityMapper<AttendeeDTO, Attendee> {

    @Mapping(source = "activity.id", target = "activityId")
    AttendeeDTO toDto(Attendee attendee);

    @Mapping(source = "activityId", target = "activity")
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
