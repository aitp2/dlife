package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.UserEventService;
import com.aitp.dlife.service.dto.UserEventDTO;
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
 * REST controller for managing UserEvent.
 */
@RestController
@RequestMapping("/api")
public class UserEventResource {

    private final Logger log = LoggerFactory.getLogger(UserEventResource.class);

    private static final String ENTITY_NAME = "userEvent";

    private final UserEventService userEventService;

    public UserEventResource(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    /**
     * POST  /user-events : Create a new userEvent.
     *
     * @param userEventDTO the userEventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userEventDTO, or with status 400 (Bad Request) if the userEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-events")
    @Timed
    public ResponseEntity<UserEventDTO> createUserEvent(@RequestBody UserEventDTO userEventDTO) throws URISyntaxException {
        log.debug("REST request to save UserEvent : {}", userEventDTO);
        if (userEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new userEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserEventDTO result = userEventService.save(userEventDTO);
        return ResponseEntity.created(new URI("/api/user-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-events : Updates an existing userEvent.
     *
     * @param userEventDTO the userEventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userEventDTO,
     * or with status 400 (Bad Request) if the userEventDTO is not valid,
     * or with status 500 (Internal Server Error) if the userEventDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-events")
    @Timed
    public ResponseEntity<UserEventDTO> updateUserEvent(@RequestBody UserEventDTO userEventDTO) throws URISyntaxException {
        log.debug("REST request to update UserEvent : {}", userEventDTO);
        if (userEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserEventDTO result = userEventService.save(userEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-events : get all the userEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userEvents in body
     */
    @GetMapping("/user-events")
    @Timed
    public ResponseEntity<List<UserEventDTO>> getAllUserEvents(Pageable pageable) {
        log.debug("REST request to get a page of UserEvents");
        Page<UserEventDTO> page = userEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-events/:id : get the "id" userEvent.
     *
     * @param id the id of the userEventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-events/{id}")
    @Timed
    public ResponseEntity<UserEventDTO> getUserEvent(@PathVariable Long id) {
        log.debug("REST request to get UserEvent : {}", id);
        Optional<UserEventDTO> userEventDTO = userEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userEventDTO);
    }

    /**
     * DELETE  /user-events/:id : delete the "id" userEvent.
     *
     * @param id the id of the userEventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-events/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserEvent(@PathVariable Long id) {
        log.debug("REST request to delete UserEvent : {}", id);
        userEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
