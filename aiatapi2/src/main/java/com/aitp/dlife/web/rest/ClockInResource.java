package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.ClockInService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.ClockInDTO;
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
 * REST controller for managing ClockIn.
 */
@RestController
@RequestMapping("/api")
public class ClockInResource {

    private final Logger log = LoggerFactory.getLogger(ClockInResource.class);

    private static final String ENTITY_NAME = "clockIn";

    private final ClockInService clockInService;

    public ClockInResource(ClockInService clockInService) {
        this.clockInService = clockInService;
    }

    /**
     * POST  /clock-ins : Create a new clockIn.
     *
     * @param clockInDTO the clockInDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clockInDTO, or with status 400 (Bad Request) if the clockIn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clock-ins")
    @Timed
    public ResponseEntity<ClockInDTO> createClockIn(@Valid @RequestBody ClockInDTO clockInDTO) throws URISyntaxException {
        log.debug("REST request to save ClockIn : {}", clockInDTO);
        if (clockInDTO.getId() != null) {
            throw new BadRequestAlertException("A new clockIn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClockInDTO result = clockInService.save(clockInDTO);
        return ResponseEntity.created(new URI("/api/clock-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clock-ins : Updates an existing clockIn.
     *
     * @param clockInDTO the clockInDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clockInDTO,
     * or with status 400 (Bad Request) if the clockInDTO is not valid,
     * or with status 500 (Internal Server Error) if the clockInDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clock-ins")
    @Timed
    public ResponseEntity<ClockInDTO> updateClockIn(@Valid @RequestBody ClockInDTO clockInDTO) throws URISyntaxException {
        log.debug("REST request to update ClockIn : {}", clockInDTO);
        if (clockInDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClockInDTO result = clockInService.save(clockInDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clockInDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clock-ins : get all the clockIns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clockIns in body
     */
    @GetMapping("/clock-ins")
    @Timed
    public ResponseEntity<List<ClockInDTO>> getAllClockIns(Pageable pageable) {
        log.debug("REST request to get a page of ClockIns");
        Page<ClockInDTO> page = clockInService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clock-ins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clock-ins/:id : get the "id" clockIn.
     *
     * @param id the id of the clockInDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clockInDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clock-ins/{id}")
    @Timed
    public ResponseEntity<ClockInDTO> getClockIn(@PathVariable Long id) {
        log.debug("REST request to get ClockIn : {}", id);
        Optional<ClockInDTO> clockInDTO = clockInService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clockInDTO);
    }

    /**
     * DELETE  /clock-ins/:id : delete the "id" clockIn.
     *
     * @param id the id of the clockInDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clock-ins/{id}")
    @Timed
    public ResponseEntity<Void> deleteClockIn(@PathVariable Long id) {
        log.debug("REST request to delete ClockIn : {}", id);
        clockInService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
