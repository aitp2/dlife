package com.aitp.dlife.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aitp.dlife.domain.Pics;
import com.aitp.dlife.request.PicsRequest;

@Mapper(componentModel = "spring", uses = {FitnessActivityMapper.class, ClockInMapper.class})
public interface PicsForRequestMapper extends EntityMapper<PicsRequest, Pics> {

	
		PicsRequest toDto(Pics pics);

	    @Mapping(target = "clockIn", ignore = true)
		@Mapping(target = "fitnessActivity", ignore = true)
		@Mapping(target = "id", ignore = true)
		@Mapping(target = "createTime",expression = "java(Instant.now())")
	    Pics toEntity(PicsRequest picsDTO);

	    default Pics fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        Pics pics = new Pics();
	        pics.setId(id);
	        return pics;
	    }
	
	
}
