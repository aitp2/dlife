package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.TaskGroupService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.TaskGroupDTO;
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
 * REST controller for managing TaskGroup.
 */
@RestController
@RequestMapping("/api")
public class TaskGroupResource {

    private final Logger log = LoggerFactory.getLogger(TaskGroupResource.class);

    private static final String ENTITY_NAME = "taskGroup";

    private final TaskGroupService taskGroupService;

    public TaskGroupResource(TaskGroupService taskGroupService) {
        this.taskGroupService = taskGroupService;
    }

    /**
     * POST  /task-groups : Create a new taskGroup.
     *
     * @param taskGroupDTO the taskGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskGroupDTO, or with status 400 (Bad Request) if the taskGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-groups")
    @Timed
    public ResponseEntity<TaskGroupDTO> createTaskGroup(@Valid @RequestBody TaskGroupDTO taskGroupDTO) throws URISyntaxException {
        log.debug("REST request to save TaskGroup : {}", taskGroupDTO);
        if (taskGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskGroupDTO result = taskGroupService.save(taskGroupDTO);
        return ResponseEntity.created(new URI("/api/task-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-groups : Updates an existing taskGroup.
     *
     * @param taskGroupDTO the taskGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskGroupDTO,
     * or with status 400 (Bad Request) if the taskGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-groups")
    @Timed
    public ResponseEntity<TaskGroupDTO> updateTaskGroup(@Valid @RequestBody TaskGroupDTO taskGroupDTO) throws URISyntaxException {
        log.debug("REST request to update TaskGroup : {}", taskGroupDTO);
        if (taskGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskGroupDTO result = taskGroupService.save(taskGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-groups : get all the taskGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taskGroups in body
     */
    @GetMapping("/task-groups")
    @Timed
    public ResponseEntity<List<TaskGroupDTO>> getAllTaskGroups(Pageable pageable) {
        log.debug("REST request to get a page of TaskGroups");
        Page<TaskGroupDTO> page = taskGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/task-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /task-groups/:id : get the "id" taskGroup.
     *
     * @param id the id of the taskGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-groups/{id}")
    @Timed
    public ResponseEntity<TaskGroupDTO> getTaskGroup(@PathVariable Long id) {
        log.debug("REST request to get TaskGroup : {}", id);
        Optional<TaskGroupDTO> taskGroupDTO = taskGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskGroupDTO);
    }

    /**
     * DELETE  /task-groups/:id : delete the "id" taskGroup.
     *
     * @param id the id of the taskGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskGroup(@PathVariable Long id) {
        log.debug("REST request to delete TaskGroup : {}", id);
        taskGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
