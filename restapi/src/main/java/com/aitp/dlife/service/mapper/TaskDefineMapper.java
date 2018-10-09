package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.TaskDefineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaskDefine and its DTO TaskDefineDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskDefineMapper extends EntityMapper<TaskDefineDTO, TaskDefine> {



    default TaskDefine fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskDefine taskDefine = new TaskDefine();
        taskDefine.setId(id);
        return taskDefine;
    }
}
