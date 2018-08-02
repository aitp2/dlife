package com.aitp.dlife.service;

import com.aitp.dlife.domain.EventMessage;
import com.aitp.dlife.repository.EventMessageRepository;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.mapper.EventMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing EventMessage.
 */
@Service
@Transactional
public class EventMessageService {

    private final Logger log = LoggerFactory.getLogger(EventMessageService.class);

    private final EventMessageRepository eventMessageRepository;

    private final EventMessageMapper eventMessageMapper;

    public EventMessageService(EventMessageRepository eventMessageRepository, EventMessageMapper eventMessageMapper) {
        this.eventMessageRepository = eventMessageRepository;
        this.eventMessageMapper = eventMessageMapper;
    }

    /**
     * Save a eventMessage.
     *
     * @param eventMessageDTO the entity to save
     * @return the persisted entity
     */
    public EventMessageDTO save(EventMessageDTO eventMessageDTO) {
        log.debug("Request to save EventMessage : {}", eventMessageDTO);
        EventMessage eventMessage = eventMessageMapper.toEntity(eventMessageDTO);
        eventMessage = eventMessageRepository.save(eventMessage);
        return eventMessageMapper.toDto(eventMessage);
    }

    /**
     * Get all the eventMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EventMessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventMessages");
        return eventMessageRepository.findAll(pageable)
            .map(eventMessageMapper::toDto);
    }


    /**
     * Get one eventMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EventMessageDTO> findOne(Long id) {
        log.debug("Request to get EventMessage : {}", id);
        return eventMessageRepository.findById(id)
            .map(eventMessageMapper::toDto);
    }

    /**
     * Delete the eventMessage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EventMessage : {}", id);
        eventMessageRepository.deleteById(id);
    }
}
