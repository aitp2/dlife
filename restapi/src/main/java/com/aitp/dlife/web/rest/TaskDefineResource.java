package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.TaskDefineService;
import com.aitp.dlife.service.dto.TaskDefineDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TaskDefine.
 */
@RestController
@RequestMapping("/api")
public class TaskDefineResource {

    private final Logger log = LoggerFactory.getLogger(TaskDefineResource.class);

    private static final String ENTITY_NAME = "taskDefine";

    private final TaskDefineService taskDefineService;

    public TaskDefineResource(TaskDefineService taskDefineService) {
        this.taskDefineService = taskDefineService;
    }

    /**
     * POST  /task-defines : Create a new taskDefine.
     *
     * @param taskDefineDTO the taskDefineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskDefineDTO, or with status 400 (Bad Request) if the taskDefine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-defines")
    @Timed
    public ResponseEntity<TaskDefineDTO> createTaskDefine(@Valid @RequestBody TaskDefineDTO taskDefineDTO) throws URISyntaxException {
        log.debug("REST request to save TaskDefine : {}", taskDefineDTO);
        if (taskDefineDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskDefine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskDefineDTO result = taskDefineService.save(taskDefineDTO);
        return ResponseEntity.created(new URI("/api/task-defines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-defines : Updates an existing taskDefine.
     *
     * @param taskDefineDTO the taskDefineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskDefineDTO,
     * or with status 400 (Bad Request) if the taskDefineDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskDefineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-defines")
    @Timed
    public ResponseEntity<TaskDefineDTO> updateTaskDefine(@Valid @RequestBody TaskDefineDTO taskDefineDTO) throws URISyntaxException {
        log.debug("REST request to update TaskDefine : {}", taskDefineDTO);
        if (taskDefineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskDefineDTO result = taskDefineService.save(taskDefineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskDefineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /task-defines : get all the taskDefines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taskDefines in body
     */
    @GetMapping("/task-defines")
    @Timed
    public ResponseEntity<List<TaskDefineDTO>> getAllTaskDefines(Pageable pageable) {
        log.debug("REST request to get a page of TaskDefines");
        Page<TaskDefineDTO> page = taskDefineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/task-defines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /task-defines/:id : get the "id" taskDefine.
     *
     * @param id the id of the taskDefineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskDefineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-defines/{id}")
    @Timed
    public ResponseEntity<TaskDefineDTO> getTaskDefine(@PathVariable Long id) {
        log.debug("REST request to get TaskDefine : {}", id);
        Optional<TaskDefineDTO> taskDefineDTO = taskDefineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskDefineDTO);
    }

    /**
     * DELETE  /task-defines/:id : delete the "id" taskDefine.
     *
     * @param id the id of the taskDefineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-defines/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskDefine(@PathVariable Long id) {
        log.debug("REST request to delete TaskDefine : {}", id);
        taskDefineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
