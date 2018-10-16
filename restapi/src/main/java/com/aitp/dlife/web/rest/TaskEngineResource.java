package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.TaskEngineService;
import com.aitp.dlife.service.dto.EventResultDTO;
import com.aitp.dlife.service.dto.UserEventDTO;
import com.aitp.dlife.service.dto.UserPointDetailsDTO;
import com.aitp.dlife.service.dto.UserTaskDTO;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

import java.util.List;

/**
 * REST controller for managing TaskDefine.
 */
@RestController
@RequestMapping("/api")
public class TaskEngineResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(TaskEngineResource.class);

    /** The task engine service. */
    private final TaskEngineService taskEngineService;

    /**
     * Instantiates a new task engine resource.
     *
     * @param taskEngineService the task engine service
     */
    public TaskEngineResource(TaskEngineService taskEngineService) {
        this.taskEngineService = taskEngineService;
    }


    /**
     * Gets the user tasks.
     *
     * @param userid the userid
     * @return the user tasks
     * @throws URISyntaxException the URI syntax exception
     */
    @GetMapping("/task-engine/user-tasks/{userid}")
    @Timed
    public ResponseEntity<List<UserTaskDTO>> getUserTasks(@PathVariable String userid) throws URISyntaxException {
    	log.debug("REST request to get user's tasks. userid= : {}", userid);
        List<UserTaskDTO> result = taskEngineService.getUserTasks(userid);
        return new ResponseEntity(result, HttpStatus.OK);
    }
    
    /**
     * Notify new event.
     *
     * @param userEventDTO the user event DTO
     * @return the response entity
     * @throws URISyntaxException the URI syntax exception
     */
    @PostMapping("/task-engine/event/new")
    @Timed
    public ResponseEntity<EventResultDTO> notifyNewEvent(@Valid @RequestBody UserEventDTO userEventDTO) throws URISyntaxException {
        log.debug("REST request to notify NewEvent : {}", userEventDTO);
        return new ResponseEntity(taskEngineService.saveNewEvent(userEventDTO), HttpStatus.OK);
    }
    
    /**
     * Notify new event.
     *
     * @param userPointDetailsDTO the user point details DTO
     * @return the response entity
     * @throws URISyntaxException the URI syntax exception
     */
    @PostMapping("/task-engine/append-point")
    @Timed
    public ResponseEntity<EventResultDTO> notifyNewEvent(@Valid @RequestBody UserPointDetailsDTO userPointDetailsDTO) throws URISyntaxException {
        log.debug("REST request to notify UserPointDetailDTO : {}", userPointDetailsDTO);
        if (userPointDetailsDTO.getUserid() == null ) {
            throw new BadRequestAlertException("A new record must have userid", "UserPointDetailsDTO", "notnull");
        }

        return new ResponseEntity(taskEngineService.updatePointDetails(userPointDetailsDTO), HttpStatus.OK);
        
    }

}
