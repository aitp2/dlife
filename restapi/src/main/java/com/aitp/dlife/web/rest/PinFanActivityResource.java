package com.aitp.dlife.web.rest;

import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.service.*;
import com.aitp.dlife.service.dto.*;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HttpUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

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

    private final WechatUserService wechatUserService;

    private final EventMessageService eventMessageService;

    private final MessageService messageService;
    public PinFanActivityResource(PinFanActivityService pinFanActivityService,PinfanPicsService pinfanPicsService,WechatUserService wechatUserService,
                                  EventMessageService eventMessageService,MessageService messageService) {
        this.pinFanActivityService = pinFanActivityService;
        this.pinfanPicsService=pinfanPicsService;
        this.wechatUserService = wechatUserService;
        this.eventMessageService = eventMessageService;
        this.messageService = messageService;
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
        pinFanActivityDTO.setCommentCount(0);
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

        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(pinFanActivityDTO.getWechatUserId()));
        String sexString="";
        if (null!=wechatUserDTO && null!=wechatUserDTO.getSex()){
            Integer sex = wechatUserDTO.getSex();
            if (sex==1) {
                sexString = "male";
            }else if(sex==2){
                sexString = "female";
            }else{
                sexString="";
            }
        }
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","pinfan","",HttpUtil.baseEncoder(pinFanActivityDTO.getActivitiyTile()),"createActivity",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end


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
        PinFanActivityDTO oldDto = pinFanActivityService.findOne(pinFanActivityDTO.getId());
        if (null!=oldDto){
            pinFanActivityDTO.setCommentCount(oldDto.getCommentCount());
            pinFanActivityDTO.setStatus(oldDto.getStatus());
        }
        PinFanActivityDTO result = pinFanActivityService.save(pinFanActivityDTO);

        //record the activity modify event start
        EventMessageDTO eventMessageDTO = eventMessageService.recordEventMessage(EventChannel.PINFAN,DateUtil.getYMDDateString(new Date()),EventType.UPDATE,
            result.getWechatUserId(),result.getActivitiyTile(),result.getId(),result.getAvatar(),result.getNickName());
        if (null!=eventMessageDTO.getId()){
            messageService.createMessageForEvent(eventMessageDTO);
        }
        //record the activity modify event end

        //we need to compar image with the new image,
        List<PinfanPicsDTO> oldImages = pinfanPicsService.findPicsByActivityId(pinFanActivityDTO.getId());
        if (!CollectionUtils.isEmpty(pinFanActivityDTO.getPinfanPics()))
        {

            List<Long> oldIds = new ArrayList<>();

            for(PinfanPicsDTO newImage : pinFanActivityDTO.getPinfanPics())
            {
                if(newImage.getId() == null)
                {
                    newImage.setCreateTime(DateUtil.getYMDDateString(new Date()));
                    newImage.setPinFanActivityId(pinFanActivityDTO.getId());
                    pinfanPicsService.save(newImage);
                    continue;
                }

                oldIds.add(newImage.getId());
            }

            for(PinfanPicsDTO oldImage : oldImages)
            {
                if (!oldIds.contains(oldImage.getId()))
                {
                    pinfanPicsService.delete(oldImage.getId());
                }
            }
        }
        else
        {
            for(PinfanPicsDTO oldImage : oldImages)
            {
                pinfanPicsService.delete(oldImage.getId());
            }
        }


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pinFanActivityDTO.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /pin-fan-activities : Cancel an existing pinFanActivity.
     *
     * @param id the pinFanActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinFanActivityDTO,
     * or with status 400 (Bad Request) if the pinFanActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the pinFanActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pin-fan-activities/cancel/{id}")
    @Timed
    public ResponseEntity<Void> cancelPinFanActivity(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to cancel PinFanActivity : {}", id);
        PinFanActivityDTO pinFanActivityDTO = pinFanActivityService.findOne(id);
        pinFanActivityDTO.setStatus(2);
        pinFanActivityService.save(pinFanActivityDTO);

        //record the activity quit event start
        EventMessageDTO eventMessageDTO = eventMessageService.recordEventMessage(EventChannel.PINFAN,DateUtil.getYMDDateString(new Date()),EventType.CANCEL,
            pinFanActivityDTO.getWechatUserId(),pinFanActivityDTO.getActivitiyTile(),
            pinFanActivityDTO.getId(),pinFanActivityDTO.getAvatar(),
            pinFanActivityDTO.getNickName());
        if (null!=eventMessageDTO.getId()){
            messageService.createMessageForEvent(eventMessageDTO);
        }
        //record the activity quit event end

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /pin-fan-activities : get all the pinFanActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pinFanActivities in body
     */
    @GetMapping("/pin-fan-activities/user/{wechatUserId}")
    @Timed
    public ResponseEntity<List<PinFanActivityDTO>> getAllPinFanActivities(Pageable pageable,@PathVariable String wechatUserId) {
        log.debug("REST request to get a page of PinFanActivities");

        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(wechatUserId));

        String sexString="";
        if (null!=wechatUserDTO && null!=wechatUserDTO.getSex()){
            Integer sex = wechatUserDTO.getSex();
            if (sex==1) {
                sexString = "male";
            }else if(sex==2){
                sexString = "female";
            }else{
                sexString="";
            }
        }
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","pinfan","","","login",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end

        Page<PinFanActivityDTO> page = pinFanActivityService.findAll(pageable);
        final List<PinFanActivityDTO> content = page.getContent();
        if(content!=null){
            for(PinFanActivityDTO activityDTO:content){
                activityDTO.setAttended(isAttend(wechatUserId,activityDTO.getAttendees()));
            }

        }
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

        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(wechatUserId));
        String sexString="";
        if (null!=wechatUserDTO && null!=wechatUserDTO.getSex()){
            Integer sex = wechatUserDTO.getSex();
            if (sex==1) {
                sexString = "male";
            }else if(sex==2){
                sexString = "female";
            }else{
                sexString="";
            }
        }
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","pinfan",pinFanActivityDTO.getId(),HttpUtil.baseEncoder(pinFanActivityDTO.getActivitiyTile()),"PDP",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end
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

    @GetMapping("/pin-fan-activities/createView/{wechatUserId}")
    @Timed
    public ResponseEntity<Void> viewCreatePinFanActivity(@PathVariable String wechatUserId) {
        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(wechatUserId));
        String sexString="";
        if (null!=wechatUserDTO && null!=wechatUserDTO.getSex()){
            Integer sex = wechatUserDTO.getSex();
            if (sex==1) {
                sexString = "male";
            }else if(sex==2){
                sexString = "female";
            }else{
                sexString="";
            }
        }
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","pinfan","","","createView",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatUserId)).build();
    }

    /**
     * PUT  /pin-fan-activities : Updates an existing pinFanActivity.
     *
     * @param id the pinFanActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pinFanActivityDTO,
     * or with status 400 (Bad Request) if the pinFanActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the pinFanActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pin-fan-activities/readingCount")
    @Timed
    public ResponseEntity<PinFanActivityDTO> updateReadingCount(Long id) throws URISyntaxException {
		log.debug("REST request to update PinFanActivity : {}", id);
		if (id == null) {
			throw new BadRequestAlertException("ID is needed", ENTITY_NAME, "idexists");
		}
        PinFanActivityDTO pinfanActivityRecord = pinFanActivityService.findOne(id);
        if (null == pinfanActivityRecord) {
			throw new BadRequestAlertException("Can not find record by id", ENTITY_NAME, "notfound");
		}

        pinfanActivityRecord.setReadingCount(
        		pinfanActivityRecord.getReadingCount() != null ? pinfanActivityRecord.getReadingCount() + 1 : 1);

        PinFanActivityDTO result = pinFanActivityService.save(pinfanActivityRecord);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }

    @GetMapping("/pin-fan-activities/getActivityForTomorrow")
    @Timed
    public List<PinFanActivityDTO> getActivityByStartTime(){
        log.debug("REST request to get PinFanActivity for tomorrow");
        return pinFanActivityService.getPinFanActivityForTomorrow();
    }
}
