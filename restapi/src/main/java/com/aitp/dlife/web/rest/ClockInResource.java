package com.aitp.dlife.web.rest;

import com.aitp.dlife.service.*;
import com.aitp.dlife.service.dto.*;
import com.aitp.dlife.web.rest.util.HttpUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.Instant;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing ClockIn.
 */
@RestController
@RequestMapping("/api")
public class ClockInResource {

    private final Logger log = LoggerFactory.getLogger(ClockInResource.class);

    private static final String ENTITY_NAME = "clockIn";

    private final ClockInService clockInService;
    private final PicsService picsService;
    private final ClockinSummaryService clockinSummaryService;
    private final WechatUserService wechatUserService;
    private final FitnessActivityService fitnessActivityService;
    private final ActivityParticipationService activityParticipationService;

	public ClockInResource(ClockInService clockInService, PicsService picsService,
			ClockinSummaryService clockinSummaryService,WechatUserService wechatUserService,FitnessActivityService fitnessActivityService,ActivityParticipationService activityParticipationService) {
		this.clockInService = clockInService;
		this.picsService = picsService;
		this.clockinSummaryService = clockinSummaryService;
		this.wechatUserService=wechatUserService;
		this.fitnessActivityService=fitnessActivityService;
		this.activityParticipationService=activityParticipationService;
	}

    /**
     * POST  /clock-ins : Create a new clockIn.
     *
     * @param clockInDTO the clockInDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clockInDTO, or with status 400 (Bad Request) if the clockIn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clock-ins")
    @Timed
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


        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(clockInDTO.getWechatUserId()));
        String sexString="";
        if (null!=wechatUserDTO && null!=wechatUserDTO.isSex()){
            boolean sex = wechatUserDTO.isSex();
            if (sex) {
                sexString = "male";
            }else{
                sexString = "female";
            }
        }
        ActivityParticipationDTO participationDTO = activityParticipationService.findOne(clockInDTO.getActivityParticipationId());
        FitnessActivityDTO dto = fitnessActivityService.findOne(participationDTO.getActivityId());
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","fit",dto.getId(),HttpUtil.baseEncoder(dto.getTitle()),"clock-in",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end


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
    public ResponseEntity<List<ClockInDTO>> getAllClockIns(Pageable pageable) {
        log.debug("REST request to get a page of ClockIns");
        Page<ClockInDTO> page = clockInService.findAll(pageable);
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
    public ResponseEntity<Void> deleteClockIn(@PathVariable Long id) {
        log.debug("REST request to delete ClockIn : {}", id);
        clockInService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/clock-ins/getClockinsByActivityParticipationId")
    @Timed
    public List<ClockInDTO>  getClockinsByActivityParticipationId(Long activityParticipationId) {
        log.debug("REST request to get Clockins : {}", activityParticipationId);
        return clockInService.findClockinsByActivityParticipationId(activityParticipationId);
    }

    @GetMapping("/clock-ins/getClockinsDateByWechatUserIdAndMonth")
    @Timed
    public List<String>  getClockinsDateByWechatUserIdAndMonth(String wechatUserId,String yearMonth) {
        log.debug("REST request to get Clockins : {}", wechatUserId,yearMonth);
        return clockInService.getClockinsDateByWechatUserIdAndMonth(wechatUserId,yearMonth);
    }

    @GetMapping("/clock-ins/getClockinsByWechatUserIdAndDate")
    @Timed
    public List<ClockInDTO>  getClockinsByWechatUserIdAndDate(String wechatUserId,String yearMonthDate) {
        log.debug("REST request to get Clockins : {}", wechatUserId,yearMonthDate);
        return clockInService.getClockinsByWechatUserIdAndDate(wechatUserId,yearMonthDate);
    }

    @GetMapping("/clock-ins/getClockinsByActivityId")
    @Timed
    public List<ClockInDTO>  getClockinsByActivityId(String activityId) {
        log.debug("REST request to get Clockins by activity id: {}", activityId);
        return clockInService.getClockinsByActivityId(activityId);
    }
}
