package com.aitp.dlife.service;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.repository.FitnessActivityRepository;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.mapper.FitnessActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing FitnessActivity.
 */
@Service
@Transactional
public class FitnessActivityService {

    private final Logger log = LoggerFactory.getLogger(FitnessActivityService.class);

    private final FitnessActivityRepository fitnessActivityRepository;

    private final FitnessActivityMapper fitnessActivityMapper;

    public FitnessActivityService(FitnessActivityRepository fitnessActivityRepository, FitnessActivityMapper fitnessActivityMapper) {
        this.fitnessActivityRepository = fitnessActivityRepository;
        this.fitnessActivityMapper = fitnessActivityMapper;
    }

    /**
     * Save a fitnessActivity.
     *
     * @param fitnessActivityDTO the entity to save
     * @return the persisted entity
     */
    public FitnessActivityDTO save(FitnessActivityDTO fitnessActivityDTO) {
        log.debug("Request to save FitnessActivity : {}", fitnessActivityDTO);
        FitnessActivity fitnessActivity = fitnessActivityMapper.toEntity(fitnessActivityDTO);
        fitnessActivity = fitnessActivityRepository.save(fitnessActivity);
        return fitnessActivityMapper.toDto(fitnessActivity);
    }

    /**
     * Get all the fitnessActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FitnessActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FitnessActivities");
        return fitnessActivityRepository.findAll(pageable)
            .map(fitnessActivityMapper::toDto);
    }


    /**
     * Get one fitnessActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FitnessActivityDTO> findOne(Long id) {
        log.debug("Request to get FitnessActivity : {}", id);
        return fitnessActivityRepository.findById(id)
            .map(fitnessActivityMapper::toDto);
    }

    /**
     * Delete the fitnessActivity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FitnessActivity : {}", id);
        fitnessActivityRepository.deleteById(id);
    }
}
