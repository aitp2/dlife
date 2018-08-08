package com.aitp.dlife.web.rest;

import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.WechatUserService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.WechatUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing WechatUser.
 */
@RestController
@RequestMapping("/api")
public class WechatUserResource {

    private final Logger log = LoggerFactory.getLogger(WechatUserResource.class);

    private static final String ENTITY_NAME = "wechatUser";

    private final WechatUserService wechatUserService;

    public WechatUserResource(WechatUserService wechatUserService) {
        this.wechatUserService = wechatUserService;
    }

    /**
     * POST  /wechat-users : Create a new wechatUser.
     *
     * @param wechatUserDTO the wechatUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wechatUserDTO, or with status 400 (Bad Request) if the wechatUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wechat-users")
    @Timed
    public ResponseEntity<WechatUserDTO> createWechatUser(@Valid @RequestBody WechatUserDTO wechatUserDTO) throws URISyntaxException {
        log.debug("REST request to save WechatUser : {}", wechatUserDTO);
        if (wechatUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new wechatUser cannot already have an ID", ENTITY_NAME, "idexists");
        }

        //set default messages
        wechatUserDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
        wechatUserDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

        WechatUserDTO result = wechatUserService.save(wechatUserDTO);
        return ResponseEntity.created(new URI("/api/wechat-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wechat-users : Updates an existing wechatUser.
     *
     * @param wechatUserDTO the wechatUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wechatUserDTO,
     * or with status 400 (Bad Request) if the wechatUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the wechatUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wechat-users")
    @Timed
    public ResponseEntity<WechatUserDTO> updateWechatUser(@Valid @RequestBody WechatUserDTO wechatUserDTO) throws URISyntaxException {
        log.debug("REST request to update WechatUser : {}", wechatUserDTO);
        if (wechatUserDTO.getId() == null) {
            return createWechatUser(wechatUserDTO);
        }

        //set default messages
        wechatUserDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

        WechatUserDTO result = wechatUserService.save(wechatUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wechatUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wechat-users : get all the wechatUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wechatUsers in body
     */
    @GetMapping("/wechat-users")
    @Timed
    public ResponseEntity<List<WechatUserDTO>> getAllWechatUsers(Pageable pageable) {
        log.debug("REST request to get a page of WechatUsers");
        Page<WechatUserDTO> page = wechatUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wechat-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wechat-users/:id : get the "id" wechatUser.
     *
     * @param id the id of the wechatUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wechatUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wechat-users/{id}")
    @Timed
    public ResponseEntity<WechatUserDTO> getWechatUser(@PathVariable Long id) {
        log.debug("REST request to get WechatUser : {}", id);
        WechatUserDTO wechatUserDTO = wechatUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wechatUserDTO));
    }

    /**
     * GET  /wechat-users/userinfo/:openid : get the "openid" wechatUser.
     *
     * @param openid
     * @return
     */
    @GetMapping("/wechat-users/userinfo/{openid}")
    @Timed
    public ResponseEntity<WechatUserDTO> getWechatUserByOpenID(@PathVariable String openid) {
        log.debug("REST request to get WechatUser : {}", openid);
        WechatUserDTO wechatUserDTO = wechatUserService.findByOpenId(openid);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wechatUserDTO));
    }

    /**
     * DELETE  /wechat-users/:id : delete the "id" wechatUser.
     *
     * @param id the id of the wechatUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wechat-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteWechatUser(@PathVariable Long id) {
        log.debug("REST request to delete WechatUser : {}", id);
        wechatUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
