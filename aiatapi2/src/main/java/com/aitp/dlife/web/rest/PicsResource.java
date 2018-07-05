package com.aitp.dlife.web.rest;

import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.PicsService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.PicsDTO;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pics.
 */
@RestController
@RequestMapping("/api")
public class PicsResource {

    private final Logger log = LoggerFactory.getLogger(PicsResource.class);

    private static final String ENTITY_NAME = "pics";

    private final PicsService picsService;

    public PicsResource(PicsService picsService) {
        this.picsService = picsService;
    }

    /**
     * POST  /pics : Create a new pics.
     *
     * @param picsDTO the picsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new picsDTO, or with status 400 (Bad Request) if the pics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pics")
    @Timed
    public ResponseEntity<PicsDTO> createPics(@Valid @RequestBody PicsDTO picsDTO) throws URISyntaxException {
        log.debug("REST request to save Pics : {}", picsDTO);
        if (picsDTO.getId() != null) {
            throw new BadRequestAlertException("A new pics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(StringUtils.isEmpty(picsDTO.getCreateTime())){
            picsDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
        }
        PicsDTO result = picsService.save(picsDTO);
        return ResponseEntity.created(new URI("/api/pics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pics : Updates an existing pics.
     *
     * @param picsDTO the picsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated picsDTO,
     * or with status 400 (Bad Request) if the picsDTO is not valid,
     * or with status 500 (Internal Server Error) if the picsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pics")
    @Timed
    public ResponseEntity<PicsDTO> updatePics(@Valid @RequestBody PicsDTO picsDTO) throws URISyntaxException {
        log.debug("REST request to update Pics : {}", picsDTO);
        if (picsDTO.getId() == null) {
            return createPics(picsDTO);
        }
        PicsDTO result = picsService.save(picsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, picsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pics : get all the pics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pics in body
     */
    @GetMapping("/pics")
    @Timed
    public ResponseEntity<List<PicsDTO>> getAllPics(Pageable pageable) {
        log.debug("REST request to get a page of Pics");
        Page<PicsDTO> page = picsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pics/:id : get the "id" pics.
     *
     * @param id the id of the picsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the picsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pics/{id}")
    @Timed
    public ResponseEntity<PicsDTO> getPics(@PathVariable Long id) {
        log.debug("REST request to get Pics : {}", id);
        PicsDTO picsDTO = picsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(picsDTO));
    }

    /**
     * DELETE  /pics/:id : delete the "id" pics.
     *
     * @param id the id of the picsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pics/{id}")
    @Timed
    public ResponseEntity<Void> deletePics(@PathVariable Long id) {
        log.debug("REST request to delete Pics : {}", id);
        picsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /pics : Create new pics.
     *
     * @param picsDTO the picsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new picsDTO, or with status 400 (Bad Request) if the pics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/multi-pics")
    @Timed
    public ResponseEntity<List<PicsDTO>> createPics(@Valid @RequestBody List<PicsDTO> picsDTOs) throws URISyntaxException {
    	List<PicsDTO> list = new ArrayList<>();
        for(PicsDTO picsDTO : picsDTOs){
        	log.debug("REST request to save Pics : {}", picsDTO);
            if (picsDTO.getId() != null) {
                throw new BadRequestAlertException("A new pics cannot already have an ID", ENTITY_NAME, "idexists");
            }
            if(StringUtils.isEmpty(picsDTO.getCreateTime())){
                picsDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
            }
            PicsDTO result = picsService.save(picsDTO);
            list.add(result);
        }
        return ResponseEntity.ok(list);
    }
}
