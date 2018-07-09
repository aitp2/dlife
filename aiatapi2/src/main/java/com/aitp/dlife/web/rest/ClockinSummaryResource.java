package com.aitp.dlife.web.rest;

import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.ClockinSummaryService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.ClockinSummaryDTO;
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
 * REST controller for managing ClockinSummary.
 */
@RestController
@RequestMapping("/api")
public class ClockinSummaryResource {

    private final Logger log = LoggerFactory.getLogger(ClockinSummaryResource.class);

    private static final String ENTITY_NAME = "clockinSummary";

    private final ClockinSummaryService clockinSummaryService;

    private final WechatUserService wechatUserService;

    public ClockinSummaryResource(ClockinSummaryService clockinSummaryService,WechatUserService wechatUserService) {
        this.clockinSummaryService = clockinSummaryService;
        this.wechatUserService=wechatUserService;
    }

    /**
     * POST  /clockin-summaries : Create a new clockinSummary.
     *
     * @param clockinSummaryDTO the clockinSummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clockinSummaryDTO, or with status 400 (Bad Request) if the clockinSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clockin-summaries")
    @Timed
    public ResponseEntity<ClockinSummaryDTO> createClockinSummary(@Valid @RequestBody ClockinSummaryDTO clockinSummaryDTO) throws URISyntaxException {
        log.debug("REST request to save ClockinSummary : {}", clockinSummaryDTO);
        if (clockinSummaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new clockinSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClockinSummaryDTO result = clockinSummaryService.save(clockinSummaryDTO);
        return ResponseEntity.created(new URI("/api/clockin-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clockin-summaries : Updates an existing clockinSummary.
     *
     * @param clockinSummaryDTO the clockinSummaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clockinSummaryDTO,
     * or with status 400 (Bad Request) if the clockinSummaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the clockinSummaryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clockin-summaries")
    @Timed
    public ResponseEntity<ClockinSummaryDTO> updateClockinSummary(@Valid @RequestBody ClockinSummaryDTO clockinSummaryDTO) throws URISyntaxException {
        log.debug("REST request to update ClockinSummary : {}", clockinSummaryDTO);
        if (clockinSummaryDTO.getId() == null) {
            return createClockinSummary(clockinSummaryDTO);
        }
        ClockinSummaryDTO result = clockinSummaryService.save(clockinSummaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clockinSummaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clockin-summaries : get all the clockinSummaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clockinSummaries in body
     */
    @GetMapping("/clockin-summaries")
    @Timed
    public ResponseEntity<List<ClockinSummaryDTO>> getAllClockinSummaries(Pageable pageable) {
        log.debug("REST request to get a page of ClockinSummaries");
        Page<ClockinSummaryDTO> page = clockinSummaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clockin-summaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clockin-summaries/:id : get the "id" clockinSummary.
     *
     * @param id the id of the clockinSummaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clockinSummaryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clockin-summaries/{id}")
    @Timed
    public ResponseEntity<ClockinSummaryDTO> getClockinSummary(@PathVariable Long id) {
        log.debug("REST request to get ClockinSummary : {}", id);
        ClockinSummaryDTO clockinSummaryDTO = clockinSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clockinSummaryDTO));
    }

    /**
     * DELETE  /clockin-summaries/:id : delete the "id" clockinSummary.
     *
     * @param id the id of the clockinSummaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clockin-summaries/{id}")
    @Timed
    public ResponseEntity<Void> deleteClockinSummary(@PathVariable Long id) {
        log.debug("REST request to delete ClockinSummary : {}", id);
        clockinSummaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /clockin-summaries : GET a clockinSummary.
     *
     * @param wechatUserId the clockinSummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clockinSummaryDTO, or with status 400 (Bad Request) if the clockinSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @GetMapping("/clockin-summaries/getByWechatUserId")
    @Timed
    public ClockinSummaryDTO getClockinSummary(String wechatUserId) {
        log.debug("REST request to get ClockinSummary by wechatUserId: {}", wechatUserId);

        //log for markting start
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(Long.valueOf(wechatUserId));
        String sexString="";
        if (null!=wechatUserDTO && null!=wechatUserDTO.isSex()){
            boolean sex = wechatUserDTO.isSex();
            if (sex) {
                sexString = "male";
            }else{
                sexString = "female";
            }
        }
        log.debug("module:{},moduleEntryId:{},moduleEntryTitle:{},operator:{},operatorTime:{},nickname:{},sex:{}","fit","","","login",DateUtil.getYMDDateString(new Date()),wechatUserDTO.getNickName(),sexString);
        //log for markting end

        if (null == wechatUserId) {
            throw new BadRequestAlertException("wechatUserId is null", ENTITY_NAME, "wechatUserIdNULL");
        }
        return clockinSummaryService.findByWechatUserId(wechatUserId);
    }
}
