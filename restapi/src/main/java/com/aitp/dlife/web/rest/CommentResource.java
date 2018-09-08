package com.aitp.dlife.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.domain.PinFanActivity;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.domain.enumeration.ThumbsUpModule;
import com.aitp.dlife.repository.FitnessActivityRepository;
import com.aitp.dlife.repository.specification.CommentSpecification;
import com.aitp.dlife.repository.specification.ReplySpecification;
import com.aitp.dlife.repository.specification.ThumbsUpSpecification;
import com.aitp.dlife.service.ClockInService;
import com.aitp.dlife.service.CommentPicService;
import com.aitp.dlife.service.CommentService;
import com.aitp.dlife.service.EventMessageService;
import com.aitp.dlife.service.FitnessActivityService;
import com.aitp.dlife.service.MessageService;
import com.aitp.dlife.service.PinFanActivityService;
import com.aitp.dlife.service.QuestionService;
import com.aitp.dlife.service.ThumbsUpService;
import com.aitp.dlife.service.builder.EventMessageBuilder;
import com.aitp.dlife.service.dto.ClockInDTO;
import com.aitp.dlife.service.dto.CommentDTO;
import com.aitp.dlife.service.dto.CommentPicDTO;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.QuestionDTO;
import com.aitp.dlife.service.dto.ReplyDTO;
import com.aitp.dlife.service.dto.ThumbsUpDTO;
import com.aitp.dlife.service.mapper.InstantMapper;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing Comment.
 */
@Api(value = "评论和小问答回答API", tags = "评论和小问答回答API")
@RestController
@RequestMapping("/api")
public class CommentResource {

	private final Logger log = LoggerFactory.getLogger(CommentResource.class);

	private static final String ENTITY_NAME = "comment";

	private final CommentService commentService;

	private final CommentPicService commentPicService;

	private final FitnessActivityRepository fitnessActivityRepository;

	private final ThumbsUpService thumbsUpService;

	private final ClockInService clockInService;
	
	private final EventMessageService eventMessageService;

	private final FitnessActivityService fitnessActivityService;
	
	private final PinFanActivityService pinFanActivityService;

	private final MessageService messageService;

	private final QuestionService questionService;

	public CommentResource(CommentService commentService, CommentPicService commentPicService,
			FitnessActivityRepository fitnessActivityRepository, EventMessageService eventMessageService,
			PinFanActivityService pinFanActivityService, ThumbsUpService thumbsUpService,
			QuestionService questionService, MessageService messageService,ClockInService clockInService,FitnessActivityService fitnessActivityService) {
		this.commentService = commentService;
		this.commentPicService = commentPicService;
		this.fitnessActivityRepository = fitnessActivityRepository;
		this.eventMessageService = eventMessageService;
		this.thumbsUpService = thumbsUpService;
		this.pinFanActivityService = pinFanActivityService;
		this.questionService = questionService;
		this.messageService = messageService;
		this.clockInService = clockInService;
		this.fitnessActivityService = fitnessActivityService;
	}

	/**
	 * POST /comments : Create a new comment.
	 *
	 * @param commentDTO
	 *            the commentDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new commentDTO, or with status 400 (Bad Request) if the comment
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/comments")
	@ApiOperation(value = "创建评论，创建小问答的回答", response = CommentDTO.class, produces = "application/json")
	@Timed
	public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO)
			throws URISyntaxException {
		log.debug("REST request to save Comment : {}", commentDTO);
		if (commentDTO.getId() != null) {
			throw new BadRequestAlertException("A new comment cannot already have an ID", ENTITY_NAME, "idexists");
		}

		commentDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));
		commentDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));

		CommentDTO result = commentService.save(commentDTO);


		Set<CommentPicDTO> picDTOS = new HashSet<>();
		if (commentDTO.getCommentPics() != null && !commentDTO.getCommentPics().isEmpty()) {

			for (CommentPicDTO pics : commentDTO.getCommentPics()) {
				if (!StringUtils.isEmpty(pics.getCreateTime())) {
					pics.setCreateTime(DateUtil.getYMDDateString(new Date()));
				}
				pics.setCommentId(result.getId());
				picDTOS.add(commentPicService.save(pics));
			}
			result.setCommentPics(picDTOS);
		}
		updateDateTime(commentDTO,EventMessageBuilder.buildEventMessageDTO(result).type(EventType.COMMENT).get());
		return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	

	/**
	 * POST /comments : Create a new comment.
	 *
	 * @param commentDTO
	 *            the commentDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new commentDTO, or with status 400 (Bad Request) if the comment
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/reply")
	@ApiOperation(value = "创建回复", response = CommentDTO.class, produces = "application/json")
	@Timed
	public ResponseEntity<ReplyDTO> createReply(@Valid @RequestBody ReplyDTO replyDTO)
			throws URISyntaxException {
		log.debug("REST request to save Comment : {}", replyDTO);
		ReplyDTO result = commentService.save(replyDTO);
		switch (replyDTO.getModule()) {
		case COMMENT:
			CommentDTO commentDTO = commentService.findOne(replyDTO.getParentId());
			updateDateTime(commentDTO,EventMessageBuilder.buildEventMessageDTO(result).type(EventType.REPLY).object(commentDTO.getObjectId()).get());
			break;
		case ACTIVITY:
			ClockInDTO clockInDTO = clockInService.findOne(replyDTO.getParentId());
			FitnessActivityDTO fitnessActivityDTO  = fitnessActivityService.findOne(Long.valueOf((long)clockInDTO.getActivityId().intValue()));
			EventMessageDTO eventMessageDTO = EventMessageBuilder.buildEventMessageDTO(result)
					.type(EventType.REPLY).
					object(Long.valueOf((long)clockInDTO.
							getActivityId().
							intValue())).title(fitnessActivityDTO.getTitle()).Channel(EventChannel.FITNESS).get();
			eventMessageDTO = eventMessageService.save(eventMessageDTO);
			if (null != eventMessageDTO.getId()) {
				messageService.createMessageForEvent(eventMessageDTO);
			}
			break;
		default:
			break;
		}
		return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}
	
	

	/**
	 * POST /comments : Create a new comment.
	 *
	 * @param commentDTO
	 *            the commentDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new commentDTO, or with status 400 (Bad Request) if the comment
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@GetMapping("/replys")
	@ApiOperation(value = "回复列表查询功能", response = ReplyDTO.class, produces = "application/json")
	@Timed
	public ResponseEntity<List<ReplyDTO>> getReplay(Pageable pageable, ReplySpecification spec)
			 {
		log.debug("REST request to get a page of Comments");
		Page<ReplyDTO> page = commentService.findAll(pageable, spec);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
	
	
	
	
	private void updateDateTime(CommentDTO commentDTO,EventMessageDTO eventMessageDTO){
		EventChannel eventChannel = null;
		String objectTitle = null;
		if (CommentChannel.FIT.equals(commentDTO.getChannel())) {
			FitnessActivity fitnessActivity = fitnessActivityRepository.findById(commentDTO.getObjectId()).get();
			fitnessActivity.setModifyTime(Instant.now());
			fitnessActivityRepository.save(fitnessActivity);
			eventChannel = EventChannel.FITNESS;
			objectTitle = fitnessActivity.getTitle();
		} else if (CommentChannel.PIN.equals(commentDTO.getChannel())) {
			PinFanActivity pinFanActivity = pinFanActivityService.updateModifyTime(commentDTO.getObjectId());
			if (null != pinFanActivity) {
				eventChannel = EventChannel.PINFAN;
				objectTitle = pinFanActivity.getActivitiyTile();
			}
		} else if (CommentChannel.FAQS.equals(commentDTO.getChannel())) {
			Optional<QuestionDTO> questionDTO = questionService.findOne(commentDTO.getObjectId());
			if (questionDTO.isPresent()) {
				QuestionDTO dto = questionDTO.get();
				eventChannel = EventChannel.FAQS;
				objectTitle = dto.getTitle();
				questionDTO.get().setModifyTime(InstantMapper.toDateString(Instant.now()));
				questionService.save(questionDTO.get());
			}
			
		}
		if (eventChannel != null) {
			eventMessageDTO.setObjectTitle(objectTitle);
			eventMessageDTO.setChannel(eventChannel);
			eventMessageDTO = eventMessageService.save(eventMessageDTO);
			// record the activity participation event start
			if (null != eventMessageDTO.getId()) {
				messageService.createMessageForEvent(eventMessageDTO);
			}
			// record the activity participation event end
		}
	}
	/**
	 * PUT /comments : Updates an existing comment.
	 *
	 * @param commentDTO
	 *            the commentDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         commentDTO, or with status 400 (Bad Request) if the commentDTO is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         commentDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/comments")
	@ApiOperation(value = "更新评论", response = CommentDTO.class, produces = "application/json")
	@Timed
	public ResponseEntity<CommentDTO> updateComment(@Valid @RequestBody CommentDTO commentDTO)
			throws URISyntaxException {
		log.debug("REST request to update Comment : {}", commentDTO);
		if (commentDTO.getId() == null) {
			return createComment(commentDTO);
		}
		CommentDTO result = commentService.save(commentDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentDTO.getId().toString())).body(result);
	}

	/**
	 * GET /comments : get all the comments.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of comments
	 *         in body
	 */
	@GetMapping("/comments")
	@Timed
	@ApiOperation(value = "获取评论信息列表", notes = "根据不同条件进行分页查查询和排序", response = CommentDTO.class)
	public ResponseEntity<List<CommentDTO>> getAllComments(Pageable pageable, CommentSpecification spec) {
		log.debug("REST request to get a page of Comments");
		Page<CommentDTO> page = commentService.findAll(pageable, spec);
		ThumbsUpSpecification thumbsUpSpecification  = new ThumbsUpSpecification(spec.getQuerys().getObjectId(),ThumbsUpModule.COMMENT);
		List<ThumbsUpDTO> thumbsUpDTOs = thumbsUpService.findAll(thumbsUpSpecification);
		page.getContent().parallelStream().forEach(comment -> comment.setThumbsUpDTOs(thumbsUpDTOs.stream()
				.filter(thb -> thb.getObjectId().equals(comment.getId())&&ThumbsUpModule.COMMENT.equals(thb.getModule())).collect(Collectors.toSet())));
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /comments : get all the comments.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of comments
	 *         in body
	 */
	@GetMapping("/comments/{channel}/{objectId}")
	@Deprecated
	@ApiOperation(value = "获得应用和对象的评论信息", response = CommentDTO.class, produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "channel", value = "应用的code", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "objectId", value = "对象的ID", required = true) })
	@Timed
	public ResponseEntity<List<CommentDTO>> getAllCommentsForOneObject(Pageable pageable, @PathVariable String channel,
			@PathVariable String objectId) {
		log.debug("REST request to get a page of Comments");
		Page<CommentDTO> page = commentService.findAllForOneObject(pageable, channel, objectId);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments");

		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /comments/:id : get the "id" comment.
	 *
	 * @param id
	 *            the id of the commentDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         commentDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/comments/{id}")
	@ApiOperation(value = "获取评论信息", response = Void.class, produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "id", value = "评论的ID", required = true) })
	@Timed
	public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
		log.debug("REST request to get Comment : {}", id);
		CommentDTO commentDTO = commentService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentDTO));
	}

	/**
	 * DELETE /comments/:id : delete the "id" comment.
	 *
	 * @param id
	 *            the id of the commentDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/comments/{id}")
	@ApiOperation(value = "删除评论信息", response = Void.class, produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "String", defaultValue = "", name = "id", value = "评论的ID", required = true) })
	@Timed
	public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
		log.debug("REST request to delete Comment : {}", id);
		commentService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}
