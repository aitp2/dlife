package com.aitp.dlife.service;

import com.aitp.dlife.domain.Rates;
import com.aitp.dlife.repository.RatesRepository;
import com.aitp.dlife.service.dto.RatesDTO;
import com.aitp.dlife.service.mapper.RatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Rates.
 */
@Service
@Transactional
public class RatesService {

    private final Logger log = LoggerFactory.getLogger(RatesService.class);

    private final RatesRepository ratesRepository;

    private final RatesMapper ratesMapper;

    public RatesService(RatesRepository ratesRepository, RatesMapper ratesMapper) {
        this.ratesRepository = ratesRepository;
        this.ratesMapper = ratesMapper;
    }

    /**
     * Save a rates.
     *
     * @param ratesDTO the entity to save
     * @return the persisted entity
     */
    public RatesDTO save(RatesDTO ratesDTO) {
        log.debug("Request to save Rates : {}", ratesDTO);
        Rates rates = ratesMapper.toEntity(ratesDTO);
        rates = ratesRepository.save(rates);
        return ratesMapper.toDto(rates);
    }

    /**
     * Get all the rates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rates");
        return ratesRepository.findAll(pageable)
            .map(ratesMapper::toDto);
    }

    /**
     * Get one rates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RatesDTO findOne(Long id) {
        log.debug("Request to get Rates : {}", id);
        Rates rates = ratesRepository.findOne(id);
        return ratesMapper.toDto(rates);
    }

    /**
     * Delete the rates by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Rates : {}", id);
        ratesRepository.delete(id);
    }
}
