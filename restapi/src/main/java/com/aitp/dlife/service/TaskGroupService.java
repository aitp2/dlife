package com.aitp.dlife.service;

import com.aitp.dlife.domain.TaskGroup;
import com.aitp.dlife.repository.TaskGroupRepository;
import com.aitp.dlife.service.dto.TaskGroupDTO;
import com.aitp.dlife.service.mapper.TaskGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing TaskGroup.
 */
@Service
@Transactional
public class TaskGroupService {

    private final Logger log = LoggerFactory.getLogger(TaskGroupService.class);

    private final TaskGroupRepository taskGroupRepository;

    private final TaskGroupMapper taskGroupMapper;

    public TaskGroupService(TaskGroupRepository taskGroupRepository, TaskGroupMapper taskGroupMapper) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupMapper = taskGroupMapper;
    }

    /**
     * Save a taskGroup.
     *
     * @param taskGroupDTO the entity to save
     * @return the persisted entity
     */
    public TaskGroupDTO save(TaskGroupDTO taskGroupDTO) {
        log.debug("Request to save TaskGroup : {}", taskGroupDTO);
        TaskGroup taskGroup = taskGroupMapper.toEntity(taskGroupDTO);
        taskGroup = taskGroupRepository.save(taskGroup);
        return taskGroupMapper.toDto(taskGroup);
    }

    /**
     * Get all the taskGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TaskGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskGroups");
        return taskGroupRepository.findAll(pageable)
            .map(taskGroupMapper::toDto);
    }


    /**
     * Get one taskGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TaskGroupDTO> findOne(Long id) {
        log.debug("Request to get TaskGroup : {}", id);
        return taskGroupRepository.findById(id)
            .map(taskGroupMapper::toDto);
    }

    /**
     * Delete the taskGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskGroup : {}", id);
        taskGroupRepository.deleteById(id);
    }
}
