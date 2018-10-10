package com.aitp.dlife.service;

import com.aitp.dlife.domain.UserTask;
import com.aitp.dlife.repository.UserTaskRepository;
import com.aitp.dlife.service.dto.UserTaskDTO;
import com.aitp.dlife.service.mapper.UserTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing UserTask.
 */
@Service
@Transactional
public class UserTaskService {

    private final Logger log = LoggerFactory.getLogger(UserTaskService.class);

    private final UserTaskRepository userTaskRepository;

    private final UserTaskMapper userTaskMapper;

    public UserTaskService(UserTaskRepository userTaskRepository, UserTaskMapper userTaskMapper) {
        this.userTaskRepository = userTaskRepository;
        this.userTaskMapper = userTaskMapper;
    }

    /**
     * Save a userTask.
     *
     * @param userTaskDTO the entity to save
     * @return the persisted entity
     */
    public UserTaskDTO save(UserTaskDTO userTaskDTO) {
        log.debug("Request to save UserTask : {}", userTaskDTO);
        UserTask userTask = userTaskMapper.toEntity(userTaskDTO);
        userTask = userTaskRepository.save(userTask);
        return userTaskMapper.toDto(userTask);
    }

    /**
     * Get all the userTasks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserTaskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserTasks");
        return userTaskRepository.findAll(pageable)
            .map(userTaskMapper::toDto);
    }


    /**
     * Get one userTask by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserTaskDTO> findOne(Long id) {
        log.debug("Request to get UserTask : {}", id);
        return userTaskRepository.findById(id)
            .map(userTaskMapper::toDto);
    }

    /**
     * Delete the userTask by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserTask : {}", id);
        userTaskRepository.deleteById(id);
    }
}
