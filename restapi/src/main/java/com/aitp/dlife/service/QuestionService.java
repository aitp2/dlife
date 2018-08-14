package com.aitp.dlife.service;

import com.aitp.dlife.domain.Question;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.repository.QuestionRepository;
import com.aitp.dlife.service.dto.CommentDTO;
import com.aitp.dlife.service.dto.QuestionDTO;
import com.aitp.dlife.service.dto.QuestionPicDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.service.mapper.QuestionMapper;
import com.aitp.dlife.web.rest.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Question.
 */
@Service
@Transactional
public class QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final QuestionPicService questionPicService;

    private final WechatUserService wechatUserService;

    private final CommentService commentService;

    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper, QuestionPicService questionPicService, WechatUserService wechatUserService, CommentService commentService) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.questionPicService = questionPicService;
        this.wechatUserService = wechatUserService;
        this.commentService = commentService;
    }

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    public QuestionDTO save(QuestionDTO questionDTO) {
        log.debug("Request to save Question : {}", questionDTO);
        Question question = questionMapper.toEntity(questionDTO);
        question = questionRepository.save(question);
        return questionMapper.toDto(question);
    }

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    public QuestionDTO createNewQuestion(QuestionDTO questionDTO) {
        log.debug("Request to create new Question : {}", questionDTO);

        // set the creation date
        if (StringUtils.isEmpty(questionDTO.getCreateTime())){
            questionDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
        }

        // set the default value
        questionDTO.setAnswerCount(Integer.valueOf(0));
        questionDTO.setReadingCount(Integer.valueOf(0));

        // set the user info
        if (StringUtils.isEmpty(questionDTO.getAvatar()) || StringUtils.isEmpty(questionDTO.getNickName())){
            WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(questionDTO.getWechatUserId()));
            if (wechatUserDTO != null){
                questionDTO.setAvatar(wechatUserDTO.getAvatar());
                questionDTO.setNickName(wechatUserDTO.getNickName());
            }
        }

        QuestionDTO result = save(questionDTO);

        // save the pics
        final Set<QuestionPicDTO> questionPicDTOs = new HashSet<>();
        if(!CollectionUtils.isEmpty(questionDTO.getQuestionPics())){
            for(QuestionPicDTO pic : questionDTO.getQuestionPics()){
                if(!StringUtils.isEmpty(pic.getCreateTime())){
                    pic.setCreateTime(DateUtil.getYMDDateString(new Date()));
                }
                pic.setQuestionId(result.getId());
                questionPicDTOs.add(questionPicService.save(pic));
            }
        }
        result.setQuestionPics(questionPicDTOs);


        return result;
    }

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<QuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        Page<QuestionDTO> result = questionRepository.findAll(pageable)
            .map(questionMapper::toDto);

        if(result!=null){
            Sort.Order order = new Sort.Order(Sort.Direction.DESC,"createTime");
            Sort sort = new Sort(order);
            PageRequest eventPageable = new PageRequest(0,1,sort);
            for(QuestionDTO questionDTO:result){
                List<CommentDTO> commentDTOList = commentService.findAllForOneObject(eventPageable, CommentChannel.FAQS.toString(),
                    questionDTO.getId()+"").stream().collect(Collectors.toList());
                questionDTO.setAnswers(commentDTOList);
            }
        }

        return result;
    }


    /**
     * Get one question by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<QuestionDTO> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id)
            .map(questionMapper::toDto);
    }

    /**
     * Delete the question by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }
}
