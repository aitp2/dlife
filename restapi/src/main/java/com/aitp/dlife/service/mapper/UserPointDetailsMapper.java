package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.domain.UserPointDetails;
import com.aitp.dlife.service.dto.UserPointDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserPointDetails and its DTO UserPointDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserPointDetailsMapper extends EntityMapper<UserPointDetailsDTO, UserPointDetails> {



    default UserPointDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPointDetails userPointDetails = new UserPointDetails();
        userPointDetails.setId(id);
        return userPointDetails;
    }
}
