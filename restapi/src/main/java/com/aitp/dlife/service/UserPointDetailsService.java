package com.aitp.dlife.service;

import com.aitp.dlife.domain.UserPointDetails;
import com.aitp.dlife.repository.UserPointDetailsRepository;
import com.aitp.dlife.service.dto.UserPointDetailsDTO;
import com.aitp.dlife.service.mapper.UserPointDetailsMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing UserPointDetails.
 */
@Service
@Transactional
public class UserPointDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserPointDetailsService.class);

    private final UserPointDetailsRepository userPointDetailsRepository;

    private final UserPointDetailsMapper userPointDetailsMapper;

    public UserPointDetailsService(UserPointDetailsRepository userPointDetailsRepository, UserPointDetailsMapper userPointDetailsMapper) {
        this.userPointDetailsRepository = userPointDetailsRepository;
        this.userPointDetailsMapper = userPointDetailsMapper;
    }

    /**
     * Save a userPointDetails.
     *
     * @param userPointDetailsDTO the entity to save
     * @return the persisted entity
     */
    public UserPointDetailsDTO save(UserPointDetailsDTO userPointDetailsDTO) {
        log.debug("Request to save UserPointDetails : {}", userPointDetailsDTO);
        UserPointDetails userPointDetails = userPointDetailsMapper.toEntity(userPointDetailsDTO);
        userPointDetails = userPointDetailsRepository.save(userPointDetails);
        return userPointDetailsMapper.toDto(userPointDetails);
    }

    /**
     * Get all the userPointDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserPointDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPointDetails");
        return userPointDetailsRepository.findAll(pageable)
            .map(userPointDetailsMapper::toDto);
    }


    /**
     * Get one userPointDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserPointDetailsDTO> findOne(Long id) {
        log.debug("Request to get UserPointDetails : {}", id);
        return userPointDetailsRepository.findById(id)
            .map(userPointDetailsMapper::toDto);
    }

    /**
     * Delete the userPointDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPointDetails : {}", id);
        userPointDetailsRepository.deleteById(id);
    }

	public Page<UserPointDetailsDTO> findAllByUserid(String userid, Pageable pageable) {
        log.debug("Request to get all UserPointDetails");
        return userPointDetailsRepository.findAllByUserid(userid, pageable)
            .map(userPointDetailsMapper::toDto);
	}
}
