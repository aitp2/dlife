package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActivityParticipation and its DTO ActivityParticipationDTO.
 */
@Mapper(componentModel = "spring", uses = {FitnessActivityMapper.class})
public interface ActivityParticipationMapper extends EntityMapper<ActivityParticipationDTO, ActivityParticipation> {

    @Mapping(source = "fitnessActivity.id", target = "fitnessActivityId")
    ActivityParticipationDTO toDto(ActivityParticipation activityParticipation);

    @Mapping(target = "clockIns", ignore = true)
    @Mapping(source = "fitnessActivityId", target = "fitnessActivity")
    ActivityParticipation toEntity(ActivityParticipationDTO activityParticipationDTO);

    default ActivityParticipation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivityParticipation activityParticipation = new ActivityParticipation();
        activityParticipation.setId(id);
        return activityParticipation;
    }
}
