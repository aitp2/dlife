package com.aitp.dlife.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aitp.dlife.service.MessageService;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.aitp.dlife.service.dto.MessageDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Message.
 */
@Api(value = "消息列表API", tags = "消息列表API")
@RestController
@RequestMapping("/api")
public class MessageResource {

    private final Logger log = LoggerFactory.getLogger(MessageResource.class);

    private static final String ENTITY_NAME = "message";

    private final MessageService messageService;

    public MessageResource(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * POST  /messages : Create a new message.
     *
     * @param messageDTO the messageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageDTO, or with status 400 (Bad Request) if the message has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/messages")
    @ApiOperation(value = "创建消息",notes = "目前无此功能，前端请忽略", response = MessageDTO.class, produces = "application/json")
    @Timed
    public ResponseEntity<MessageDTO> createMessage(@Valid @RequestBody MessageDTO messageDTO) throws URISyntaxException {
        log.debug("REST request to save Message : {}", messageDTO);
        if (messageDTO.getId() != null) {
            throw new BadRequestAlertException("A new message cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageDTO result = messageService.save(messageDTO);
        return ResponseEntity.created(new URI("/api/messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /messages : Updates an existing message.
     *
     * @param messageDTO the messageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageDTO,
     * or with status 400 (Bad Request) if the messageDTO is not valid,
     * or with status 500 (Internal Server Error) if the messageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/messages")
    @ApiOperation(value = "修改消息",notes = "目前无此功能，前端请忽略", response = MessageDTO.class, produces = "application/json")
    @Timed
    public ResponseEntity<MessageDTO> updateMessage(@Valid @RequestBody MessageDTO messageDTO) throws URISyntaxException {
        log.debug("REST request to update Message : {}", messageDTO);
        if (messageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageDTO result = messageService.save(messageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /messages : get all the messages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of messages in body
     */
    @GetMapping("/messages")
    @ApiOperation(value = "查询所有消息列表",notes = "目前无此功能，前端请忽略", response = MessageDTO.class, produces = "application/json")
    @Timed
    public ResponseEntity<List<MessageDTO>> getAllMessages(Pageable pageable) {
        log.debug("REST request to get a page of Messages");
        Page<MessageDTO> page = messageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /messages/:id : get the "id" message.
     *
     * @param id the id of the messageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/messages/{id}")
    @ApiOperation(value = "查询单个消息",notes = "目前无此功能，前端请忽略", response = MessageDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "Long", defaultValue = "", name = "id", value = "消息id", required = true)})
    @Timed
    public ResponseEntity<MessageDTO> getMessage(@PathVariable Long id) {
        log.debug("REST request to get Message : {}", id);
        Optional<MessageDTO> messageDTO = messageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageDTO);
    }

    /**
     * DELETE  /messages/:id : delete the "id" message.
     *
     * @param id the id of the messageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/messages/{id}")
    @ApiOperation(value = "删除单个消息", notes = "目前无此功能，前端请忽略", response = void.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "path", dataType = "Long", defaultValue = "", name = "id", value = "消息id", required = true)})
    @Timed
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        log.debug("REST request to delete Message : {}", id);
        messageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /messages/:type : get the "id" message.
     *
     * @param wechatUserId the id of the messageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/messages/my-message")
    @ApiOperation(value = "我的消息列表", notes = "先获取已读列表，再获取未读列表，当获取未读列表成功后，后台会把未读置为已读；前台根据消息channel和type来唯一区分消息模板", response = MessageDTO.class, produces = "application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", dataType = "String", defaultValue = "", name = "wechatUserId", value = "当前用户wechatUserId", required = true),
        @ApiImplicitParam(paramType = "query", dataType = "String", defaultValue = "0", name = "type", value = "消息类型：0=all,1=comment,2=event,3=like", required = true),
        @ApiImplicitParam(paramType = "query", dataType = "Boolean", defaultValue = "true", name = "read", value = "已读/未读标识", required = true)})
    @Timed
    public ResponseEntity<List<MessageDTO>> getMyMessage(String wechatUserId,String type, Boolean read) {
        if(null==wechatUserId||null==type||null==read){
            throw new BadRequestAlertException("need request param ", ENTITY_NAME, "param is null");
        }
        log.debug("REST request to get Message : {}", wechatUserId);
        List<MessageDTO> messageDTO = messageService.findMessageByUser(wechatUserId,type,read);
        if (!CollectionUtils.isEmpty(messageDTO)){
            List<MessageDTO> toReadDTO = messageDTO;
            messageService.markAsRead(toReadDTO);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageDTO));
    }
}
