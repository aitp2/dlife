package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.ThumbsUpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ThumbsUp and its DTO ThumbsUpDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ThumbsUpMapper extends EntityMapper<ThumbsUpDTO, ThumbsUp> {



    default ThumbsUp fromId(Long id) {
        if (id == null) {
            return null;
        }
        ThumbsUp thumbsUp = new ThumbsUp();
        thumbsUp.setId(id);
        return thumbsUp;
    }
}
