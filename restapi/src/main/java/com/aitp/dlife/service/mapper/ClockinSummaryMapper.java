package com.aitp.dlife.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aitp.dlife.domain.ClockinSummary;
import com.aitp.dlife.service.dto.ClockinSummaryDTO;

/**
 * Mapper for the entity ClockinSummary and its DTO ClockinSummaryDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class})
public interface ClockinSummaryMapper extends EntityMapper<ClockinSummaryDTO, ClockinSummary> {


	
    @Mapping(target = "lastClockInTime", expression = "java(InstantMapper.toDateString(clockinSummary.getLastClockInTime()))")
	ClockinSummaryDTO toDto(ClockinSummary clockinSummary);
    

    @Mapping(target = "lastClockInTime", expression = "java(InstantMapper.fromString(clockinSummaryDTO.getLastClockInTime()))")
    ClockinSummary toEntity(ClockinSummaryDTO clockinSummaryDTO);
	

    default ClockinSummary fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClockinSummary clockinSummary = new ClockinSummary();
        clockinSummary.setId(id);
        return clockinSummary;
    }
}
