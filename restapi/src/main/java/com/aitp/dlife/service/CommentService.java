package com.aitp.dlife.service;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.domain.PinFanActivity;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.repository.CommentRepository;
import com.aitp.dlife.repository.FitnessActivityRepository;
import com.aitp.dlife.repository.PinFanActivityRepository;
import com.aitp.dlife.service.dto.CommentDTO;
import com.aitp.dlife.service.mapper.CommentMapper;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Comment.
 */
@Service
@Transactional
public class CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final FitnessActivityRepository fitnessActivityRepository;

    private final PinFanActivityRepository pinFanActivityRepository;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, FitnessActivityRepository fitnessActivityRepository, PinFanActivityRepository pinFanActivityRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;

        this.fitnessActivityRepository = fitnessActivityRepository;
        this.pinFanActivityRepository = pinFanActivityRepository;
    }

    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save
     * @return the persisted entity
     */
    public CommentDTO save(CommentDTO commentDTO) {
        log.debug("Request to save Comment : {}", commentDTO);
        if(CommentChannel.FIT.equals(commentDTO.getChannel()))
        {
            FitnessActivity fitnessActivity = fitnessActivityRepository.findOne(commentDTO.getObjectId());
            fitnessActivity.setCommentCount(fitnessActivity.getCommentCount() == null? 1 : fitnessActivity.getCommentCount() +1);
        }
        if(CommentChannel.PIN.equals(commentDTO.getChannel()))
        {
            PinFanActivity pinFanActivity = pinFanActivityRepository.findOne(commentDTO.getObjectId());
            pinFanActivity.setCommentCount(pinFanActivity.getCommentCount() == null? 1 : pinFanActivity.getCommentCount() + 1);
        }
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentRepository.findAll(pageable)
            .map(commentMapper::toDto);
    }

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommentDTO> findAllForOneObject(Pageable pageable,String channel,String objectId) {
        log.debug("Request to get all Comments");

        for(CommentChannel channel1:CommentChannel.values()){
            if(channel.toUpperCase().equals(channel1.toString())){
                return commentRepository.findAllForOneObject(pageable,channel1,Long.valueOf(objectId))
                    .map(commentMapper::toDto);
            }
        }
        throw new BadRequestAlertException("There is no comment for current request","Comment","暂无评论");
    }

    /**
     * Get one comment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CommentDTO findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        Comment comment = commentRepository.findOne(id);
        return commentMapper.toDto(comment);
    }

    /**
     * Delete the comment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.delete(id);
    }
}
