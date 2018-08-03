package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.FitnessActivityService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
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
 * REST controller for managing FitnessActivity.
 */
@RestController
@RequestMapping("/api")
public class FitnessActivityResource {

    private final Logger log = LoggerFactory.getLogger(FitnessActivityResource.class);

    private static final String ENTITY_NAME = "fitnessActivity";

    private final FitnessActivityService fitnessActivityService;

    public FitnessActivityResource(FitnessActivityService fitnessActivityService) {
        this.fitnessActivityService = fitnessActivityService;
    }

    /**
     * POST  /fitness-activities : Create a new fitnessActivity.
     *
     * @param fitnessActivityDTO the fitnessActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fitnessActivityDTO, or with status 400 (Bad Request) if the fitnessActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fitness-activities")
    @Timed
    public ResponseEntity<FitnessActivityDTO> createFitnessActivity(@Valid @RequestBody FitnessActivityDTO fitnessActivityDTO) throws URISyntaxException {
        log.debug("REST request to save FitnessActivity : {}", fitnessActivityDTO);
        if (fitnessActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new fitnessActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FitnessActivityDTO result = fitnessActivityService.save(fitnessActivityDTO);
        return ResponseEntity.created(new URI("/api/fitness-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fitness-activities : Updates an existing fitnessActivity.
     *
     * @param fitnessActivityDTO the fitnessActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fitnessActivityDTO,
     * or with status 400 (Bad Request) if the fitnessActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the fitnessActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fitness-activities")
    @Timed
    public ResponseEntity<FitnessActivityDTO> updateFitnessActivity(@Valid @RequestBody FitnessActivityDTO fitnessActivityDTO) throws URISyntaxException {
        log.debug("REST request to update FitnessActivity : {}", fitnessActivityDTO);
        if (fitnessActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FitnessActivityDTO result = fitnessActivityService.save(fitnessActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fitnessActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fitness-activities : get all the fitnessActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fitnessActivities in body
     */
    @GetMapping("/fitness-activities")
    @Timed
    public ResponseEntity<List<FitnessActivityDTO>> getAllFitnessActivities(Pageable pageable) {
        log.debug("REST request to get a page of FitnessActivities");
        Page<FitnessActivityDTO> page = fitnessActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fitness-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fitness-activities/:id : get the "id" fitnessActivity.
     *
     * @param id the id of the fitnessActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fitnessActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fitness-activities/{id}")
    @Timed
    public ResponseEntity<FitnessActivityDTO> getFitnessActivity(@PathVariable Long id) {
        log.debug("REST request to get FitnessActivity : {}", id);
        Optional<FitnessActivityDTO> fitnessActivityDTO = fitnessActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fitnessActivityDTO);
    }

    /**
     * DELETE  /fitness-activities/:id : delete the "id" fitnessActivity.
     *
     * @param id the id of the fitnessActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fitness-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFitnessActivity(@PathVariable Long id) {
        log.debug("REST request to delete FitnessActivity : {}", id);
        fitnessActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
