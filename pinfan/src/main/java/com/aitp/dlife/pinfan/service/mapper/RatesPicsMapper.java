package com.aitp.dlife.pinfan.service.mapper;

import com.aitp.dlife.pinfan.domain.*;
import com.aitp.dlife.pinfan.service.dto.RatesPicsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RatesPics and its DTO RatesPicsDTO.
 */
@Mapper(componentModel = "spring", uses = {RatesMapper.class})
public interface RatesPicsMapper extends EntityMapper<RatesPicsDTO, RatesPics> {

    @Mapping(source = "rate.id", target = "rateId")
    RatesPicsDTO toDto(RatesPics ratesPics);

    @Mapping(source = "rateId", target = "rate")
    RatesPics toEntity(RatesPicsDTO ratesPicsDTO);

    default RatesPics fromId(Long id) {
        if (id == null) {
            return null;
        }
        RatesPics ratesPics = new RatesPics();
        ratesPics.setId(id);
        return ratesPics;
    }
}
