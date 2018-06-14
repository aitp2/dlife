package com.aitp.dlife.pinfan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.pinfan.service.RatesPicsService;
import com.aitp.dlife.pinfan.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.pinfan.web.rest.util.HeaderUtil;
import com.aitp.dlife.pinfan.service.dto.RatesPicsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RatesPics.
 */
@RestController
@RequestMapping("/api")
public class RatesPicsResource {

    private final Logger log = LoggerFactory.getLogger(RatesPicsResource.class);

    private static final String ENTITY_NAME = "ratesPics";

    private final RatesPicsService ratesPicsService;

    public RatesPicsResource(RatesPicsService ratesPicsService) {
        this.ratesPicsService = ratesPicsService;
    }

    /**
     * POST  /rates-pics : Create a new ratesPics.
     *
     * @param ratesPicsDTO the ratesPicsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ratesPicsDTO, or with status 400 (Bad Request) if the ratesPics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rates-pics")
    @Timed
    public ResponseEntity<RatesPicsDTO> createRatesPics(@Valid @RequestBody RatesPicsDTO ratesPicsDTO) throws URISyntaxException {
        log.debug("REST request to save RatesPics : {}", ratesPicsDTO);
        if (ratesPicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new ratesPics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatesPicsDTO result = ratesPicsService.save(ratesPicsDTO);
        return ResponseEntity.created(new URI("/api/rates-pics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rates-pics : Updates an existing ratesPics.
     *
     * @param ratesPicsDTO the ratesPicsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ratesPicsDTO,
     * or with status 400 (Bad Request) if the ratesPicsDTO is not valid,
     * or with status 500 (Internal Server Error) if the ratesPicsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rates-pics")
    @Timed
    public ResponseEntity<RatesPicsDTO> updateRatesPics(@Valid @RequestBody RatesPicsDTO ratesPicsDTO) throws URISyntaxException {
        log.debug("REST request to update RatesPics : {}", ratesPicsDTO);
        if (ratesPicsDTO.getId() == null) {
            return createRatesPics(ratesPicsDTO);
        }
        RatesPicsDTO result = ratesPicsService.save(ratesPicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ratesPicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rates-pics : get all the ratesPics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ratesPics in body
     */
    @GetMapping("/rates-pics")
    @Timed
    public List<RatesPicsDTO> getAllRatesPics() {
        log.debug("REST request to get all RatesPics");
        return ratesPicsService.findAll();
        }

    /**
     * GET  /rates-pics/:id : get the "id" ratesPics.
     *
     * @param id the id of the ratesPicsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ratesPicsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rates-pics/{id}")
    @Timed
    public ResponseEntity<RatesPicsDTO> getRatesPics(@PathVariable Long id) {
        log.debug("REST request to get RatesPics : {}", id);
        RatesPicsDTO ratesPicsDTO = ratesPicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ratesPicsDTO));
    }

    /**
     * DELETE  /rates-pics/:id : delete the "id" ratesPics.
     *
     * @param id the id of the ratesPicsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rates-pics/{id}")
    @Timed
    public ResponseEntity<Void> deleteRatesPics(@PathVariable Long id) {
        log.debug("REST request to delete RatesPics : {}", id);
        ratesPicsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rates-pics?query=:query : search for the ratesPics corresponding
     * to the query.
     *
     * @param query the query of the ratesPics search
     * @return the result of the search
     */
    @GetMapping("/_search/rates-pics")
    @Timed
    public List<RatesPicsDTO> searchRatesPics(@RequestParam String query) {
        log.debug("REST request to search RatesPics for query {}", query);
        return ratesPicsService.search(query);
    }

}
