package com.aitp.dlife.pinfan.service.mapper;

import com.aitp.dlife.pinfan.domain.*;
import com.aitp.dlife.pinfan.service.dto.ActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {


    @Mapping(target = "attendees", ignore = true)
    Activity toEntity(ActivityDTO activityDTO);

    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}
