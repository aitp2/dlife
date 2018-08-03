package com.aitp.dlife.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.service.dto.FitnessActivityDTO;

/**
 * Mapper for the entity FitnessActivity and its DTO FitnessActivityDTO.
 */
@Mapper(componentModel = "spring", uses = { InstantMapper.class,ActivityParticipationMapper.class })
public interface FitnessActivityMapper extends EntityMapper<FitnessActivityDTO, FitnessActivity> {

	// @Mapping(source = "images", target = "images")
	@Mapping(target = "signStartTime", expression = "java(InstantMapper.toDateString(fitnessActivity.getSignStartTime()))")
	@Mapping(target = "signEndTime", expression = "java(InstantMapper.toDateString(fitnessActivity.getSignEndTime()))")
	@Mapping(target = "activityStartTime", expression = "java(InstantMapper.toDateString(fitnessActivity.getActivityStartTime()))")
	@Mapping(target = "activityEndTime", expression = "java(InstantMapper.toDateString(fitnessActivity.getActivityEndTime()))")
	@Mapping(target = "attendCount", expression = "java(fitnessActivity.getActivityParticipations() == null ? 0 : fitnessActivity.getActivityParticipations().size())")
	@Mapping(source="activityParticipations",target ="activityParticipations")
	FitnessActivityDTO toDto(FitnessActivity fitnessActivity);

	@Mapping(target = "activityParticipations", ignore = true)
	@Mapping(target = "images", ignore = true)
	@Mapping(target = "signStartTime", expression = "java(InstantMapper.fromString(fitnessActivityDTO.getSignStartTime()))")
	@Mapping(target = "signEndTime", expression = "java(InstantMapper.fromString(fitnessActivityDTO.getSignEndTime()))")
	@Mapping(target = "activityStartTime", expression = "java(InstantMapper.fromString(fitnessActivityDTO.getActivityStartTime()))")
	@Mapping(target = "activityEndTime", expression = "java(InstantMapper.fromString(fitnessActivityDTO.getActivityEndTime()))")
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
