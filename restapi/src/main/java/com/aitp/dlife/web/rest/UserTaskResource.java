package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.UserTaskService;
import com.aitp.dlife.service.dto.UserTaskDTO;
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
 * REST controller for managing UserTask.
 */
@RestController
@RequestMapping("/api")
public class UserTaskResource {

    private final Logger log = LoggerFactory.getLogger(UserTaskResource.class);

    private static final String ENTITY_NAME = "userTask";

    private final UserTaskService userTaskService;

    public UserTaskResource(UserTaskService userTaskService) {
        this.userTaskService = userTaskService;
    }

    /**
     * POST  /user-tasks : Create a new userTask.
     *
     * @param userTaskDTO the userTaskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userTaskDTO, or with status 400 (Bad Request) if the userTask has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-tasks")
    @Timed
    public ResponseEntity<UserTaskDTO> createUserTask(@RequestBody UserTaskDTO userTaskDTO) throws URISyntaxException {
        log.debug("REST request to save UserTask : {}", userTaskDTO);
        if (userTaskDTO.getId() != null) {
            throw new BadRequestAlertException("A new userTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTaskDTO result = userTaskService.save(userTaskDTO);
        return ResponseEntity.created(new URI("/api/user-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-tasks : Updates an existing userTask.
     *
     * @param userTaskDTO the userTaskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userTaskDTO,
     * or with status 400 (Bad Request) if the userTaskDTO is not valid,
     * or with status 500 (Internal Server Error) if the userTaskDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-tasks")
    @Timed
    public ResponseEntity<UserTaskDTO> updateUserTask(@RequestBody UserTaskDTO userTaskDTO) throws URISyntaxException {
        log.debug("REST request to update UserTask : {}", userTaskDTO);
        if (userTaskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserTaskDTO result = userTaskService.save(userTaskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userTaskDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-tasks : get all the userTasks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userTasks in body
     */
    @GetMapping("/user-tasks")
    @Timed
    public ResponseEntity<List<UserTaskDTO>> getAllUserTasks(Pageable pageable) {
        log.debug("REST request to get a page of UserTasks");
        Page<UserTaskDTO> page = userTaskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-tasks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-tasks/:id : get the "id" userTask.
     *
     * @param id the id of the userTaskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userTaskDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-tasks/{id}")
    @Timed
    public ResponseEntity<UserTaskDTO> getUserTask(@PathVariable Long id) {
        log.debug("REST request to get UserTask : {}", id);
        Optional<UserTaskDTO> userTaskDTO = userTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTaskDTO);
    }

    /**
     * DELETE  /user-tasks/:id : delete the "id" userTask.
     *
     * @param id the id of the userTaskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserTask(@PathVariable Long id) {
        log.debug("REST request to delete UserTask : {}", id);
        userTaskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
