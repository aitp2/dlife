package com.aitp.dlife.service;

import com.aitp.dlife.domain.ThumbsUp;
import com.aitp.dlife.domain.WechatUser;
import com.aitp.dlife.repository.ClockInRepository;
import com.aitp.dlife.repository.CommentRepository;
import com.aitp.dlife.repository.ThumbsUpRepository;
import com.aitp.dlife.repository.WechatUserRepository;
import com.aitp.dlife.repository.specification.ThumbsUpSpecification;
import com.aitp.dlife.service.dto.ThumbsUpDTO;
import com.aitp.dlife.service.mapper.ThumbsUpMapper;
import com.aitp.dlife.web.rest.errors.UniquenessConflictException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    private  final CommentRepository commentRepository;

    private final ClockInRepository clockInRepository;

    private final WechatUserRepository wechatUserRepository;

    private final ThumbsUpMapper thumbsUpMapper;

    public ThumbsUpService(ThumbsUpRepository thumbsUpRepository, ThumbsUpMapper thumbsUpMapper, ClockInRepository clockInRepository, CommentRepository commentRepository, WechatUserRepository wechatUserRepository) {
        this.thumbsUpRepository = thumbsUpRepository;
        this.thumbsUpMapper = thumbsUpMapper;
        this.commentRepository = commentRepository;
        this.clockInRepository = clockInRepository;
        this.wechatUserRepository = wechatUserRepository;
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
    	try {
    	        thumbsUp = thumbsUpRepository.save(thumbsUp);
		} catch (DataIntegrityViolationException e) {
			throw new UniquenessConflictException();
		}
        return thumbsUpMapper.toDto(thumbsUp);
    }

    /**
     * Get all the thumbsUps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ThumbsUpDTO> findAll(Pageable pageable,Specification<ThumbsUp> spec) {
        log.debug("Request to get all ThumbsUps");
        return thumbsUpRepository.findAll(spec,pageable)
            .map(thumbsUpMapper::toDto);
    }


    /**
     * Get all the thumbsUps.
     *
     * @param spec the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ThumbsUpDTO> findAll(ThumbsUpSpecification spec) {
        log.debug("Request to get all ThumbsUps");
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return thumbsUpRepository.findAll(spec,sort).stream()
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

    /**
     * update the user's thumbs up count
     *
     * @param wechatUserId
     * @param add
     */
    public void updateUserThumbsUpCount(String wechatUserId, boolean add){
        log.debug("Request to update the Thumbs Up count User id: {}", wechatUserId);
        WechatUser wechatUser = wechatUserRepository.getOne(Long.valueOf(wechatUserId));
        Integer newCount;
        if (add){
            newCount = (wechatUser.getThumbsUpCount() == null ? Integer.valueOf(1) : Integer.valueOf(wechatUser.getThumbsUpCount().intValue() + 1));
        }else{
            newCount = (wechatUser.getThumbsUpCount() == null ? Integer.valueOf(0) : Integer.valueOf(wechatUser.getThumbsUpCount().intValue() - 1));
        }

        if (newCount.intValue() < 0){
            newCount = Integer.valueOf(0);
        }

        wechatUser.setThumbsUpCount(newCount);
        wechatUserRepository.save(wechatUser);
    }
}
