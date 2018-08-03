package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.EventMessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EventMessage and its DTO EventMessageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventMessageMapper extends EntityMapper<EventMessageDTO, EventMessage> {


    @Mapping(target = "messages", ignore = true)
    EventMessage toEntity(EventMessageDTO eventMessageDTO);

    default EventMessage fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventMessage eventMessage = new EventMessage();
        eventMessage.setId(id);
        return eventMessage;
    }
}
