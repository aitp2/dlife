package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.TaskGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaskGroup and its DTO TaskGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskGroupMapper extends EntityMapper<TaskGroupDTO, TaskGroup> {



    default TaskGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskGroup taskGroup = new TaskGroup();
        taskGroup.setId(id);
        return taskGroup;
    }
}
