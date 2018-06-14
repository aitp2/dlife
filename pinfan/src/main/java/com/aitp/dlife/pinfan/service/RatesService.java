package com.aitp.dlife.pinfan.service;

import com.aitp.dlife.pinfan.domain.Rates;
import com.aitp.dlife.pinfan.repository.RatesRepository;
import com.aitp.dlife.pinfan.repository.search.RatesSearchRepository;
import com.aitp.dlife.pinfan.service.dto.RatesDTO;
import com.aitp.dlife.pinfan.service.mapper.RatesMapper;
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
 * Service Implementation for managing Rates.
 */
@Service
@Transactional
public class RatesService {

    private final Logger log = LoggerFactory.getLogger(RatesService.class);

    private final RatesRepository ratesRepository;

    private final RatesMapper ratesMapper;

    private final RatesSearchRepository ratesSearchRepository;

    public RatesService(RatesRepository ratesRepository, RatesMapper ratesMapper, RatesSearchRepository ratesSearchRepository) {
        this.ratesRepository = ratesRepository;
        this.ratesMapper = ratesMapper;
        this.ratesSearchRepository = ratesSearchRepository;
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
        RatesDTO result = ratesMapper.toDto(rates);
        ratesSearchRepository.save(rates);
        return result;
    }

    /**
     * Get all the rates.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RatesDTO> findAll() {
        log.debug("Request to get all Rates");
        return ratesRepository.findAll().stream()
            .map(ratesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
        ratesSearchRepository.delete(id);
    }

    /**
     * Search for the rates corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RatesDTO> search(String query) {
        log.debug("Request to search Rates for query {}", query);
        return StreamSupport
            .stream(ratesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ratesMapper::toDto)
            .collect(Collectors.toList());
    }
}
