package com.aitp.dlife.service;

import com.aitp.dlife.domain.CommentPic;
import com.aitp.dlife.repository.CommentPicRepository;
import com.aitp.dlife.service.dto.CommentPicDTO;
import com.aitp.dlife.service.mapper.CommentPicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing CommentPic.
 */
@Service
@Transactional
public class CommentPicService {

    private final Logger log = LoggerFactory.getLogger(CommentPicService.class);

    private final CommentPicRepository commentPicRepository;

    private final CommentPicMapper commentPicMapper;

    public CommentPicService(CommentPicRepository commentPicRepository, CommentPicMapper commentPicMapper) {
        this.commentPicRepository = commentPicRepository;
        this.commentPicMapper = commentPicMapper;
    }

    /**
     * Save a commentPic.
     *
     * @param commentPicDTO the entity to save
     * @return the persisted entity
     */
    public CommentPicDTO save(CommentPicDTO commentPicDTO) {
        log.debug("Request to save CommentPic : {}", commentPicDTO);
        CommentPic commentPic = commentPicMapper.toEntity(commentPicDTO);
        commentPic = commentPicRepository.save(commentPic);
        return commentPicMapper.toDto(commentPic);
    }

    /**
     * Get all the commentPics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommentPicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommentPics");
        return commentPicRepository.findAll(pageable)
            .map(commentPicMapper::toDto);
    }


    /**
     * Get one commentPic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommentPicDTO> findOne(Long id) {
        log.debug("Request to get CommentPic : {}", id);
        return commentPicRepository.findById(id)
            .map(commentPicMapper::toDto);
    }

    /**
     * Delete the commentPic by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommentPic : {}", id);
        commentPicRepository.deleteById(id);
    }
}
