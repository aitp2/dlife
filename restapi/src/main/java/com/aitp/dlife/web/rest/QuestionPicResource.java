package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.QuestionPicService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.QuestionPicDTO;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing QuestionPic.
 */
@RestController
@RequestMapping("/api")
public class QuestionPicResource {

    private final Logger log = LoggerFactory.getLogger(QuestionPicResource.class);

    private static final String ENTITY_NAME = "questionPic";

    private final QuestionPicService questionPicService;

    public QuestionPicResource(QuestionPicService questionPicService) {
        this.questionPicService = questionPicService;
    }

    /**
     * POST  /question-pics : Create a new questionPic.
     *
     * @param questionPicDTO the questionPicDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionPicDTO, or with status 400 (Bad Request) if the questionPic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-pics")
    @Timed
    public ResponseEntity<QuestionPicDTO> createQuestionPic(@Valid @RequestBody QuestionPicDTO questionPicDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionPic : {}", questionPicDTO);
        if (questionPicDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionPic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionPicDTO result = questionPicService.save(questionPicDTO);
        return ResponseEntity.created(new URI("/api/question-pics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-pics : Updates an existing questionPic.
     *
     * @param questionPicDTO the questionPicDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionPicDTO,
     * or with status 400 (Bad Request) if the questionPicDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionPicDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-pics")
    @Timed
    public ResponseEntity<QuestionPicDTO> updateQuestionPic(@Valid @RequestBody QuestionPicDTO questionPicDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionPic : {}", questionPicDTO);
        if (questionPicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionPicDTO result = questionPicService.save(questionPicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionPicDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-pics : get all the questionPics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questionPics in body
     */
    @GetMapping("/question-pics")
    @Timed
    public ResponseEntity<List<QuestionPicDTO>> getAllQuestionPics(Pageable pageable) {
        log.debug("REST request to get a page of QuestionPics");
        Page<QuestionPicDTO> page = questionPicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/question-pics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /question-pics/:id : get the "id" questionPic.
     *
     * @param id the id of the questionPicDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionPicDTO, or with status 404 (Not Found)
     */
    @GetMapping("/question-pics/{id}")
    @Timed
    public ResponseEntity<QuestionPicDTO> getQuestionPic(@PathVariable Long id) {
        log.debug("REST request to get QuestionPic : {}", id);
        Optional<QuestionPicDTO> questionPicDTO = questionPicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionPicDTO);
    }

    /**
     * DELETE  /question-pics/:id : delete the "id" questionPic.
     *
     * @param id the id of the questionPicDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-pics/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionPic(@PathVariable Long id) {
        log.debug("REST request to delete QuestionPic : {}", id);
        questionPicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
