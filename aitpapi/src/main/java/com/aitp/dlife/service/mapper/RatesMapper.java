package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.RatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rates and its DTO RatesDTO.
 */
@Mapper(componentModel = "spring", uses = {PinFanActivityMapper.class})
public interface RatesMapper extends EntityMapper<RatesDTO, Rates> {

    @Mapping(source = "pinFanActivity.id", target = "pinFanActivityId")
    RatesDTO toDto(Rates rates);

//    @Mapping(target = "pinfanPics", ignore = true)
    @Mapping(source = "pinFanActivityId", target = "pinFanActivity")
    Rates toEntity(RatesDTO ratesDTO);

    default Rates fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rates rates = new Rates();
        rates.setId(id);
        return rates;
    }
}
