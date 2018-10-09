package com.aitp.dlife.service;

import com.aitp.dlife.domain.SystemTotalPoints;
import com.aitp.dlife.repository.SystemTotalPointsRepository;
import com.aitp.dlife.service.dto.SystemTotalPointsDTO;
import com.aitp.dlife.service.mapper.SystemTotalPointsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing SystemTotalPoints.
 */
@Service
@Transactional
public class SystemTotalPointsService {

    private final Logger log = LoggerFactory.getLogger(SystemTotalPointsService.class);

    private final SystemTotalPointsRepository systemTotalPointsRepository;

    private final SystemTotalPointsMapper systemTotalPointsMapper;

    public SystemTotalPointsService(SystemTotalPointsRepository systemTotalPointsRepository, SystemTotalPointsMapper systemTotalPointsMapper) {
        this.systemTotalPointsRepository = systemTotalPointsRepository;
        this.systemTotalPointsMapper = systemTotalPointsMapper;
    }

    /**
     * Save a systemTotalPoints.
     *
     * @param systemTotalPointsDTO the entity to save
     * @return the persisted entity
     */
    public SystemTotalPointsDTO save(SystemTotalPointsDTO systemTotalPointsDTO) {
        log.debug("Request to save SystemTotalPoints : {}", systemTotalPointsDTO);
        SystemTotalPoints systemTotalPoints = systemTotalPointsMapper.toEntity(systemTotalPointsDTO);
        systemTotalPoints = systemTotalPointsRepository.save(systemTotalPoints);
        return systemTotalPointsMapper.toDto(systemTotalPoints);
    }

    /**
     * Get all the systemTotalPoints.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemTotalPointsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemTotalPoints");
        return systemTotalPointsRepository.findAll(pageable)
            .map(systemTotalPointsMapper::toDto);
    }


    /**
     * Get one systemTotalPoints by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SystemTotalPointsDTO> findOne(Long id) {
        log.debug("Request to get SystemTotalPoints : {}", id);
        return systemTotalPointsRepository.findById(id)
            .map(systemTotalPointsMapper::toDto);
    }

    /**
     * Delete the systemTotalPoints by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SystemTotalPoints : {}", id);
        systemTotalPointsRepository.deleteById(id);
    }
}
