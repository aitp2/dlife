package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.domain.UserTask;
import com.aitp.dlife.service.dto.UserTaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserTask and its DTO UserTaskDTO.
 */
@Mapper(componentModel = "spring", uses = {TaskDefineMapper.class})
public interface UserTaskMapper extends EntityMapper<UserTaskDTO, UserTask> {

    @Mapping(source = "task.id", target = "taskId")
    UserTaskDTO toDto(UserTask userTask);

    @Mapping(source = "taskId", target = "task")
    UserTask toEntity(UserTaskDTO userTaskDTO);

    default UserTask fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserTask userTask = new UserTask();
        userTask.setId(id);
        return userTask;
    }
}
