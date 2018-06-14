package com.aitp.dlife.pinfan.service;

import com.aitp.dlife.pinfan.domain.RatesPics;
import com.aitp.dlife.pinfan.repository.RatesPicsRepository;
import com.aitp.dlife.pinfan.repository.search.RatesPicsSearchRepository;
import com.aitp.dlife.pinfan.service.dto.RatesPicsDTO;
import com.aitp.dlife.pinfan.service.mapper.RatesPicsMapper;
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
 * Service Implementation for managing RatesPics.
 */
@Service
@Transactional
public class RatesPicsService {

    private final Logger log = LoggerFactory.getLogger(RatesPicsService.class);

    private final RatesPicsRepository ratesPicsRepository;

    private final RatesPicsMapper ratesPicsMapper;

    private final RatesPicsSearchRepository ratesPicsSearchRepository;

    public RatesPicsService(RatesPicsRepository ratesPicsRepository, RatesPicsMapper ratesPicsMapper, RatesPicsSearchRepository ratesPicsSearchRepository) {
        this.ratesPicsRepository = ratesPicsRepository;
        this.ratesPicsMapper = ratesPicsMapper;
        this.ratesPicsSearchRepository = ratesPicsSearchRepository;
    }

    /**
     * Save a ratesPics.
     *
     * @param ratesPicsDTO the entity to save
     * @return the persisted entity
     */
    public RatesPicsDTO save(RatesPicsDTO ratesPicsDTO) {
        log.debug("Request to save RatesPics : {}", ratesPicsDTO);
        RatesPics ratesPics = ratesPicsMapper.toEntity(ratesPicsDTO);
        ratesPics = ratesPicsRepository.save(ratesPics);
        RatesPicsDTO result = ratesPicsMapper.toDto(ratesPics);
        ratesPicsSearchRepository.save(ratesPics);
        return result;
    }

    /**
     * Get all the ratesPics.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RatesPicsDTO> findAll() {
        log.debug("Request to get all RatesPics");
        return ratesPicsRepository.findAll().stream()
            .map(ratesPicsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ratesPics by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RatesPicsDTO findOne(Long id) {
        log.debug("Request to get RatesPics : {}", id);
        RatesPics ratesPics = ratesPicsRepository.findOne(id);
        return ratesPicsMapper.toDto(ratesPics);
    }

    /**
     * Delete the ratesPics by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RatesPics : {}", id);
        ratesPicsRepository.delete(id);
        ratesPicsSearchRepository.delete(id);
    }

    /**
     * Search for the ratesPics corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RatesPicsDTO> search(String query) {
        log.debug("Request to search RatesPics for query {}", query);
        return StreamSupport
            .stream(ratesPicsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ratesPicsMapper::toDto)
            .collect(Collectors.toList());
    }
}
