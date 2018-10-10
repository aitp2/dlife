package com.aitp.dlife.web.rest;

import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.service.FollowService;
import com.aitp.dlife.service.TaskEngineService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.FollowDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
 * REST controller for managing Follow.
 */
@RestController
@RequestMapping("/api")
@Api(value = "用户关注API", tags = "用户关注API")
public class FollowResource {

    private final Logger log = LoggerFactory.getLogger(FollowResource.class);

    private static final String ENTITY_NAME = "follow";

    private final FollowService followService;

    private final WechatUserService wechatUserService;
    
    private final TaskEngineService taskEngineService;

    public FollowResource(FollowService followService, WechatUserService wechatUserService,TaskEngineService taskEngineService) {
        this.followService = followService;
        this.wechatUserService = wechatUserService;
        this.taskEngineService = taskEngineService;
    }

    /**
     * POST  /follows : Create a new follow.
     *
     * @param followDTO the followDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new followDTO, or with status 400 (Bad Request) if the follow has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/follows")
    @ApiOperation(value = "关注用户", response = FollowDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "json", defaultValue = "", name = "followDTO", value = "关注用户的内容", required = true) })
    @Timed
    public ResponseEntity<FollowDTO> createFollow(@Valid @RequestBody FollowDTO followDTO) throws URISyntaxException {
        log.debug("REST request to save Follow : {}", followDTO);
        if (followDTO.getId() != null) {
            throw new BadRequestAlertException("A new follow cannot already have an ID", ENTITY_NAME, "idexists");
        }

        //set follow user message
        if (StringUtils.isEmpty(followDTO.getFollowUserId()))
        {
            throw new BadRequestAlertException("The request must have the follow user id", ENTITY_NAME, "noFollowUserId");
        }
        try {
            Long.valueOf(followDTO.getFollowUserId());
        } catch (NumberFormatException e) {
            throw new BadRequestAlertException("The request wechat user id must be number type", ENTITY_NAME, "notNumberType");
        }

        //set followed user message
        if (StringUtils.isEmpty(followDTO.getFollowedUserId()))
        {
            throw new BadRequestAlertException("The request must have the followed user id", ENTITY_NAME, "noFollowedUserId");
        }
        try {
            Long.valueOf(followDTO.getFollowedUserId());
        } catch (NumberFormatException e) {
            throw new BadRequestAlertException("The request wechat user id must be number type", ENTITY_NAME, "notNumberType");
        }

        FollowDTO result = followService.createFollow(followDTO);
        
		taskEngineService.saveNewEvent(followDTO.getFollowUserId(), "关注他人", PointEventType.FOCUS, CommentChannel.PLATFORM.toString(),"");
		taskEngineService.saveNewEvent(followDTO.getFollowedUserId(), "新增粉丝", PointEventType.NEWFUN, CommentChannel.PLATFORM.toString(),"");
        return ResponseEntity.created(new URI("/api/follows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /follows : Updates an existing follow.
     *
     * @param followDTO the followDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated followDTO,
     * or with status 400 (Bad Request) if the followDTO is not valid,
     * or with status 500 (Internal Server Error) if the followDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/follows")
    @ApiOperation(value = "更新关注信息", response = FollowDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "json", defaultValue = "", name = "followDTO", value = "关注用户的内容", required = true) })
    @Timed
    public ResponseEntity<FollowDTO> updateFollow(@Valid @RequestBody FollowDTO followDTO) throws URISyntaxException {
        log.debug("REST request to update Follow : {}", followDTO);
        if (followDTO.getId() == null) {
            return createFollow(followDTO);
        }

        //set default messages
        followDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

        FollowDTO result = followService.save(followDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, followDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /follows : get all the follows.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of follows in body
     */
    @GetMapping("/follows")
    @ApiOperation(value = "获取关注信息", response = FollowDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "pageable", value = "分页信息", required = false),
        @ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "followWechatUserId", value = "用于查询我关注的人的信息", required = false),
        @ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "followedWechatUserId", value = "用于查询我的粉丝的信息", required = false)})
    @Timed
    public ResponseEntity<List<FollowDTO>> getAllFollows(Pageable pageable,
    		@RequestParam(value = "followWechatUserId", required = false) String followWechatUserId,
    		@RequestParam(value = "followedWechatUserId", required = false) String followedWechatUserId) {
        log.debug("REST request to get a page of Follows");
        Page<FollowDTO> page = null;
        if(!StringUtils.isEmpty(followWechatUserId)) {
        	page = followService.findAllByFollowUserId(pageable, followWechatUserId);
        }else if(!StringUtils.isEmpty(followedWechatUserId)) {
        	page =  followService.findAllByFollowedUserId(pageable, followedWechatUserId);
        }else {
        	page = followService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/follows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /follows/:id : get the "id" follow.
     *
     * @param id the id of the followDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the followDTO, or with status 404 (Not Found)
     */
    @GetMapping("/follows/{id}")
    @ApiOperation(value = "获得具体的关注信息", response = FollowDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "id", value = "对象的ID", required = true) })
    @Timed
    public ResponseEntity<FollowDTO> getFollow(@PathVariable Long id) {
        log.debug("REST request to get Follow : {}", id);
        FollowDTO followDTO = followService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(followDTO));
    }

    /**
     * DELETE  /follows/:id : delete the "id" follow.
     *
     * @param id the id of the followDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/follows/{id}")
    @ApiOperation(value = "删除关注信息", response = FollowDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "id", value = "对象的ID", required = true) })
    @Timed
    public ResponseEntity<Void> deleteFollow(@PathVariable Long id) {
        log.debug("REST request to delete Follow : {}", id);
        followService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
