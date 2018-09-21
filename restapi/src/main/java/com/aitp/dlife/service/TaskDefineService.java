package com.aitp.dlife.service;

import com.aitp.dlife.domain.TaskDefine;
import com.aitp.dlife.repository.TaskDefineRepository;
import com.aitp.dlife.service.dto.TaskDefineDTO;
import com.aitp.dlife.service.mapper.TaskDefineMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing TaskDefine.
 */
@Service
@Transactional
public class TaskDefineService {

    private final Logger log = LoggerFactory.getLogger(TaskDefineService.class);

    private final TaskDefineRepository taskDefineRepository;

    private final TaskDefineMapper taskDefineMapper;

    public TaskDefineService(TaskDefineRepository taskDefineRepository, TaskDefineMapper taskDefineMapper) {
        this.taskDefineRepository = taskDefineRepository;
        this.taskDefineMapper = taskDefineMapper;
    }

    /**
     * Save a taskDefine.
     *
     * @param taskDefineDTO the entity to save
     * @return the persisted entity
     */
    public TaskDefineDTO save(TaskDefineDTO taskDefineDTO) {
        log.debug("Request to save TaskDefine : {}", taskDefineDTO);
        TaskDefine taskDefine = taskDefineMapper.toEntity(taskDefineDTO);
        taskDefine = taskDefineRepository.save(taskDefine);
        return taskDefineMapper.toDto(taskDefine);
    }

    /**
     * Get all the taskDefines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TaskDefineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskDefines");
        return taskDefineRepository.findAll(pageable)
            .map(taskDefineMapper::toDto);
    }


    /**
     * Get one taskDefine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TaskDefineDTO> findOne(Long id) {
        log.debug("Request to get TaskDefine : {}", id);
        return taskDefineRepository.findById(id)
            .map(taskDefineMapper::toDto);
    }

    /**
     * Delete the taskDefine by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskDefine : {}", id);
        taskDefineRepository.deleteById(id);
    }
}
