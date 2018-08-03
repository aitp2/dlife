package com.aitp.dlife.service;

import com.aitp.dlife.domain.ActivityParticipation;
import com.aitp.dlife.repository.ActivityParticipationRepository;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.service.mapper.ActivityParticipationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ActivityParticipation.
 */
@Service
@Transactional
public class ActivityParticipationService {

    private final Logger log = LoggerFactory.getLogger(ActivityParticipationService.class);

    private final ActivityParticipationRepository activityParticipationRepository;

    private final ActivityParticipationMapper activityParticipationMapper;

    public ActivityParticipationService(ActivityParticipationRepository activityParticipationRepository, ActivityParticipationMapper activityParticipationMapper) {
        this.activityParticipationRepository = activityParticipationRepository;
        this.activityParticipationMapper = activityParticipationMapper;
    }

    /**
     * Save a activityParticipation.
     *
     * @param activityParticipationDTO the entity to save
     * @return the persisted entity
     */
    public ActivityParticipationDTO save(ActivityParticipationDTO activityParticipationDTO) {
        log.debug("Request to save ActivityParticipation : {}", activityParticipationDTO);
        ActivityParticipation activityParticipation = activityParticipationMapper.toEntity(activityParticipationDTO);
        activityParticipation = activityParticipationRepository.save(activityParticipation);
        return activityParticipationMapper.toDto(activityParticipation);
    }

    /**
     * Get all the activityParticipations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ActivityParticipationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityParticipations");
        return activityParticipationRepository.findAll(pageable)
            .map(activityParticipationMapper::toDto);
    }


    /**
     * Get one activityParticipation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ActivityParticipationDTO> findOne(Long id) {
        log.debug("Request to get ActivityParticipation : {}", id);
        return activityParticipationRepository.findById(id)
            .map(activityParticipationMapper::toDto);
    }

    /**
     * Delete the activityParticipation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ActivityParticipation : {}", id);
        activityParticipationRepository.deleteById(id);
    }
}
