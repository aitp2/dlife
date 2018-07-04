package com.aitp.dlife.service;

import com.aitp.dlife.domain.Attendee;
import com.aitp.dlife.repository.AttendeeRepository;
import com.aitp.dlife.service.dto.AttendeeDTO;
import com.aitp.dlife.service.mapper.AttendeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Attendee.
 */
@Service
@Transactional
public class AttendeeService {

    private final Logger log = LoggerFactory.getLogger(AttendeeService.class);

    private final AttendeeRepository attendeeRepository;

    private final AttendeeMapper attendeeMapper;

    public AttendeeService(AttendeeRepository attendeeRepository, AttendeeMapper attendeeMapper) {
        this.attendeeRepository = attendeeRepository;
        this.attendeeMapper = attendeeMapper;
    }

    /**
     * Save a attendee.
     *
     * @param attendeeDTO the entity to save
     * @return the persisted entity
     */
    public AttendeeDTO save(AttendeeDTO attendeeDTO) {
        log.debug("Request to save Attendee : {}", attendeeDTO);
        Attendee attendee = attendeeMapper.toEntity(attendeeDTO);
        attendee = attendeeRepository.save(attendee);
        return attendeeMapper.toDto(attendee);
    }

    /**
     * Get all the attendees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attendees");
        return attendeeRepository.findAll(pageable)
            .map(attendeeMapper::toDto);
    }

    /**
     * Get one attendee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AttendeeDTO findOne(Long id) {
        log.debug("Request to get Attendee : {}", id);
        Attendee attendee = attendeeRepository.findOne(id);
        return attendeeMapper.toDto(attendee);
    }

    /**
     * Delete the attendee by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendee : {}", id);
        attendeeRepository.delete(id);
    }
}
