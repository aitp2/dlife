package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.PinfanPicsService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.PinfanPicsDTO;
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
 * REST controller for managing PinfanPics.
 */
@RestController
@RequestMapping("/api")
public class PinfanPicsResource {

    private final Logger log = LoggerFactory.getLogger(PinfanPicsResource.class);

    private static final String ENTITY_NAME = "pinfanPics";

    private final PinfanPicsService pinfanPicsService;

    public PinfanPicsResource(PinfanPicsService pinfanPicsService) {
        this.pinfanPicsService = pinfanPicsService;
    }

    /**
     * POST  /pinfan-pics : Create a new pinfanPics.
     *
     * @param pinfanPicsDTO the pinfanPicsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pinfanPicsDTO, or with status 400 (Bad Request) if the pinfanPics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pinfan-pics")
    @Timed
    public ResponseEntity<PinfanPicsDTO> createPinfanPics(@Valid @RequestBody PinfanPicsDTO pinfanPicsDTO) throws URISyntaxException {
        log.debug("REST request to save PinfanPics : {}", pinfanPicsDTO);
        if (pinfanPicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new pinfanPics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PinfanPicsDTO result = pinfanPicsService.save(pinfanPicsDTO);
        return ResponseEntity.created(new URI("/api/pinfan-pics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pinfan-pics : Updates an existing pinfanPics.
     *
     * @param pinfanPicsDTO the pinfanPicsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinfanPicsDTO,
     * or with status 400 (Bad Request) if the pinfanPicsDTO is not valid,
     * or with status 500 (Internal Server Error) if the pinfanPicsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pinfan-pics")
    @Timed
    public ResponseEntity<PinfanPicsDTO> updatePinfanPics(@Valid @RequestBody PinfanPicsDTO pinfanPicsDTO) throws URISyntaxException {
        log.debug("REST request to update PinfanPics : {}", pinfanPicsDTO);
        if (pinfanPicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PinfanPicsDTO result = pinfanPicsService.save(pinfanPicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pinfanPicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pinfan-pics : get all the pinfanPics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pinfanPics in body
     */
    @GetMapping("/pinfan-pics")
    @Timed
    public ResponseEntity<List<PinfanPicsDTO>> getAllPinfanPics(Pageable pageable) {
        log.debug("REST request to get a page of PinfanPics");
        Page<PinfanPicsDTO> page = pinfanPicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pinfan-pics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pinfan-pics/:id : get the "id" pinfanPics.
     *
     * @param id the id of the pinfanPicsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pinfanPicsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pinfan-pics/{id}")
    @Timed
    public ResponseEntity<PinfanPicsDTO> getPinfanPics(@PathVariable Long id) {
        log.debug("REST request to get PinfanPics : {}", id);
        Optional<PinfanPicsDTO> pinfanPicsDTO = pinfanPicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pinfanPicsDTO);
    }

    /**
     * DELETE  /pinfan-pics/:id : delete the "id" pinfanPics.
     *
     * @param id the id of the pinfanPicsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pinfan-pics/{id}")
    @Timed
    public ResponseEntity<Void> deletePinfanPics(@PathVariable Long id) {
        log.debug("REST request to delete PinfanPics : {}", id);
        pinfanPicsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
