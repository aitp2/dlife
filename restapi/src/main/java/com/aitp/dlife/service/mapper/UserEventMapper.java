package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.domain.UserEvent;
import com.aitp.dlife.service.dto.UserEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserEvent and its DTO UserEventDTO.
 */
@Mapper(componentModel = "spring", uses = {TaskDefineMapper.class})
public interface UserEventMapper extends EntityMapper<UserEventDTO, UserEvent> {

    @Mapping(source = "applyTask.id", target = "applyTaskId")
    UserEventDTO toDto(UserEvent userEvent);

    @Mapping(source = "applyTaskId", target = "applyTask")
    UserEvent toEntity(UserEventDTO userEventDTO);

    default UserEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserEvent userEvent = new UserEvent();
        userEvent.setId(id);
        return userEvent;
    }
}
