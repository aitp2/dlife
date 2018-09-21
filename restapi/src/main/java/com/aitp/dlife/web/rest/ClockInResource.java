package com.aitp.dlife.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.domain.enumeration.ThumbsUpModule;
import com.aitp.dlife.repository.specification.ClockInSpecification;
import com.aitp.dlife.repository.specification.ThumbsUpSpecification;
import com.aitp.dlife.service.ActivityParticipationService;
import com.aitp.dlife.service.ClockInActivityService;
import com.aitp.dlife.service.ClockInService;
import com.aitp.dlife.service.ClockinSummaryService;
import com.aitp.dlife.service.EventMessageService;
import com.aitp.dlife.service.FitnessActivityService;
import com.aitp.dlife.service.PicsService;
import com.aitp.dlife.service.ThumbsUpService;
import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.service.dto.ClockInDTO;
import com.aitp.dlife.service.dto.ClockinSummaryDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.PicsDTO;
import com.aitp.dlife.service.dto.ThumbsUpDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.HttpUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing ClockIn.
 */
@RestController
@RequestMapping("/api")
@Api(value = "小目标打卡API", tags = "小目标打卡API")
public class ClockInResource {

    private final Logger log = LoggerFactory.getLogger(ClockInResource.class);

    private static final String ENTITY_NAME = "clockIn";

    private final ClockInService clockInService;
    private final PicsService picsService;
    private final ClockinSummaryService clockinSummaryService;
    private final WechatUserService wechatUserService;
	private final ThumbsUpService thumbsUpService;
    private final FitnessActivityService fitnessActivityService;
    private final ActivityParticipationService activityParticipationService;
    private final EventMessageService eventMessageService;
    private final ClockInActivityService clockInActivityService;

	public ClockInResource(ClockInService clockInService, PicsService picsService,
                           ClockinSummaryService clockinSummaryService, WechatUserService wechatUserService, FitnessActivityService fitnessActivityService, ActivityParticipationService activityParticipationService,
                           EventMessageService eventMessageService, ClockInActivityService clockInActivityService,ThumbsUpService thumbsUpService) {
		this.clockInService = clockInService;
		this.picsService = picsService;
		this.clockinSummaryService = clockinSummaryService;
		this.wechatUserService=wechatUserService;
		this.fitnessActivityService=fitnessActivityService;
		this.activityParticipationService=activityParticipationService;
        this.eventMessageService = eventMessageService;
        this.clockInActivityService = clockInActivityService;
        this.thumbsUpService = thumbsUpService;
    }

    /**
     * POST  /clock-ins : Create a new clockIn.
     *
     * @param clockInDTO the clockInDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clockInDTO, or with status 400 (Bad Request) if the clockIn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clock-ins")
    @Deprecated
    @Timed
    @ApiOperation(value = "创建打卡", notes = "根据ClockDTO传入打卡信息", response = ClockInDTO.class)
    public ResponseEntity<ClockInDTO> createClockIn(@Valid @RequestBody ClockInDTO clockInDTO) throws URISyntaxException {
        log.debug("REST request to save ClockIn : {}", clockInDTO);
        if (clockInDTO.getId() != null) {
            throw new BadRequestAlertException("A new clockIn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(clockInService.isClockIn(clockInDTO.getWechatUserId(),clockInDTO.getActivityParticipationId()))
        {
            throw new BadRequestAlertException("A new clockIn cannot already have clockin today",ENTITY_NAME,"请不要重复打卡哦");
        }
        clockInDTO.setPunchDateTime(DateUtil.getYMDDateString(new Date()));
        Set<PicsDTO> imagesDTO = new HashSet<>();
        ClockInDTO result = clockInService.save(clockInDTO);
        if (clockInDTO.getPics() != null && !clockInDTO.getPics().isEmpty()){
        	for(PicsDTO pics : clockInDTO.getPics()){
                     pics.setCreateTime(DateUtil.getYMDDateString(new Date()));
        		 pics.setClockInId(result.getId());
        		 imagesDTO.add(picsService.save(pics));
        	}
        }
        result.setPics(imagesDTO);
        //update clockin summary
        ClockinSummaryDTO clockinSummaryDTO = clockinSummaryService.findByWechatUserId(clockInDTO.getWechatUserId());
        if(null == clockinSummaryDTO ){
        	ClockinSummaryDTO newClockinSummaryDTO = new ClockinSummaryDTO();
        	newClockinSummaryDTO.setLastClockInTime(DateUtil.getYMDDateString(new Date()));
        	newClockinSummaryDTO.setSerialCount(1);
        	newClockinSummaryDTO.setWeeklyCount(1);
        	newClockinSummaryDTO.setTotallyCount(1);
        	newClockinSummaryDTO.setWechatUserId(String.valueOf(clockInDTO.getWechatUserId()));
        	clockinSummaryService.save(newClockinSummaryDTO);
		} else if (!DateUtil.isToday(DateUtil.fromYDMStringDate(clockinSummaryDTO.getLastClockInTime()))) {
			ClockinSummaryDTO newClockinSummaryDTO = new ClockinSummaryDTO();
			newClockinSummaryDTO.setId(clockinSummaryDTO.getId());
			newClockinSummaryDTO.setSerialCount(
					DateUtil.isYesterday(DateUtil.fromYDMStringDate(clockinSummaryDTO.getLastClockInTime()))
							? clockinSummaryDTO.getSerialCount() + 1 : 1);
			newClockinSummaryDTO.setWeeklyCount(DateUtil.isThisWeek(clockinSummaryDTO.getLastClockInTime())
					? clockinSummaryDTO.getWeeklyCount() + 1 : 1);
			newClockinSummaryDTO.setTotallyCount(clockinSummaryDTO.getTotallyCount() + 1);
			newClockinSummaryDTO.setWechatUserId(String.valueOf(clockInDTO.getWechatUserId()));
			newClockinSummaryDTO.setLastClockInTime(DateUtil.getYMDDateString(new Date()));
			clockinSummaryService.save(newClockinSummaryDTO);
		}

        //冗余活动打卡信息
        clockInActivityService.updateActivityParticipation(clockInDTO.getActivityParticipationId());

        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(clockInDTO.getWechatUserId()));
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
        ActivityParticipationDTO participationDTO = activityParticipationService.findOne(clockInDTO.getActivityParticipationId()).get();
        FitnessActivityDTO dto = fitnessActivityService.findOne(participationDTO.getActivityId());
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","fit",dto.getId(),HttpUtil.baseEncoder(dto.getTitle()),"clock-in",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end

        //record the activity participation event start
        eventMessageService.recordEventMessage(EventChannel.FITNESS,DateUtil.getYMDDateString(new Date()), EventType.CLOCKIN,
            result.getWechatUserId(),participationDTO.getActivityTitle(),participationDTO.getActivityId(),wechatUserDTO.getAvatar(),wechatUserDTO.getNickName());
        //record the activity participation event end

        return ResponseEntity.created(new URI("/api/clock-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clock-ins : Updates an existing clockIn.
     *
     * @param clockInDTO the clockInDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clockInDTO,
     * or with status 400 (Bad Request) if the clockInDTO is not valid,
     * or with status 500 (Internal Server Error) if the clockInDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clock-ins")
    @Timed
    @ApiOperation(value = "更新打卡方法", notes = "根据ClockDTO传入打卡信息", response = ClockInDTO.class)
    public ResponseEntity<ClockInDTO> updateClockIn(@Valid @RequestBody ClockInDTO clockInDTO) throws URISyntaxException {
        log.debug("REST request to update ClockIn : {}", clockInDTO);
        if (clockInDTO.getId() == null) {
            return createClockIn(clockInDTO);
        }
        ClockInDTO result = clockInService.save(clockInDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clockInDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clock-ins : get all the clockIns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clockIns in body
     */
    @GetMapping("/clock-ins")
    @Timed
    @ApiOperation(value = "打卡信息列表查询方法", notes = "根据不同传入参数获取打卡信息，具有分页功能，查询条件可传可不传", response = ClockInDTO.class)
    public ResponseEntity<List<ClockInDTO>> getAllClockIns(Pageable pageable,ClockInSpecification spec) {
        log.debug("REST request to get a page of ClockIns");
        Page<ClockInDTO> page = clockInService.findAll(pageable,spec);
    	ThumbsUpSpecification thumbsUpSpecification  = new ThumbsUpSpecification(spec.getQuerys().getActivityId(),ThumbsUpModule.ACTIVITY);
		List<ThumbsUpDTO> thumbsUpDTOs = thumbsUpService.findAll(thumbsUpSpecification);
		page.getContent().parallelStream().forEach(clockin -> clockin.setThumbsUpDTOs(thumbsUpDTOs.stream()
				.filter(thb -> thb.getObjectId().equals(clockin.getId())&&ThumbsUpModule.ACTIVITY.equals(thb.getModule())).collect(Collectors.toSet())));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clock-ins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clock-ins : get all the clockIns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clockIns in body
     */
    @GetMapping("/all-clock-ins")
    @Timed
    @ApiOperation(value = "小目标首页全部动态查询方法", notes = "根据开始时间和结束时间获取全部动态信息，具有分页功能，查询条件可传可不传，比如需要查询今天往前14天的动态，endTime传今天的日期，startTime传14天前的日期，排序字段可以传打卡时间：punchDateTime,desc", response = ClockInDTO.class)
    public ResponseEntity<List<ClockInDTO>> getNewClockIns(Pageable pageable,ClockInSpecification spec) {
        log.debug("REST request to get a page of ClockIns");
        Page<ClockInDTO> page = clockInService.findAll(pageable,spec);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clock-ins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clock-ins/:id : get the "id" clockIn.
     *
     * @param id the id of the clockInDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clockInDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clock-ins/{id}")
    @Timed
    @ApiOperation(value = "获取打卡信息方法", notes = "根据打卡ID获取打卡信息", response = ClockInDTO.class)
    public ResponseEntity<ClockInDTO> getClockIn(@PathVariable Long id) {
        log.debug("REST request to get ClockIn : {}", id);
        ClockInDTO clockInDTO = clockInService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clockInDTO));
    }

    /**
     * DELETE  /clock-ins/:id : delete the "id" clockIn.
     *
     * @param id the id of the clockInDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clock-ins/{id}")
    @Timed
    @ApiOperation(value = "删除打卡信息方法", notes = "根据打卡ID删除打卡信息, 正确返回ok")
    public ResponseEntity<String> deleteClockIn(@PathVariable Long id) {
        log.debug("REST request to delete ClockIn : {}", id);
        clockInService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).body("ok");
    }

    @GetMapping("/clock-ins/getClockinsByActivityParticipationId")
    @Timed
    @Deprecated
    @ApiOperation(value = "根据报名信息查询打卡信息", notes = "已过时", response = ClockInDTO.class)
    public List<ClockInDTO>  getClockinsByActivityParticipationId(Long activityParticipationId) {
        log.debug("REST request to get Clockins : {}", activityParticipationId);
        return clockInService.findClockinsByActivityParticipationId(activityParticipationId);
    }

    @GetMapping("/clock-ins/getClockinsDateByWechatUserIdAndMonth")
    @Timed
    @Deprecated
    @ApiOperation(value = "获取根据用户和月份获取打卡信息", notes = "已过时", response = ClockInDTO.class)
    public List<String>  getClockinsDateByWechatUserIdAndMonth(String wechatUserId,String yearMonth) {
        log.debug("REST request to get Clockins : {}", wechatUserId,yearMonth);
        return clockInService.getClockinsDateByWechatUserIdAndMonth(wechatUserId,yearMonth);
    }

    @GetMapping("/clock-ins/getClockinsByWechatUserIdAndDate")
    @Timed
    @Deprecated
    @ApiOperation(value = "获取根据用户和日期获取打卡信息", notes = "已过时", response = ClockInDTO.class)
    public List<ClockInDTO>  getClockinsByWechatUserIdAndDate(String wechatUserId,String yearMonthDate) {
        log.debug("REST request to get Clockins : {}", wechatUserId,yearMonthDate);
        return clockInService.getClockinsByWechatUserIdAndDate(wechatUserId,yearMonthDate);
    }

    @GetMapping("/clock-ins/getClockinsByActivityId")
    @Timed
    @Deprecated
    @ApiOperation(value = "获取根活动获取打卡信息", notes = "已过时", response = ClockInDTO.class)
    public List<ClockInDTO>  getClockinsByActivityId(String activityId) {
        log.debug("REST request to get Clockins by activity id: {}", activityId);
        return clockInService.getClockinsByActivityId(activityId);
    }
}
