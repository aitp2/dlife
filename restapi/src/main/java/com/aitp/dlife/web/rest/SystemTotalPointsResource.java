package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.SystemTotalPointsService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.SystemTotalPointsDTO;
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
 * REST controller for managing SystemTotalPoints.
 */
@RestController
@RequestMapping("/api")
public class SystemTotalPointsResource {

    private final Logger log = LoggerFactory.getLogger(SystemTotalPointsResource.class);

    private static final String ENTITY_NAME = "systemTotalPoints";

    private final SystemTotalPointsService systemTotalPointsService;

    public SystemTotalPointsResource(SystemTotalPointsService systemTotalPointsService) {
        this.systemTotalPointsService = systemTotalPointsService;
    }

    /**
     * POST  /system-total-points : Create a new systemTotalPoints.
     *
     * @param systemTotalPointsDTO the systemTotalPointsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemTotalPointsDTO, or with status 400 (Bad Request) if the systemTotalPoints has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-total-points")
    @Timed
    public ResponseEntity<SystemTotalPointsDTO> createSystemTotalPoints(@Valid @RequestBody SystemTotalPointsDTO systemTotalPointsDTO) throws URISyntaxException {
        log.debug("REST request to save SystemTotalPoints : {}", systemTotalPointsDTO);
        if (systemTotalPointsDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemTotalPoints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemTotalPointsDTO result = systemTotalPointsService.save(systemTotalPointsDTO);
        return ResponseEntity.created(new URI("/api/system-total-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-total-points : Updates an existing systemTotalPoints.
     *
     * @param systemTotalPointsDTO the systemTotalPointsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemTotalPointsDTO,
     * or with status 400 (Bad Request) if the systemTotalPointsDTO is not valid,
     * or with status 500 (Internal Server Error) if the systemTotalPointsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-total-points")
    @Timed
    public ResponseEntity<SystemTotalPointsDTO> updateSystemTotalPoints(@Valid @RequestBody SystemTotalPointsDTO systemTotalPointsDTO) throws URISyntaxException {
        log.debug("REST request to update SystemTotalPoints : {}", systemTotalPointsDTO);
        if (systemTotalPointsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemTotalPointsDTO result = systemTotalPointsService.save(systemTotalPointsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemTotalPointsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-total-points : get all the systemTotalPoints.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of systemTotalPoints in body
     */
    @GetMapping("/system-total-points")
    @Timed
    public ResponseEntity<List<SystemTotalPointsDTO>> getAllSystemTotalPoints(Pageable pageable) {
        log.debug("REST request to get a page of SystemTotalPoints");
        Page<SystemTotalPointsDTO> page = systemTotalPointsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-total-points");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /system-total-points/:id : get the "id" systemTotalPoints.
     *
     * @param id the id of the systemTotalPointsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemTotalPointsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/system-total-points/{id}")
    @Timed
    public ResponseEntity<SystemTotalPointsDTO> getSystemTotalPoints(@PathVariable Long id) {
        log.debug("REST request to get SystemTotalPoints : {}", id);
        Optional<SystemTotalPointsDTO> systemTotalPointsDTO = systemTotalPointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemTotalPointsDTO);
    }

    /**
     * DELETE  /system-total-points/:id : delete the "id" systemTotalPoints.
     *
     * @param id the id of the systemTotalPointsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-total-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteSystemTotalPoints(@PathVariable Long id) {
        log.debug("REST request to delete SystemTotalPoints : {}", id);
        systemTotalPointsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
