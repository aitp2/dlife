package com.aitp.dlife.service;

import com.aitp.dlife.domain.UserEvent;
import com.aitp.dlife.repository.UserEventRepository;
import com.aitp.dlife.service.dto.UserEventDTO;
import com.aitp.dlife.service.mapper.UserEventMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing UserEvent.
 */
@Service
@Transactional
public class UserEventService {

    private final Logger log = LoggerFactory.getLogger(UserEventService.class);

    private final UserEventRepository userEventRepository;

    private final UserEventMapper userEventMapper;

    public UserEventService(UserEventRepository userEventRepository, UserEventMapper userEventMapper) {
        this.userEventRepository = userEventRepository;
        this.userEventMapper = userEventMapper;
    }

    /**
     * Save a userEvent.
     *
     * @param userEventDTO the entity to save
     * @return the persisted entity
     */
    public UserEventDTO save(UserEventDTO userEventDTO) {
        log.debug("Request to save UserEvent : {}", userEventDTO);
        UserEvent userEvent = userEventMapper.toEntity(userEventDTO);
        userEvent = userEventRepository.save(userEvent);
        return userEventMapper.toDto(userEvent);
    }

    /**
     * Get all the userEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserEvents");
        return userEventRepository.findAll(pageable)
            .map(userEventMapper::toDto);
    }


    /**
     * Get one userEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserEventDTO> findOne(Long id) {
        log.debug("Request to get UserEvent : {}", id);
        return userEventRepository.findById(id)
            .map(userEventMapper::toDto);
    }

    /**
     * Delete the userEvent by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserEvent : {}", id);
        userEventRepository.deleteById(id);
    }
}
