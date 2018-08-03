package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.FitnessActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FitnessActivity and its DTO FitnessActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FitnessActivityMapper extends EntityMapper<FitnessActivityDTO, FitnessActivity> {


    @Mapping(target = "activityParticipations", ignore = true)
    @Mapping(target = "pics", ignore = true)
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
