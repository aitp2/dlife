package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.MessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Message and its DTO MessageDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMessageMapper.class})
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {

    @Mapping(source = "eventMessage.id", target = "eventMessageId")
    MessageDTO toDto(Message message);

    @Mapping(source = "eventMessageId", target = "eventMessage")
    Message toEntity(MessageDTO messageDTO);

    default Message fromId(Long id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
