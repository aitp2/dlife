package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.QuestionService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.QuestionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Question.
 */
@Api(value = "小问答问题API", tags = "小问答问题API")
@RestController
@RequestMapping("/api")
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    private static final String ENTITY_NAME = "question";

    private final QuestionService questionService;

    public QuestionResource(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * POST  /questions : Create a new question.
     *
     * @param questionDTO the questionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionDTO, or with status 400 (Bad Request) if the question has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/questions")
    @ApiOperation(value = "创建问题", response = QuestionDTO.class, produces = "application/json")
    @Timed
    public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDTO) throws URISyntaxException {
        log.debug("REST request to save Question : {}", questionDTO);
        if (questionDTO.getId() != null) {
            throw new BadRequestAlertException("A new question cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (StringUtils.isEmpty(questionDTO.getWechatUserId())){
            throw new BadRequestAlertException("The wechat user id can not be empty", ENTITY_NAME, "userIdEmpty");
        }

        QuestionDTO result = questionService.createNewQuestion(questionDTO);
        return ResponseEntity.created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questions : Updates an existing question.
     *
     * @param questionDTO the questionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionDTO,
     * or with status 400 (Bad Request) if the questionDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questions")
    @ApiOperation(value = "更新问题", response = QuestionDTO.class, produces = "application/json")
    @Timed
    public ResponseEntity<QuestionDTO> updateQuestion(@Valid @RequestBody QuestionDTO questionDTO) throws URISyntaxException {
        log.debug("REST request to update Question : {}", questionDTO);
        if (questionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionDTO result = questionService.save(questionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questions : get all the questions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questions in body
     */
    @GetMapping("/questions")
    @ApiOperation(value = "查询问题列表", response = QuestionDTO.class, produces = "application/json")
    @Timed
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(Pageable pageable) {
        log.debug("REST request to get a page of Questions");
        Page<QuestionDTO> page = questionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/questions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /questions/:id : get the "id" question.
     *
     * @param id the id of the questionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/questions/{id}")
    @ApiOperation(value = "问题详情", response = QuestionDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "id", value = "问题的ID", required = true) })
    @Timed
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long id) {
        log.debug("REST request to get Question : {}", id);
        Optional<QuestionDTO> questionDTO = questionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionDTO);
    }

    /**
     * DELETE  /questions/:id : delete the "id" question.
     *
     * @param id the id of the questionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questions/{id}")
    @ApiOperation(value = "删除问题", response = Void.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "id", value = "问题的ID", required = true) })
    @Timed
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        log.debug("REST request to delete Question : {}", id);
        questionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * PUT  /questions : Updates an existing question's reading count.
     *
     * @param id the question id to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionDTO,
     * or with status 400 (Bad Request) if the questionDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questions/readingCount")
    @ApiOperation(value = "更新问题浏览量", response = QuestionDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "param", dataType = "String", defaultValue = "", name = "id", value = "问题的ID", required = true) })
    @Timed
    public ResponseEntity<QuestionDTO> updateQuestionReadingCount(Long id) throws URISyntaxException {
        log.debug("REST request to update Question : {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<QuestionDTO> oldResultOptional = questionService.findOne(id);
        if (!oldResultOptional.isPresent()){
            throw new BadRequestAlertException("Not fount", ENTITY_NAME, "notfound");
        }

        QuestionDTO oldResult = oldResultOptional.get();

        oldResult.setReadingCount(
            oldResult.getReadingCount() != null ? oldResult.getReadingCount() + 1 : 1);

        QuestionDTO result = questionService.save(oldResult);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
