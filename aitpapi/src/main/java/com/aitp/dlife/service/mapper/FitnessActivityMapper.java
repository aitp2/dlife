package com.aitp.dlife.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.service.dto.FitnessActivityDTO;

/**
 * Mapper for the entity FitnessActivity and its DTO FitnessActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FitnessActivityMapper extends EntityMapper<FitnessActivityDTO, FitnessActivity> {

	
    @Mapping(source = "images", target = "images")
    FitnessActivityDTO toDto(FitnessActivity fitnessActivity);

    @Mapping(target = "activityParticipations", ignore = true)
    @Mapping(target = "images", ignore = true)
    FitnessActivity toEntity(FitnessActivityDTO fitnessActivityDTO);

    default FitnessActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        FitnessActivity fitnessActivity = new FitnessActivity();
        fitnessActivity.setId(id);
        return fitnessActivity;
    }
}
