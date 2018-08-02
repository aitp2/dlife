package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.PinFanActivityService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
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
 * REST controller for managing PinFanActivity.
 */
@RestController
@RequestMapping("/api")
public class PinFanActivityResource {

    private final Logger log = LoggerFactory.getLogger(PinFanActivityResource.class);

    private static final String ENTITY_NAME = "pinFanActivity";

    private final PinFanActivityService pinFanActivityService;

    public PinFanActivityResource(PinFanActivityService pinFanActivityService) {
        this.pinFanActivityService = pinFanActivityService;
    }

    /**
     * POST  /pin-fan-activities : Create a new pinFanActivity.
     *
     * @param pinFanActivityDTO the pinFanActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pinFanActivityDTO, or with status 400 (Bad Request) if the pinFanActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pin-fan-activities")
    @Timed
    public ResponseEntity<PinFanActivityDTO> createPinFanActivity(@Valid @RequestBody PinFanActivityDTO pinFanActivityDTO) throws URISyntaxException {
        log.debug("REST request to save PinFanActivity : {}", pinFanActivityDTO);
        if (pinFanActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new pinFanActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PinFanActivityDTO result = pinFanActivityService.save(pinFanActivityDTO);
        return ResponseEntity.created(new URI("/api/pin-fan-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pin-fan-activities : Updates an existing pinFanActivity.
     *
     * @param pinFanActivityDTO the pinFanActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinFanActivityDTO,
     * or with status 400 (Bad Request) if the pinFanActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the pinFanActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pin-fan-activities")
    @Timed
    public ResponseEntity<PinFanActivityDTO> updatePinFanActivity(@Valid @RequestBody PinFanActivityDTO pinFanActivityDTO) throws URISyntaxException {
        log.debug("REST request to update PinFanActivity : {}", pinFanActivityDTO);
        if (pinFanActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PinFanActivityDTO result = pinFanActivityService.save(pinFanActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pinFanActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pin-fan-activities : get all the pinFanActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pinFanActivities in body
     */
    @GetMapping("/pin-fan-activities")
    @Timed
    public ResponseEntity<List<PinFanActivityDTO>> getAllPinFanActivities(Pageable pageable) {
        log.debug("REST request to get a page of PinFanActivities");
        Page<PinFanActivityDTO> page = pinFanActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pin-fan-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pin-fan-activities/:id : get the "id" pinFanActivity.
     *
     * @param id the id of the pinFanActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pinFanActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pin-fan-activities/{id}")
    @Timed
    public ResponseEntity<PinFanActivityDTO> getPinFanActivity(@PathVariable Long id) {
        log.debug("REST request to get PinFanActivity : {}", id);
        Optional<PinFanActivityDTO> pinFanActivityDTO = pinFanActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pinFanActivityDTO);
    }

    /**
     * DELETE  /pin-fan-activities/:id : delete the "id" pinFanActivity.
     *
     * @param id the id of the pinFanActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pin-fan-activities/{id}")
    @Timed
    public ResponseEntity<Void> deletePinFanActivity(@PathVariable Long id) {
        log.debug("REST request to delete PinFanActivity : {}", id);
        pinFanActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
