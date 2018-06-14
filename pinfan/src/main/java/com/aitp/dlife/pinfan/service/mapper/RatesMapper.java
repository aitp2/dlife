package com.aitp.dlife.pinfan.service.mapper;

import com.aitp.dlife.pinfan.domain.*;
import com.aitp.dlife.pinfan.service.dto.RatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rates and its DTO RatesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RatesMapper extends EntityMapper<RatesDTO, Rates> {


    @Mapping(target = "ratesPics", ignore = true)
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
