package com.aitp.dlife.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.service.*;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.service.enums.Status;
import com.aitp.dlife.web.rest.util.HttpUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.repository.FitnessActivityRepository;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing ActivityParticipation.
 */
@RestController
@Api(value = "小目标报名API", tags = "小目标报名API")
@RequestMapping("/api")
public class ActivityParticipationResource {

	private final Logger log = LoggerFactory.getLogger(ActivityParticipationResource.class);

	private static final String ENTITY_NAME = "activityParticipation";

	private final ActivityParticipationService activityParticipationService;

	private final FitnessActivityRepository fitnessActivityRepository;

	private final WechatUserService wechatUserService;

	private final FitnessActivityService fitnessActivityService;

    private final EventMessageService eventMessageService;

    private final MessageService messageService;
    
    private final PointService pointService;

	public ActivityParticipationResource(ActivityParticipationService activityParticipationService,
                                         FitnessActivityRepository fitnessActivityRepository, WechatUserService wechatUserService,
                                         FitnessActivityService fitnessActivityService, EventMessageService eventMessageService,
                                         MessageService messageService,PointService pointService) {
		this.activityParticipationService = activityParticipationService;
		this.fitnessActivityRepository = fitnessActivityRepository;
		this.wechatUserService = wechatUserService;
		this.fitnessActivityService = fitnessActivityService;
        this.eventMessageService = eventMessageService;
        this.messageService = messageService;
        this.pointService=pointService;
    }

	/**
	 * POST /activity-participations : Create a new activityParticipation.
	 *
	 * @param activityParticipationDTO
	 *            the activityParticipationDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new activityParticipationDTO, or with status 400 (Bad Request) if
	 *         the activityParticipation has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/activity-participations")
	@Timed
	@ApiOperation(value = "创建报名信息", notes = "根据activityParticipationDTO传入打卡信息", response = ActivityParticipationDTO.class)
	public ResponseEntity<ActivityParticipationDTO> createActivityParticipation(
			@Valid @RequestBody ActivityParticipationDTO activityParticipationDTO) throws URISyntaxException {
		log.debug("REST request to save ActivityParticipation : {}", activityParticipationDTO);
		if (activityParticipationDTO.getId() != null) {
			throw new BadRequestAlertException("A new activityParticipation cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		activityParticipationDTO.setParticipationTime(DateUtil.getYMDDateString(new Date()));

		FitnessActivity fitnessActivity = fitnessActivityRepository.findById(activityParticipationDTO.getActivityId()).get();
		fitnessActivity.setModifyTime(Instant.now());
		fitnessActivityRepository.save(fitnessActivity);

		// log for markting start
		WechatUserDTO wechatUserDTO = wechatUserService
				.findOne(Long.valueOf(activityParticipationDTO.getWechatUserId()));
		String sexString = "";
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
		FitnessActivityDTO dto = fitnessActivityService.findOne(activityParticipationDTO.getActivityId());

		log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}",
				"fit", dto.getId(), HttpUtil.baseEncoder(dto.getTitle()), "attend",
				DateUtil.getYMDDateString(new Date()), wechatUserDTO.getNickName(), sexString);
		// log for markting end

		ActivityParticipationDTO result = activityParticipationService.save(activityParticipationDTO);

        //record the activity participation event start
        EventMessageDTO eventMessageDTO = eventMessageService.recordEventMessage(EventChannel.FITNESS,DateUtil.getYMDDateString(new Date()), EventType.ATTEND,
            result.getWechatUserId(),dto.getTitle(),dto.getId(),wechatUserDTO.getAvatar(),wechatUserDTO.getNickName());
        if (null!=eventMessageDTO.getId()){
            messageService.createMessageForEvent(eventMessageDTO);
        }
        //record the activity participation event end

        pointService.saveNewEvent(activityParticipationDTO.getWechatUserId(), "参与活动", PointEventType.JIONACTION,CommentChannel.FIT.toString(),dto.getTitle());
        
		return ResponseEntity.created(new URI("/api/activity-participations/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /activity-participations : Updates an existing activityParticipation.
	 *
	 * @param activityParticipationDTO
	 *            the activityParticipationDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         activityParticipationDTO, or with status 400 (Bad Request) if the
	 *         activityParticipationDTO is not valid, or with status 500
	 *         (Internal Server Error) if the activityParticipationDTO couldn't
	 *         be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/activity-participations")
	@Timed
	@ApiOperation(value = "修改报名信息", notes = "根据activityParticipationDTO传入打卡信息", response = ActivityParticipationDTO.class)
	public ResponseEntity<ActivityParticipationDTO> updateActivityParticipation(
			@Valid @RequestBody ActivityParticipationDTO activityParticipationDTO) throws URISyntaxException {
		log.debug("REST request to update ActivityParticipation : {}", activityParticipationDTO);
		if (activityParticipationDTO.getId() == null) {
			return createActivityParticipation(activityParticipationDTO);
		}
		ActivityParticipationDTO result = activityParticipationService.save(activityParticipationDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activityParticipationDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /activity-participations : get all the activityParticipations.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         activityParticipations in body
	 */
	@GetMapping("/activity-participations")
	@Timed
	@ApiOperation(value = "获取所有打卡信息", notes = "获取所有打卡信息，目前无查询条件", response = ActivityParticipationDTO.class)
	public ResponseEntity<List<ActivityParticipationDTO>> getAllActivityParticipations(Pageable pageable) {
		log.debug("REST request to get a page of ActivityParticipations");
		Page<ActivityParticipationDTO> page = activityParticipationService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activity-participations");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}



	/**
	 * GET /activity-participations : get all the activityParticipations.
	 *
	 * @param isClockIn
     * @param clockinDate
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         activityParticipations in body
	 */
	@GetMapping("/activity-participations/getNonClock")
	@Timed
	@ApiOperation(value = "获取未打卡信息", notes = "根据传入数据查询未打卡的报名人(即将过时)", response = ActivityParticipationDTO.class)
	public List<ActivityParticipationDTO> getAllActivityParticipations(
			@RequestParam Long isClockIn,
			 @RequestParam  String clockinDate) {
		log.debug("REST request to get a page of ActivityParticipations");
		List<FitnessActivityDTO> faActivityDTOs = fitnessActivityService.getActivitiesByState(Status.IN_PROGRESS.getValue());
		List<Long> ids = new ArrayList<>();
		List<ActivityParticipationDTO> activityParticipationDTOs= new ArrayList<>();
		for (FitnessActivityDTO fitnessActivityDTO : faActivityDTOs) {
			 activityParticipationDTOs.addAll(fitnessActivityDTO.getActivityParticipations());
			 ids.addAll(fitnessActivityDTO.getActivityParticipations().stream().map(ActivityParticipationDTO::getId).collect(Collectors.toList()));
		}
		List<ActivityParticipationDTO> nonclockActivityParticipationDTO  =  activityParticipationService.findTodayClockActivityParticipation(ids,clockinDate,isClockIn);
		System.out.println(nonclockActivityParticipationDTO.toString());
		List<ActivityParticipationDTO> nonclockActivityParticipationDTOa = activityParticipationDTOs.stream().filter(i-> nonclockActivityParticipationDTO.contains(i)).collect(Collectors.toList());
		return nonclockActivityParticipationDTOa;
	}



	/**
	 * GET /activity-participations/:id : get the "id" activityParticipation.
	 *
	 * @param id
	 *            the id of the activityParticipationDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         activityParticipationDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/activity-participations/{id}")
	@Timed
	@ApiOperation(value = "获取报名资源信息", notes = "根据报名ID获取报名信息", response = ActivityParticipationDTO.class)
	public ResponseEntity<ActivityParticipationDTO> getActivityParticipation(@PathVariable Long id) {
		log.debug("REST request to get ActivityParticipation : {}", id);
        Optional<ActivityParticipationDTO> activityParticipationDTO = activityParticipationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityParticipationDTO);
	}



	/**
	 * DELETE /activity-participations/:id : delete the "id"
	 * activityParticipation.
	 *
	 * @param id
	 *            the id of the activityParticipationDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/activity-participations/{id}")
	@Timed
	@ApiOperation(value = "删除出报名信息", notes = "根据报名ID删除报名信息", response = ActivityParticipationDTO.class)
	public ResponseEntity<Void> deleteActivityParticipation(@PathVariable Long id) {
		log.debug("REST request to delete ActivityParticipation : {}", id);

        Optional<ActivityParticipationDTO> activityParticipationDTO = activityParticipationService.findOne(id);

        if (activityParticipationDTO.isPresent()){
            //record the activity quit event start
            EventMessageDTO eventMessageDTO = eventMessageService.recordEventMessage(EventChannel.FITNESS,DateUtil.getYMDDateString(new Date()),EventType.QUIT,
                activityParticipationDTO.get().getWechatUserId(),activityParticipationDTO.get().getActivityTitle(),
                activityParticipationDTO.get().getActivityId(),activityParticipationDTO.get().getAvatar(),
                activityParticipationDTO.get().getNickName());
            if (null!=eventMessageDTO.getId()){
                messageService.createMessageForEvent(eventMessageDTO);
            }
            //record the activity quit event end

        }

        activityParticipationService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	@GetMapping("/activity-participations/getParticipationsByActivityId")
	@Timed
	@ApiOperation(value = "根据活动ID获取报名信息", notes = "即将过时", response = ActivityParticipationDTO.class)
	public List<ActivityParticipationDTO> getParticipationsByActivityId(@ApiParam(value="活动id") Long activityId) {
		log.debug("REST request to get ActivityParticipation : {}", activityId);
		if (null == activityId) {
			throw new BadRequestAlertException("activityId can not be null", ENTITY_NAME, "activityIdNULL");
		}
		// resort
		List<ActivityParticipationDTO> result = activityParticipationService.findByActivity(activityId);
		result.sort((b1, b2) -> b1.getClockinCount() == b2.getClockinCount() ? 0
				: (b1.getClockinCount() > b2.getClockinCount() ? -1 : 1));
		return result;
	}



	@GetMapping("/activity-participations/{wechatUserId}/{activityId}")
	@Timed
	public ResponseEntity<ActivityParticipationDTO> getSignUpInfo(@PathVariable String wechatUserId,
			@PathVariable Long activityId) {
		log.debug("REST request to get ClockinSummary by wechatUserId: {}", wechatUserId);

		// log for markting start
		if (wechatUserId == null || activityId == null) {
			throw new BadRequestAlertException("wechatUserId or activityId can not be null", ENTITY_NAME, "idexists");
		}
		FitnessActivityDTO dto = fitnessActivityService.findOne(activityId);
		WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(wechatUserId));
		String sexString = "";
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
		log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}",
				"fit", dto.getId(), HttpUtil.baseEncoder(dto.getTitle()), "PDP", DateUtil.getYMDDateString(new Date()),
				wechatUserDTO.getNickName(), sexString);
		// log for markting end

		ActivityParticipationDTO result = activityParticipationService.getByUidAndActivityId(activityId, wechatUserId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
	}

}
