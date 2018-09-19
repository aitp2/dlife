package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.domain.SystemTotalPoints;
import com.aitp.dlife.service.dto.SystemTotalPointsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SystemTotalPoints and its DTO SystemTotalPointsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemTotalPointsMapper extends EntityMapper<SystemTotalPointsDTO, SystemTotalPoints> {



    default SystemTotalPoints fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemTotalPoints systemTotalPoints = new SystemTotalPoints();
        systemTotalPoints.setId(id);
        return systemTotalPoints;
    }
}
