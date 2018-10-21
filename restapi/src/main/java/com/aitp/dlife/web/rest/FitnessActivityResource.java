package com.aitp.dlife.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.validation.Valid;

import com.aitp.dlife.service.*;
import com.aitp.dlife.service.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.repository.specification.FitnessActivitySpecification;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.HttpUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing FitnessActivity.
 */
@RestController
@Api(value = "小目标活动API", tags = "小目标活动API")
@RequestMapping("/api")
public class FitnessActivityResource {

    private final Logger log = LoggerFactory.getLogger(FitnessActivityResource.class);

    private static final String ENTITY_NAME = "fitnessActivity";

    private final FitnessActivityService fitnessActivityService;

    private final PicsService picsService;

    private final WechatUserService wechatUserService;

    private final EventMessageService eventMessageService;

    private final MessageService messageService;

    private final ActivityParticipationService activityParticipationService;

    private final ClockInActivityService clockInActivityService;

    private final PointService pointService;

    public FitnessActivityResource(FitnessActivityService fitnessActivityService,PicsService picsService,WechatUserService wechatUserService, EventMessageService eventMessageService,
                                   MessageService messageService, ActivityParticipationService activityParticipationService, ClockInActivityService clockInActivityService,PointService pointService) {
        this.fitnessActivityService = fitnessActivityService;
        this.picsService = picsService;
        this.wechatUserService=wechatUserService;
        this.eventMessageService = eventMessageService;
        this.messageService = messageService;
        this.activityParticipationService = activityParticipationService;
        this.clockInActivityService = clockInActivityService;
        this.pointService = pointService;
    }

    /**
     * POST  /fitness-activities : Create a new fitnessActivity.
     *
     * @param fitnessActivityDTO the fitnessActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fitnessActivityDTO, or with status 400 (Bad Request) if the fitnessActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fitness-activities")
    @Timed
    public ResponseEntity<FitnessActivityDTO> createFitnessActivity(@Valid @RequestBody FitnessActivityDTO fitnessActivityDTO) throws URISyntaxException {
        log.debug("REST request to save FitnessActivity : {}", fitnessActivityDTO);
        if (fitnessActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new fitnessActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(null == fitnessActivityDTO.getReminderHour()){
            fitnessActivityDTO.setReminderHour(22);
        }
        Set<PicsDTO> imagesDTO = new HashSet<>();
        fitnessActivityDTO.setCommentCount(0);
        FitnessActivityDTO result = fitnessActivityService.save(fitnessActivityDTO);

		if (fitnessActivityDTO.getImages() != null && !fitnessActivityDTO.getImages().isEmpty()){
        	for(PicsDTO pics : fitnessActivityDTO.getImages()){
                     pics.setCreateTime(DateUtil.getYMDDateString(new Date()));
        		 pics.setFitnessActivityId(result.getId());
        		 imagesDTO.add(picsService.save(pics));
        	}
        }
        result.setImages(imagesDTO);

        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(fitnessActivityDTO.getWechatUserId()));
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
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","fit","",HttpUtil.baseEncoder(fitnessActivityDTO.getTitle()),"createActivity",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end

        //record the activity create event start
        EventMessageDTO eventMessageDTO = eventMessageService.recordEventMessage(EventChannel.FITNESS,DateUtil.getYMDDateString(new Date()),EventType.CREATE,
            result.getWechatUserId(),result.getTitle(),result.getId(),wechatUserDTO.getAvatar(),wechatUserDTO.getNickName());
        if (null!=eventMessageDTO.getId()){
            messageService.createMessageForEvent(eventMessageDTO);
        }
        //record the activity create event end

        pointService.saveNewEvent(fitnessActivityDTO.getWechatUserId(), "发起活动", PointEventType.PUBILSHACTION,CommentChannel.FIT.toString(),fitnessActivityDTO.getTitle());

        return ResponseEntity.created(new URI("/api/fitness-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }




    /**
     * PUT  /fitness-activities : Updates an existing fitnessActivity.
     *
     * @param fitnessActivityDTO the fitnessActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fitnessActivityDTO,
     * or with status 400 (Bad Request) if the fitnessActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the fitnessActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fitness-activities")
    @Timed
    public ResponseEntity<FitnessActivityDTO> updateFitnessActivity(@Valid @RequestBody FitnessActivityDTO fitnessActivityDTO) throws URISyntaxException {
        log.debug("REST request to update FitnessActivity : {}", fitnessActivityDTO);
        if (fitnessActivityDTO.getId() == null) {
            return createFitnessActivity(fitnessActivityDTO);
        }
        FitnessActivityDTO oldDto = fitnessActivityService.findOne(fitnessActivityDTO.getId());
        if(null!=oldDto){
            fitnessActivityDTO.setStatus(oldDto.getStatus());
            fitnessActivityDTO.setCommentCount(oldDto.getCommentCount());
            fitnessActivityDTO.setReadingCount(oldDto.getReadingCount());
        }

        if(null == fitnessActivityDTO.getReminderHour()){
            fitnessActivityDTO.setReminderHour(22);
        }

        FitnessActivityDTO result = fitnessActivityService.save(fitnessActivityDTO);
        List<PicsDTO> oldImages = picsService.findPicsByActivityId(fitnessActivityDTO.getId());
        if (!CollectionUtils.isEmpty(fitnessActivityDTO.getImages()))
        {
            log.info("save  image");
            List<Long> oldIds = new ArrayList<>();

            for(PicsDTO newImage : fitnessActivityDTO.getImages())
            {
                if(newImage.getId() == null)
                {
                    log.info("save new image");
                    newImage.setCreateTime(DateUtil.getYMDDateString(new Date()));
                    newImage.setFitnessActivityId(fitnessActivityDTO.getId());
                    picsService.save(newImage);
                    continue;
                }
                log.info("add old image");
                oldIds.add(newImage.getId());
            }

            for(PicsDTO oldImage : oldImages)
            {
                if (!oldIds.contains(oldImage.getId()))
                {
                    log.info("delete old image");
                    picsService.delete(oldImage.getId());
                }
            }
        }
        else
        {
            log.info("delete image");
            for(PicsDTO oldImage : oldImages)
            {
                picsService.delete(oldImage.getId());
            }
        }

        //record the activity modify event start
        EventMessageDTO eventMessageDTO = eventMessageService.recordEventMessage(EventChannel.FITNESS,DateUtil.getYMDDateString(new Date()),EventType.UPDATE,
            result.getWechatUserId(),result.getTitle(),result.getId(),result.getAvatar(),result.getNickName());
        if (null!=eventMessageDTO.getId()){
            messageService.createMessageForEvent(eventMessageDTO);
        }
        //record the activity modify event end

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fitnessActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fitness-activities : get all the fitnessActivities.
     *
     * @param pageable the pagination information
     * @param eventCount the request count of the activity's envent message, default is 0
     * @return the ResponseEntity with status 200 (OK) and the list of fitnessActivities in body
     */
    @GetMapping("/fitness-activities")
    @Timed
    public ResponseEntity<List<FitnessActivityDTO>> getAllFitnessActivities(Pageable pageable,FitnessActivitySpecification spec, Integer eventCount) {
        log.debug("REST request to get a page of FitnessActivities, event count:"+ eventCount);
        Page<FitnessActivityDTO> page;
        if (eventCount == null) {
            page = fitnessActivityService.findAll(pageable,spec);
        } else {
            page = fitnessActivityService.findAllAndEvent(pageable,spec,eventCount);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fitness-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fitness-activities : get all the fitnessActivities.
     *
     * @param spec the FitnessActivitySpecification information
     * @return the ResponseEntity with status 200 (OK) and the list of fitnessActivities in body
     */
    @GetMapping("/fitness-activities-today")
    @ApiOperation(value = "获取当天的小目标", notes = "获取当天的小目标，传participantId=wechatuserid和status=2（进行中），根据ClockStatus来区分是否打过卡,未打卡（closkStatus=0），已打卡（closkStatus=1）", response = FitnessActivityDTO.class)
    @Timed
    public ResponseEntity<List<FitnessActivityDTO>> getFitnessActivitiesForToday(FitnessActivitySpecification spec) {
        if (null == spec.getQuerys().getParticipantId()) {
            throw new BadRequestAlertException("wechatUserId is null", ENTITY_NAME, "wechatUserIdNULL");
        }
        String userId = spec.getQuerys().getParticipantId().toString();
        List<FitnessActivityDTO> list = fitnessActivityService.findAll(spec);
        for(FitnessActivityDTO activityDTO : list){
            activityDTO.setActivityParticipations(null);
            activityDTO.setClockStatus(0);
            ActivityParticipationDTO dto = activityParticipationService.getByUidAndActivityId(activityDTO.getId(),userId);
            boolean flag = clockInActivityService.isClockedIn(userId,dto.getId());
            activityDTO.setClockStatus(flag?1:0);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * GET  /fitness-activities/:id : get the "id" fitnessActivity.
     *
     * @param id the id of the fitnessActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fitnessActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fitness-activities/{id}")
    @Timed
    public ResponseEntity<FitnessActivityDTO> getFitnessActivity(@PathVariable Long id) {
        log.debug("REST request to get FitnessActivity : {}", id);
        FitnessActivityDTO fitnessActivityDTO = fitnessActivityService.findOne(id);
        fitnessActivityService.ActivityStatus(fitnessActivityDTO);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fitnessActivityDTO));
    }

    /**
     * DELETE  /fitness-activities/:id : delete the "id" fitnessActivity.
     *
     * @param id the id of the fitnessActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fitness-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteFitnessActivity(@PathVariable Long id) {
        log.debug("REST request to delete FitnessActivity : {}", id);
        fitnessActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/fitness-activities/getActivitiesByWechatUserId")
    @Timed
    public List<FitnessActivityDTO> getActivitiesByWechatUserId(String wechatUserId) {
        log.debug("REST request to get ClockinSummary by wechatUserId: {}", wechatUserId);
        if (null == wechatUserId) {
            throw new BadRequestAlertException("wechatUserId is null", ENTITY_NAME, "wechatUserIdNULL");
        }


        return fitnessActivityService.getActivitiesByWechatUserId(wechatUserId);
    }


    @GetMapping("/fitness-activities/createView/{wechatUserId}")
    @Timed
    public ResponseEntity<Void> viewCreateFitnessActivity(@PathVariable String wechatUserId) {
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
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","fit","","","createView",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatUserId)).build();
    }

    @PutMapping("/fitness-activities/readingCount")
    @Timed
	public ResponseEntity<FitnessActivityDTO> updateReadingCount(Long id) throws URISyntaxException {
		log.debug("REST request to update FitnessActivity : {}", id);
		if (id == null) {
			throw new BadRequestAlertException("ID is needed", ENTITY_NAME, "idexists");
		}
		FitnessActivityDTO fitnessActivityRecord = fitnessActivityService.findOne(id);
		if (null == fitnessActivityRecord) {
			throw new BadRequestAlertException("Can not find record by id", ENTITY_NAME, "notfound");
		}

		fitnessActivityRecord.setReadingCount(
				fitnessActivityRecord.getReadingCount() != null ? fitnessActivityRecord.getReadingCount() + 1 : 1);

        if(null == fitnessActivityRecord.getReminderHour()){
            fitnessActivityRecord.setReminderHour(22);
        }
		FitnessActivityDTO result = fitnessActivityService.save(fitnessActivityRecord);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString())).body(result);
	}

}
