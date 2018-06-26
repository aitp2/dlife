package com.aitp.dlife.web.rest;

import com.aitp.dlife.domain.Attendee;
import com.aitp.dlife.domain.PinfanPics;
import com.aitp.dlife.service.PinfanPicsService;
import com.aitp.dlife.service.dto.AttendeeDTO;
import com.aitp.dlife.service.dto.PinfanPicsDTO;
import com.aitp.dlife.service.mapper.PinfanPicsMapper;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.PinFanActivityService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
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

import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing PinFanActivity.
 */
@RestController
@RequestMapping("/api")
public class PinFanActivityResource {

    private final Logger log = LoggerFactory.getLogger(PinFanActivityResource.class);

    private static final String ENTITY_NAME = "pinFanActivity";

    private final PinFanActivityService pinFanActivityService;

    private final PinfanPicsService pinfanPicsService;
    public PinFanActivityResource(PinFanActivityService pinFanActivityService,PinfanPicsService pinfanPicsService) {
        this.pinFanActivityService = pinFanActivityService;
        this.pinfanPicsService=pinfanPicsService;
    }

    /**
     * POST  /pin-fan-activities : Create a new pinFanActivity.
     *
     * @param pinFanActivityDTO the pinFanActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pinFanActivityDTO, or with status 400 (Bad Request) if the pinFanActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pin-fan-activities")
    @Timed
    public ResponseEntity<PinFanActivityDTO> createPinFanActivity(@Valid @RequestBody PinFanActivityDTO pinFanActivityDTO) throws URISyntaxException {
        log.debug("REST request to save PinFanActivity : {}", pinFanActivityDTO);
        if (pinFanActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new pinFanActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(pinFanActivityDTO.getStatus()==null){
            pinFanActivityDTO.setStatus(0);
        }
        Set<PinfanPicsDTO> pinfanPicsDTOS=new HashSet<>();
        PinFanActivityDTO result = pinFanActivityService.save(pinFanActivityDTO);

        if(pinFanActivityDTO.getPinfanPics()!=null&&!pinFanActivityDTO.getPinfanPics().isEmpty()){
            for(PinfanPicsDTO pics:pinFanActivityDTO.getPinfanPics()){
                if(!StringUtils.isEmpty(pics.getCreateTime())){
                    pics.setCreateTime(DateUtil.getYMDDateString(new Date()));
                }
                pics.setPinFanActivityId(result.getId());
                pinfanPicsDTOS.add(pinfanPicsService.save(pics));
            }
        }
        result.setPinfanPics(pinfanPicsDTOS);
        return ResponseEntity.created(new URI("/api/pin-fan-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pin-fan-activities : Updates an existing pinFanActivity.
     *
     * @param pinFanActivityDTO the pinFanActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinFanActivityDTO,
     * or with status 400 (Bad Request) if the pinFanActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the pinFanActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pin-fan-activities")
    @Timed
    public ResponseEntity<PinFanActivityDTO> updatePinFanActivity(@Valid @RequestBody PinFanActivityDTO pinFanActivityDTO) throws URISyntaxException {
        log.debug("REST request to update PinFanActivity : {}", pinFanActivityDTO);
        if (pinFanActivityDTO.getId() == null) {
            return createPinFanActivity(pinFanActivityDTO);
        }
        PinFanActivityDTO result = pinFanActivityService.save(pinFanActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pinFanActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pin-fan-activities : get all the pinFanActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pinFanActivities in body
     */
    @GetMapping("/pin-fan-activities")
    @Timed
    public ResponseEntity<List<PinFanActivityDTO>> getAllPinFanActivities(Pageable pageable) {
        log.debug("REST request to get a page of PinFanActivities");
        Page<PinFanActivityDTO> page = pinFanActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pin-fan-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    @GetMapping("/pin-fan-activities/mine/{wechatUserId}")
    @Timed
    public ResponseEntity<List<PinFanActivityDTO>> getAllPinFanActivitiesByUserId(Pageable pageable,@PathVariable String wechatUserId) {
        log.debug("REST request to get a page of PinFanActivities");
        Page<PinFanActivityDTO> page = pinFanActivityService.findAllByWechatUserId(pageable,wechatUserId);
        final List<PinFanActivityDTO> content = page.getContent();
        if(content!=null){
            for(PinFanActivityDTO activityDTO:content){
                activityDTO.setAttended(isAttend(wechatUserId,activityDTO.getAttendees()));
            }

        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pin-fan-activities");
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
    private boolean isAttend(String wechatUserId,Set<AttendeeDTO> dtos){
        if(dtos==null||dtos.isEmpty()){
            return false;
        }
        for(AttendeeDTO attendeeDTO:dtos) {
            if (wechatUserId.equals(attendeeDTO.getWechatUserId())) {
                return true;
            }
        }
        return false;
    }
    @GetMapping("/pin-fan-activities/attended/{wechatUserId}")
    @Timed
    public ResponseEntity<List<PinFanActivityDTO>> getAllAttendedPinFanActivitiesByUserId(@PathVariable String wechatUserId) {
        log.debug("REST request to get a page of PinFanActivities");
        List<PinFanActivityDTO> page = pinFanActivityService.findAllAttendedByWechatUserId(wechatUserId);
        return ResponseEntity.ok(page);
    }

    /**
     * GET  /pin-fan-activities/:id : get the "id" pinFanActivity.
     *
     * @param id the id of the pinFanActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pinFanActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pin-fan-activities/{id}")
    @Timed
    public ResponseEntity<PinFanActivityDTO> getPinFanActivity(@PathVariable Long id) {
        log.debug("REST request to get PinFanActivity : {}", id);
        PinFanActivityDTO pinFanActivityDTO = pinFanActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pinFanActivityDTO));
    }
    @GetMapping("/pin-fan-activities/{id}/{wechatUserId}")
    @Timed
    public ResponseEntity<PinFanActivityDTO> getPinFanActivityAndIsAttend(@PathVariable Long id,@PathVariable String wechatUserId ) {
        PinFanActivityDTO pinFanActivityDTO = pinFanActivityService.findOne(id);
        if(pinFanActivityDTO != null){
            pinFanActivityDTO.setAttended(isAttend(wechatUserId,pinFanActivityDTO.getAttendees()));
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pinFanActivityDTO));
    }
    /**
     * DELETE  /pin-fan-activities/:id : delete the "id" pinFanActivity.
     *
     * @param id the id of the pinFanActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pin-fan-activities/{id}")
    @Timed
    public ResponseEntity<Void> deletePinFanActivity(@PathVariable Long id) {
        log.debug("REST request to delete PinFanActivity : {}", id);
        pinFanActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
