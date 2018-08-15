package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.domain.ThumbsUp;
import com.aitp.dlife.service.CommentService;
import com.aitp.dlife.service.ThumbsUpService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.CommentDTO;
import com.aitp.dlife.service.dto.QueryDTO;
import com.aitp.dlife.service.dto.ThumbsUpDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ThumbsUp.
 */
@RestController
@RequestMapping("/api")
public class ThumbsUpResource {

    private final Logger log = LoggerFactory.getLogger(ThumbsUpResource.class);

    private static final String ENTITY_NAME = "thumbsUp";

    private final ThumbsUpService thumbsUpService;

    private final CommentService commentService;


    public ThumbsUpResource(ThumbsUpService thumbsUpService,CommentService commentService) {
        this.thumbsUpService = thumbsUpService;
        this.commentService = commentService;
    }

    /**
     * POST  /thumbs-ups : Create a new thumbsUp.
     *
     * @param thumbsUpDTO the thumbsUpDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thumbsUpDTO, or with status 400 (Bad Request) if the thumbsUp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value="点赞" ,notes ="根据用户信息通道，数据进行点赞.", produces="application/json")
    
    @PostMapping("/thumbs-ups")
    @Timed
    public ResponseEntity<ThumbsUpDTO> createThumbsUp(@Valid @RequestBody ThumbsUpDTO thumbsUpDTO) throws URISyntaxException {
        log.debug("REST request to save ThumbsUp : {}", thumbsUpDTO);
        if (thumbsUpDTO.getId() != null) {
            throw new BadRequestAlertException("A new thumbsUp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentDTO commentDTO = commentService.findOne(thumbsUpDTO.getObjectId());
        Integer thumbsUp = commentDTO.getRating1()==null?0:commentDTO.getRating1();
        thumbsUp++;
        commentDTO.setRating1(thumbsUp);
        thumbsUpDTO.setKeyName_1(commentDTO.getObjectId().toString());
        ThumbsUpDTO result = thumbsUpService.save(thumbsUpDTO);
        commentService.save(commentDTO);
        return ResponseEntity.created(new URI("/api/thumbs-ups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /thumbs-ups : Updates an existing thumbsUp.
     *
     * @param thumbsUpDTO the thumbsUpDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thumbsUpDTO,
     * or with status 400 (Bad Request) if the thumbsUpDTO is not valid,
     * or with status 500 (Internal Server Error) if the thumbsUpDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/thumbs-ups")
    @Timed
    public ResponseEntity<ThumbsUpDTO> updateThumbsUp(@Valid @RequestBody ThumbsUpDTO thumbsUpDTO) throws URISyntaxException {
        log.debug("REST request to update ThumbsUp : {}", thumbsUpDTO);
        if (thumbsUpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThumbsUpDTO result = thumbsUpService.save(thumbsUpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thumbsUpDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /thumbs-ups : get all the thumbsUps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of thumbsUps in body
     */
    @GetMapping("/thumbs-ups")
    @Timed
    public ResponseEntity<List<ThumbsUpDTO>> getAllThumbsUps(Pageable pageable,Specification<ThumbsUp> spec) {
        log.debug("REST request to get a page of ThumbsUps");
        Page<ThumbsUpDTO> page = thumbsUpService.findAll(pageable,spec);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/thumbs-ups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /thumbs-ups/:id : get the "id" thumbsUp.
     *
     * @param id the id of the thumbsUpDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thumbsUpDTO, or with status 404 (Not Found)
     */
    @GetMapping("/thumbs-ups/{id}")
    @Timed
    public ResponseEntity<ThumbsUpDTO> getThumbsUp(@PathVariable Long id) {
        log.debug("REST request to get ThumbsUp : {}", id);
        Optional<ThumbsUpDTO> thumbsUpDTO = thumbsUpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thumbsUpDTO);
    }

    /**
     * DELETE  /thumbs-ups/:id : delete the "id" thumbsUp.
     *
     * @param id the id of the thumbsUpDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(value="取消點贊",notes="根据点赞ID进行删除")
    @ApiParam()
    @DeleteMapping("/thumbs-ups/{id}")
    @Timed
    public ResponseEntity<String> deleteThumbsUp(@PathVariable @ApiParam(value="点赞ID", required=true) Long id) {
        log.debug("REST request to delete ThumbsUp : {}", id);
        ThumbsUpDTO thumbsUpDTO = thumbsUpService.findOne(id).get();
        CommentDTO commentDTO = commentService.findOne(thumbsUpDTO.getObjectId());
        Integer thumbsUp = commentDTO.getRating1()==null?0:commentDTO.getRating1();
        thumbsUp--;
        commentDTO.setRating1(thumbsUp);
        thumbsUpService.delete(id);
        commentService.save(commentDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).body("ok");
    }
}
