package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.CommentPicService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.CommentPicDTO;
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
 * REST controller for managing CommentPic.
 */
@RestController
@RequestMapping("/api")
public class CommentPicResource {

    private final Logger log = LoggerFactory.getLogger(CommentPicResource.class);

    private static final String ENTITY_NAME = "commentPic";

    private final CommentPicService commentPicService;

    public CommentPicResource(CommentPicService commentPicService) {
        this.commentPicService = commentPicService;
    }

    /**
     * POST  /comment-pics : Create a new commentPic.
     *
     * @param commentPicDTO the commentPicDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentPicDTO, or with status 400 (Bad Request) if the commentPic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comment-pics")
    @Timed
    public ResponseEntity<CommentPicDTO> createCommentPic(@Valid @RequestBody CommentPicDTO commentPicDTO) throws URISyntaxException {
        log.debug("REST request to save CommentPic : {}", commentPicDTO);
        if (commentPicDTO.getId() != null) {
            throw new BadRequestAlertException("A new commentPic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentPicDTO result = commentPicService.save(commentPicDTO);
        return ResponseEntity.created(new URI("/api/comment-pics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comment-pics : Updates an existing commentPic.
     *
     * @param commentPicDTO the commentPicDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentPicDTO,
     * or with status 400 (Bad Request) if the commentPicDTO is not valid,
     * or with status 500 (Internal Server Error) if the commentPicDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comment-pics")
    @Timed
    public ResponseEntity<CommentPicDTO> updateCommentPic(@Valid @RequestBody CommentPicDTO commentPicDTO) throws URISyntaxException {
        log.debug("REST request to update CommentPic : {}", commentPicDTO);
        if (commentPicDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommentPicDTO result = commentPicService.save(commentPicDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentPicDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comment-pics : get all the commentPics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commentPics in body
     */
    @GetMapping("/comment-pics")
    @Timed
    public ResponseEntity<List<CommentPicDTO>> getAllCommentPics(Pageable pageable) {
        log.debug("REST request to get a page of CommentPics");
        Page<CommentPicDTO> page = commentPicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comment-pics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comment-pics/:id : get the "id" commentPic.
     *
     * @param id the id of the commentPicDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentPicDTO, or with status 404 (Not Found)
     */
    @GetMapping("/comment-pics/{id}")
    @Timed
    public ResponseEntity<CommentPicDTO> getCommentPic(@PathVariable Long id) {
        log.debug("REST request to get CommentPic : {}", id);
        Optional<CommentPicDTO> commentPicDTO = commentPicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentPicDTO);
    }

    /**
     * DELETE  /comment-pics/:id : delete the "id" commentPic.
     *
     * @param id the id of the commentPicDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comment-pics/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommentPic(@PathVariable Long id) {
        log.debug("REST request to delete CommentPic : {}", id);
        commentPicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
