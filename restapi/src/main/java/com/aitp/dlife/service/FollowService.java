package com.aitp.dlife.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.Follow;
import com.aitp.dlife.repository.FollowRepository;
import com.aitp.dlife.service.dto.FollowDTO;
import com.aitp.dlife.service.mapper.FollowMapper;


/**
 * Service Implementation for managing Follow.
 */
@Service
@Transactional
public class FollowService {

    private final Logger log = LoggerFactory.getLogger(FollowService.class);

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    public FollowService(FollowRepository followRepository, FollowMapper followMapper) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
    }

    /**
     * Save a follow.
     *
     * @param followDTO the entity to save
     * @return the persisted entity
     */
    public FollowDTO save(FollowDTO followDTO) {
        log.debug("Request to save Follow : {}", followDTO);
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        return followMapper.toDto(follow);
    }

    /**
     * Get all the follows.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FollowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Follows");
        return followRepository.findAll(pageable)
            .map(followMapper::toDto);
    }

    /**
     * Get one follow by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FollowDTO findOne(Long id) {
        log.debug("Request to get Follow : {}", id);
        Follow follow = followRepository.findOne(id);
        return followMapper.toDto(follow);
    }

    /**
     * Delete the follow by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Follow : {}", id);
        followRepository.delete(id);
    }
    
    /**
     * findAllByFollowUserId
     * @param pageable
     * @param wechatUserId
     * @return
     */
    public Page<FollowDTO> findAllByFollowUserId(Pageable pageable, String wechatUserId) {
    	return followRepository.findAllByFollowUserId(pageable,wechatUserId).map(followMapper::toDto);
		
	}
    
    /**
     * findAllByFollowedUserId
     * @param pageable
     * @param wechatUserId
     * @return
     */
    public Page<FollowDTO> findAllByFollowedUserId(Pageable pageable, String wechatUserId) {
    	return followRepository.findAllByFollowedUserId(pageable,wechatUserId).map(followMapper::toDto);
	}
}
