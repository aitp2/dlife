package com.aitp.dlife.service;

import com.aitp.dlife.domain.QuestionPic;
import com.aitp.dlife.repository.QuestionPicRepository;
import com.aitp.dlife.service.dto.QuestionPicDTO;
import com.aitp.dlife.service.mapper.QuestionPicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing QuestionPic.
 */
@Service
@Transactional
public class QuestionPicService {

    private final Logger log = LoggerFactory.getLogger(QuestionPicService.class);

    private final QuestionPicRepository questionPicRepository;

    private final QuestionPicMapper questionPicMapper;

    public QuestionPicService(QuestionPicRepository questionPicRepository, QuestionPicMapper questionPicMapper) {
        this.questionPicRepository = questionPicRepository;
        this.questionPicMapper = questionPicMapper;
    }

    /**
     * Save a questionPic.
     *
     * @param questionPicDTO the entity to save
     * @return the persisted entity
     */
    public QuestionPicDTO save(QuestionPicDTO questionPicDTO) {
        log.debug("Request to save QuestionPic : {}", questionPicDTO);
        QuestionPic questionPic = questionPicMapper.toEntity(questionPicDTO);
        questionPic = questionPicRepository.save(questionPic);
        return questionPicMapper.toDto(questionPic);
    }

    /**
     * Get all the questionPics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuestionPicDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionPics");
        return questionPicRepository.findAll(pageable)
            .map(questionPicMapper::toDto);
    }


    /**
     * Get one questionPic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<QuestionPicDTO> findOne(Long id) {
        log.debug("Request to get QuestionPic : {}", id);
        return questionPicRepository.findById(id)
            .map(questionPicMapper::toDto);
    }

    /**
     * Delete the questionPic by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete QuestionPic : {}", id);
        questionPicRepository.deleteById(id);
    }
}
