package com.aitp.dlife.service;

import com.aitp.dlife.domain.Pics;
import com.aitp.dlife.repository.PicsRepository;
import com.aitp.dlife.service.dto.PicsDTO;
import com.aitp.dlife.service.mapper.PicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Pics.
 */
@Service
@Transactional
public class PicsService {

    private final Logger log = LoggerFactory.getLogger(PicsService.class);

    private final PicsRepository picsRepository;

    private final PicsMapper picsMapper;

    public PicsService(PicsRepository picsRepository, PicsMapper picsMapper) {
        this.picsRepository = picsRepository;
        this.picsMapper = picsMapper;
    }

    /**
     * Save a pics.
     *
     * @param picsDTO the entity to save
     * @return the persisted entity
     */
    public PicsDTO save(PicsDTO picsDTO) {
        log.debug("Request to save Pics : {}", picsDTO);
        Pics pics = picsMapper.toEntity(picsDTO);
        pics = picsRepository.save(pics);
        return picsMapper.toDto(pics);
    }

    /**
     * Get all the pics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pics");
        return picsRepository.findAll(pageable)
            .map(picsMapper::toDto);
    }


    /**
     * Get one pics by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PicsDTO> findOne(Long id) {
        log.debug("Request to get Pics : {}", id);
        return picsRepository.findById(id)
            .map(picsMapper::toDto);
    }

    /**
     * Delete the pics by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pics : {}", id);
        picsRepository.deleteById(id);
    }
}
