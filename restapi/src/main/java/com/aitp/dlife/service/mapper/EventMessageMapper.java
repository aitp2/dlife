package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.EventMessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EventMessage and its DTO EventMessageDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class})
public interface EventMessageMapper extends EntityMapper<EventMessageDTO, EventMessage> {

    @Mapping(target = "createTime", expression = "java(InstantMapper.toDateString(eventMessage.getCreateTime()))")
    EventMessageDTO toDto(EventMessage eventMessage);

    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "createTime", expression = "java(InstantMapper.fromString(eventMessageDTO.getCreateTime()))")
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
