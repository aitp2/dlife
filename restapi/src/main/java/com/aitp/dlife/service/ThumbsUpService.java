package com.aitp.dlife.service;

import com.aitp.dlife.domain.ThumbsUp;
import com.aitp.dlife.repository.ThumbsUpRepository;
import com.aitp.dlife.repository.specification.CommentSpecification;
import com.aitp.dlife.repository.specification.ThumbsUpSpecification;
import com.aitp.dlife.service.dto.QueryDTO;
import com.aitp.dlife.service.dto.ThumbsUpDTO;
import com.aitp.dlife.service.mapper.ThumbsUpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing ThumbsUp.
 */
@Service
@Transactional
public class ThumbsUpService {

    private final Logger log = LoggerFactory.getLogger(ThumbsUpService.class);

    private final ThumbsUpRepository thumbsUpRepository;

    private final ThumbsUpMapper thumbsUpMapper;

    public ThumbsUpService(ThumbsUpRepository thumbsUpRepository, ThumbsUpMapper thumbsUpMapper) {
        this.thumbsUpRepository = thumbsUpRepository;
        this.thumbsUpMapper = thumbsUpMapper;
    }

    /**
     * Save a thumbsUp.
     *
     * @param thumbsUpDTO the entity to save
     * @return the persisted entity
     */
    public ThumbsUpDTO save(ThumbsUpDTO thumbsUpDTO) {
        log.debug("Request to save ThumbsUp : {}", thumbsUpDTO);
        ThumbsUp thumbsUp = thumbsUpMapper.toEntity(thumbsUpDTO);
        thumbsUp = thumbsUpRepository.save(thumbsUp);
        return thumbsUpMapper.toDto(thumbsUp);
    }

    /**
     * Get all the thumbsUps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ThumbsUpDTO> findAll(Pageable pageable,List<QueryDTO> queryDTOs) {
        log.debug("Request to get all ThumbsUps");
        ThumbsUpSpecification spec = new ThumbsUpSpecification(queryDTOs);
        return thumbsUpRepository.findAll(spec,pageable)
            .map(thumbsUpMapper::toDto);
    }


    /**
     * Get all the thumbsUps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ThumbsUpDTO> findAll(List<QueryDTO> queryDTOs) {
        log.debug("Request to get all ThumbsUps");
        ThumbsUpSpecification spec = new ThumbsUpSpecification(queryDTOs);
        return thumbsUpRepository.findAll(spec).stream()
            .map(thumbsUpMapper::toDto).collect(Collectors.toList());
    }
    
    
    /**
     * Get one thumbsUp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ThumbsUpDTO> findOne(Long id) {
        log.debug("Request to get ThumbsUp : {}", id);
        return thumbsUpRepository.findById(id)
            .map(thumbsUpMapper::toDto);
    }

    /**
     * Delete the thumbsUp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ThumbsUp : {}", id);
        thumbsUpRepository.deleteById(id);
    }
}
