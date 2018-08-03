package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.EventMessageService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.EventMessageDTO;
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
 * REST controller for managing EventMessage.
 */
@RestController
@RequestMapping("/api")
public class EventMessageResource {

    private final Logger log = LoggerFactory.getLogger(EventMessageResource.class);

    private static final String ENTITY_NAME = "eventMessage";

    private final EventMessageService eventMessageService;

    public EventMessageResource(EventMessageService eventMessageService) {
        this.eventMessageService = eventMessageService;
    }

    /**
     * POST  /event-messages : Create a new eventMessage.
     *
     * @param eventMessageDTO the eventMessageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eventMessageDTO, or with status 400 (Bad Request) if the eventMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/event-messages")
    @Timed
    public ResponseEntity<EventMessageDTO> createEventMessage(@Valid @RequestBody EventMessageDTO eventMessageDTO) throws URISyntaxException {
        log.debug("REST request to save EventMessage : {}", eventMessageDTO);
        if (eventMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventMessageDTO result = eventMessageService.save(eventMessageDTO);
        return ResponseEntity.created(new URI("/api/event-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /event-messages : Updates an existing eventMessage.
     *
     * @param eventMessageDTO the eventMessageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eventMessageDTO,
     * or with status 400 (Bad Request) if the eventMessageDTO is not valid,
     * or with status 500 (Internal Server Error) if the eventMessageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/event-messages")
    @Timed
    public ResponseEntity<EventMessageDTO> updateEventMessage(@Valid @RequestBody EventMessageDTO eventMessageDTO) throws URISyntaxException {
        log.debug("REST request to update EventMessage : {}", eventMessageDTO);
        if (eventMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventMessageDTO result = eventMessageService.save(eventMessageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eventMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /event-messages : get all the eventMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eventMessages in body
     */
    @GetMapping("/event-messages")
    @Timed
    public ResponseEntity<List<EventMessageDTO>> getAllEventMessages(Pageable pageable) {
        log.debug("REST request to get a page of EventMessages");
        Page<EventMessageDTO> page = eventMessageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/event-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /event-messages/:id : get the "id" eventMessage.
     *
     * @param id the id of the eventMessageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eventMessageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/event-messages/{id}")
    @Timed
    public ResponseEntity<EventMessageDTO> getEventMessage(@PathVariable Long id) {
        log.debug("REST request to get EventMessage : {}", id);
        Optional<EventMessageDTO> eventMessageDTO = eventMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventMessageDTO);
    }

    /**
     * DELETE  /event-messages/:id : delete the "id" eventMessage.
     *
     * @param id the id of the eventMessageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/event-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteEventMessage(@PathVariable Long id) {
        log.debug("REST request to delete EventMessage : {}", id);
        eventMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
