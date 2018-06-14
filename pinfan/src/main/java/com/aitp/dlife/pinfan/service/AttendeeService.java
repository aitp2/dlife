package com.aitp.dlife.pinfan.service;

import com.aitp.dlife.pinfan.domain.Attendee;
import com.aitp.dlife.pinfan.repository.AttendeeRepository;
import com.aitp.dlife.pinfan.repository.search.AttendeeSearchRepository;
import com.aitp.dlife.pinfan.service.dto.AttendeeDTO;
import com.aitp.dlife.pinfan.service.mapper.AttendeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Attendee.
 */
@Service
@Transactional
public class AttendeeService {

    private final Logger log = LoggerFactory.getLogger(AttendeeService.class);

    private final AttendeeRepository attendeeRepository;

    private final AttendeeMapper attendeeMapper;

    private final AttendeeSearchRepository attendeeSearchRepository;

    public AttendeeService(AttendeeRepository attendeeRepository, AttendeeMapper attendeeMapper, AttendeeSearchRepository attendeeSearchRepository) {
        this.attendeeRepository = attendeeRepository;
        this.attendeeMapper = attendeeMapper;
        this.attendeeSearchRepository = attendeeSearchRepository;
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
        AttendeeDTO result = attendeeMapper.toDto(attendee);
        attendeeSearchRepository.save(attendee);
        return result;
    }

    /**
     * Get all the attendees.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AttendeeDTO> findAll() {
        log.debug("Request to get all Attendees");
        return attendeeRepository.findAll().stream()
            .map(attendeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
        attendeeSearchRepository.delete(id);
    }

    /**
     * Search for the attendee corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AttendeeDTO> search(String query) {
        log.debug("Request to search Attendees for query {}", query);
        return StreamSupport
            .stream(attendeeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(attendeeMapper::toDto)
            .collect(Collectors.toList());
    }
}
