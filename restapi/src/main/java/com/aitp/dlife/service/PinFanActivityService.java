package com.aitp.dlife.service;

import com.aitp.dlife.domain.PinFanActivity;
import com.aitp.dlife.repository.PinFanActivityRepository;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.mapper.PinFanActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing PinFanActivity.
 */
@Service
@Transactional
public class PinFanActivityService {

    private final Logger log = LoggerFactory.getLogger(PinFanActivityService.class);

    private final PinFanActivityRepository pinFanActivityRepository;

    private final PinFanActivityMapper pinFanActivityMapper;

    public PinFanActivityService(PinFanActivityRepository pinFanActivityRepository, PinFanActivityMapper pinFanActivityMapper) {
        this.pinFanActivityRepository = pinFanActivityRepository;
        this.pinFanActivityMapper = pinFanActivityMapper;
    }

    /**
     * Save a pinFanActivity.
     *
     * @param pinFanActivityDTO the entity to save
     * @return the persisted entity
     */
    public PinFanActivityDTO save(PinFanActivityDTO pinFanActivityDTO) {
        log.debug("Request to save PinFanActivity : {}", pinFanActivityDTO);
        PinFanActivity pinFanActivity = pinFanActivityMapper.toEntity(pinFanActivityDTO);
        pinFanActivity = pinFanActivityRepository.save(pinFanActivity);
        return pinFanActivityMapper.toDto(pinFanActivity);
    }

    /**
     * Get all the pinFanActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PinFanActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PinFanActivities");
        return pinFanActivityRepository.findAll(pageable)
            .map(pinFanActivityMapper::toDto);
    }


    /**
     * Get one pinFanActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PinFanActivityDTO> findOne(Long id) {
        log.debug("Request to get PinFanActivity : {}", id);
        return pinFanActivityRepository.findById(id)
            .map(pinFanActivityMapper::toDto);
    }

    /**
     * Delete the pinFanActivity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PinFanActivity : {}", id);
        pinFanActivityRepository.deleteById(id);
    }
}
