package com.aitp.dlife.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aitp.dlife.domain.ActivityParticipation;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;

/**
 * Mapper for the entity ActivityParticipation and its DTO ActivityParticipationDTO.
 */
@Mapper(componentModel = "spring", uses = {FitnessActivityMapper.class})
public interface ActivityParticipationMapper extends EntityMapper<ActivityParticipationDTO, ActivityParticipation> {

    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(target = "participationTime", expression = "java(InstantMapper.toDateString(activityParticipation.getParticipationTime()))")
    @Mapping(target = "clockinCount", expression = "java(activityParticipation.getClockIns() == null ? 0 : activityParticipation.getClockIns().size())")
    @Mapping(source = "activity.title", target = "activityTitle")
    ActivityParticipationDTO toDto(ActivityParticipation activityParticipation);

    @Mapping(target = "clockIns", ignore = true)
    @Mapping(source = "activityId", target = "activity")
    @Mapping(target = "participationTime", expression = "java(InstantMapper.fromString(activityParticipationDTO.getParticipationTime()))")
    ActivityParticipation toEntity(ActivityParticipationDTO activityParticipationDTO);

    default ActivityParticipation fromId(Long id) {
        if (id == null) {
            return null;        }
        ActivityParticipation activityParticipation = new ActivityParticipation();
        activityParticipation.setId(id);
        return activityParticipation;
    }
}
