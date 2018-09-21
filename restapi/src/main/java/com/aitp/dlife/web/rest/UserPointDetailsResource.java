package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.UserPointDetailsService;
import com.aitp.dlife.service.dto.UserPointDetailsDTO;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserPointDetails.
 */
@RestController
@RequestMapping("/api")
public class UserPointDetailsResource {

    private final Logger log = LoggerFactory.getLogger(UserPointDetailsResource.class);

    private static final String ENTITY_NAME = "userPointDetails";

    private final UserPointDetailsService userPointDetailsService;

    public UserPointDetailsResource(UserPointDetailsService userPointDetailsService) {
        this.userPointDetailsService = userPointDetailsService;
    }

    /**
     * POST  /user-point-details : Create a new userPointDetails.
     *
     * @param userPointDetailsDTO the userPointDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPointDetailsDTO, or with status 400 (Bad Request) if the userPointDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-point-details")
    @Timed
    public ResponseEntity<UserPointDetailsDTO> createUserPointDetails(@RequestBody UserPointDetailsDTO userPointDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save UserPointDetails : {}", userPointDetailsDTO);
        if (userPointDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPointDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPointDetailsDTO result = userPointDetailsService.save(userPointDetailsDTO);
        return ResponseEntity.created(new URI("/api/user-point-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-point-details : Updates an existing userPointDetails.
     *
     * @param userPointDetailsDTO the userPointDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPointDetailsDTO,
     * or with status 400 (Bad Request) if the userPointDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the userPointDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-point-details")
    @Timed
    public ResponseEntity<UserPointDetailsDTO> updateUserPointDetails(@RequestBody UserPointDetailsDTO userPointDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update UserPointDetails : {}", userPointDetailsDTO);
        if (userPointDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPointDetailsDTO result = userPointDetailsService.save(userPointDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPointDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-point-details : get all the userPointDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userPointDetails in body
     */
    @GetMapping("/user-point-details")
    @Timed
    public ResponseEntity<List<UserPointDetailsDTO>> getAllUserPointDetails(Pageable pageable) {
        log.debug("REST request to get a page of UserPointDetails");
        Page<UserPointDetailsDTO> page = userPointDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-point-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /user-point-details : get all the userPointDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userPointDetails in body
     */
    @GetMapping("/user-point-details/user/{userid}")
    @Timed
    public ResponseEntity<List<UserPointDetailsDTO>> getAllUserPointDetails(@PathVariable String userid, Pageable pageable) {
        log.debug("REST request to get a page of UserPointDetails");
        Page<UserPointDetailsDTO> page = userPointDetailsService.findAllByUserid(userid, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-point-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }   

    /**
     * GET  /user-point-details/:id : get the "id" userPointDetails.
     *
     * @param id the id of the userPointDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPointDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-point-details/{id}")
    @Timed
    public ResponseEntity<UserPointDetailsDTO> getUserPointDetails(@PathVariable Long id) {
        log.debug("REST request to get UserPointDetails : {}", id);
        Optional<UserPointDetailsDTO> userPointDetailsDTO = userPointDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPointDetailsDTO);
    }

    /**
     * DELETE  /user-point-details/:id : delete the "id" userPointDetails.
     *
     * @param id the id of the userPointDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-point-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPointDetails(@PathVariable Long id) {
        log.debug("REST request to delete UserPointDetails : {}", id);
        userPointDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
