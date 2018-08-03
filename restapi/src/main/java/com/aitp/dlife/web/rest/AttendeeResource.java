package com.aitp.dlife.web.rest;

import com.aitp.dlife.service.PinFanActivityService;
import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HttpUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.AttendeeService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.AttendeeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Attendee.
 */
@RestController
@RequestMapping("/api")
public class AttendeeResource {

    private final Logger log = LoggerFactory.getLogger(AttendeeResource.class);

    private static final String ENTITY_NAME = "attendee";

    private final AttendeeService attendeeService;
    private final PinFanActivityService pinFanActivityService;
    private final WechatUserService wechatUserService;

    public AttendeeResource(AttendeeService attendeeService,PinFanActivityService pinFanActivityService,WechatUserService wechatUserService) {
        this.attendeeService = attendeeService;
        this.pinFanActivityService=pinFanActivityService;
        this.wechatUserService=wechatUserService;
    }

    /**
     * POST  /attendees : Create a new attendee.
     *
     * @param attendeeDTO the attendeeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendeeDTO, or with status 400 (Bad Request) if the attendee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attendees")
    @Timed
    public ResponseEntity createAttendee(@Valid @RequestBody AttendeeDTO attendeeDTO) throws URISyntaxException {
        log.debug("REST request to save Attendee : {}", attendeeDTO);
        if (attendeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(StringUtils.isEmpty(attendeeDTO.getParticipationTime())){
            attendeeDTO.setParticipationTime(DateUtil.getYMDDateString(new Date()));
        }
        PinFanActivityDTO activityDTO = pinFanActivityService.findOne(attendeeDTO.getPinFanActivityId());
        if(activityDTO.getAttendees()!=null && activityDTO.getUpperLimit() != null && activityDTO.getAttendees().size()>=activityDTO.getUpperLimit()){
            throw new BadRequestAlertException("活动人数已达上限", ENTITY_NAME, "活动人数已达上限");
        }
        AttendeeDTO result = attendeeService.save(attendeeDTO);

        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(attendeeDTO.getWechatUserId()));
        String sexString="";
        if (null!=wechatUserDTO && null!=wechatUserDTO.isSex()){
            boolean sex = wechatUserDTO.isSex();
            if (sex) {
                sexString = "male";
            }else{
                sexString = "female";
            }
        }
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","pinfan",activityDTO.getId(),HttpUtil.baseEncoder(activityDTO.getActivitiyTile()),"attend",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end


        return ResponseEntity.ok().body(result);
    }

    /**
     * PUT  /attendees : Updates an existing attendee.
     *
     * @param attendeeDTO the attendeeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendeeDTO,
     * or with status 400 (Bad Request) if the attendeeDTO is not valid,
     * or with status 500 (Internal Server Error) if the attendeeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attendees")
    @Timed
    public ResponseEntity<AttendeeDTO> updateAttendee(@Valid @RequestBody AttendeeDTO attendeeDTO) throws URISyntaxException {
        log.debug("REST request to update Attendee : {}", attendeeDTO);
        if (attendeeDTO.getId() == null) {
            return createAttendee(attendeeDTO);
        }
        AttendeeDTO result = attendeeService.save(attendeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendees : get all the attendees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of attendees in body
     */
    @GetMapping("/attendees")
    @Timed
    public ResponseEntity<List<AttendeeDTO>> getAllAttendees(Pageable pageable) {
        log.debug("REST request to get a page of Attendees");
        Page<AttendeeDTO> page = attendeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attendees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /attendees/:id : get the "id" attendee.
     *
     * @param id the id of the attendeeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendeeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attendees/{id}")
    @Timed
    public ResponseEntity<AttendeeDTO> getAttendee(@PathVariable Long id) {
        log.debug("REST request to get Attendee : {}", id);
        AttendeeDTO attendeeDTO = attendeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attendeeDTO));
    }

    /**
     * DELETE  /attendees/:id : delete the "id" attendee.
     *
     * @param id the id of the attendeeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendees/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttendee(@PathVariable Long id) {
        log.debug("REST request to delete Attendee : {}", id);
        attendeeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
