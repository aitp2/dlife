package com.aitp.dlife.web.rest;

import com.aitp.dlife.domain.PinfanPics;
import com.aitp.dlife.service.PinfanPicsService;
import com.aitp.dlife.service.dto.PinfanPicsDTO;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.RatesService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.RatesDTO;
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

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Rates.
 */
@RestController
@RequestMapping("/api")
public class RatesResource {

    private final Logger log = LoggerFactory.getLogger(RatesResource.class);

    private static final String ENTITY_NAME = "rates";

    private final RatesService ratesService;
    private final PinfanPicsService pinfanPicsService;


    public RatesResource(RatesService ratesService, PinfanPicsService pinfanPicsService) {
        this.ratesService = ratesService;
        this.pinfanPicsService=pinfanPicsService;

    }

    /**
     * POST  /rates : Create a new rates.
     *
     * @param ratesDTO the ratesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ratesDTO, or with status 400 (Bad Request) if the rates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rates")
    @Timed
    public ResponseEntity<RatesDTO> createRates(@Valid @RequestBody RatesDTO ratesDTO) throws URISyntaxException {
        log.debug("REST request to save Rates : {}", ratesDTO);
        if (ratesDTO.getId() != null) {
            throw new BadRequestAlertException("A new rates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Set<PinfanPicsDTO> pinfanPics = new HashSet<>();

        RatesDTO result = ratesService.save(ratesDTO);
        if(ratesDTO.getPinfanPics()!=null&&!ratesDTO.getPinfanPics().isEmpty()){
            for(PinfanPicsDTO pics:ratesDTO.getPinfanPics()){
                pics.setRateId(result.getId());
                pics.setCreateTime(Instant.now());
                pinfanPics.add(pinfanPicsService.save(pics));
            }
        }
        result.setPinfanPics(pinfanPics);
        return ResponseEntity.created(new URI("/api/rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rates : Updates an existing rates.
     *
     * @param ratesDTO the ratesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ratesDTO,
     * or with status 400 (Bad Request) if the ratesDTO is not valid,
     * or with status 500 (Internal Server Error) if the ratesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rates")
    @Timed
    public ResponseEntity<RatesDTO> updateRates(@Valid @RequestBody RatesDTO ratesDTO) throws URISyntaxException {
        log.debug("REST request to update Rates : {}", ratesDTO);
        if (ratesDTO.getId() == null) {
            return createRates(ratesDTO);
        }
        RatesDTO result = ratesService.save(ratesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ratesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rates : get all the rates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rates in body
     */
    @GetMapping("/rates")
    @Timed
    public ResponseEntity<List<RatesDTO>> getAllRates(Pageable pageable) {
        log.debug("REST request to get a page of Rates");
        Page<RatesDTO> page = ratesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rates/:id : get the "id" rates.
     *
     * @param id the id of the ratesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ratesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rates/{id}")
    @Timed
    public ResponseEntity<RatesDTO> getRates(@PathVariable Long id) {
        log.debug("REST request to get Rates : {}", id);
        RatesDTO ratesDTO = ratesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ratesDTO));
    }

    /**
     * DELETE  /rates/:id : delete the "id" rates.
     *
     * @param id the id of the ratesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rates/{id}")
    @Timed
    public ResponseEntity<Void> deleteRates(@PathVariable Long id) {
        log.debug("REST request to delete Rates : {}", id);
        ratesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
