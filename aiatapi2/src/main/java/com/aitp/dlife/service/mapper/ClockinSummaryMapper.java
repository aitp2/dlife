package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.ClockinSummaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClockinSummary and its DTO ClockinSummaryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClockinSummaryMapper extends EntityMapper<ClockinSummaryDTO, ClockinSummary> {



    default ClockinSummary fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClockinSummary clockinSummary = new ClockinSummary();
        clockinSummary.setId(id);
        return clockinSummary;
    }
}
