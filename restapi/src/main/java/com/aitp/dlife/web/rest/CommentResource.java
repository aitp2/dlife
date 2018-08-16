package com.aitp.dlife.web.rest;

import com.aitp.dlife.domain.PinFanActivity;
import com.aitp.dlife.domain.Question;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.repository.PinFanActivityRepository;
import com.aitp.dlife.repository.specification.CommentSpecification;
import com.aitp.dlife.service.*;
import com.aitp.dlife.service.dto.*;
import com.aitp.dlife.service.dto.builder.EventMessageBuilder;
import com.aitp.dlife.web.rest.util.DateUtil;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.repository.FitnessActivityRepository;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.HeaderUtil;
import com.aitp.dlife.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	private final EventMessageService eventMessageService;

	private final PinFanActivityService pinFanActivityService;

	private final MessageService messageService;

	private final QuestionService questionService;

	public CommentResource(CommentService commentService, CommentPicService commentPicService,
			FitnessActivityRepository fitnessActivityRepository, EventMessageService eventMessageService,
			PinFanActivityService pinFanActivityService, ThumbsUpService thumbsUpService,
			QuestionService questionService, MessageService messageService) {
		this.commentService = commentService;
		this.commentPicService = commentPicService;
		this.fitnessActivityRepository = fitnessActivityRepository;
		this.eventMessageService = eventMessageService;
		this.thumbsUpService = thumbsUpService;
		this.pinFanActivityService = pinFanActivityService;
		this.questionService = questionService;
		this.messageService = messageService;
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
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "body", dataType = "String", defaultValue = "", name = "commentDTO", value = "评论的内容", required = true) })
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

		EventChannel eventChannel = null;
		String objectTitle = null;
		Long objectId = null;
		if (CommentChannel.FIT.equals(commentDTO.getChannel())) {
			FitnessActivity fitnessActivity = fitnessActivityRepository.findById(commentDTO.getObjectId()).get();
			fitnessActivity.setModifyTime(Instant.now());
			fitnessActivityRepository.save(fitnessActivity);

			eventChannel = EventChannel.FITNESS;
			objectTitle = fitnessActivity.getTitle();
			objectId = fitnessActivity.getId();
		} else if (CommentChannel.PIN.equals(commentDTO.getChannel())) {
			PinFanActivityDTO pinFanActivity = pinFanActivityService.findOne(commentDTO.getObjectId());
			eventChannel = EventChannel.PINFAN;
			objectTitle = pinFanActivity.getActivitiyTile();
			objectId = pinFanActivity.getId();
		} else if (CommentChannel.FAQS.equals(commentDTO.getChannel())) {
			Optional<QuestionDTO> questionDTO = questionService.findOne(commentDTO.getObjectId());
			if (questionDTO.isPresent()) {
				QuestionDTO dto = questionDTO.get();
				eventChannel = EventChannel.FAQS;
				objectTitle = dto.getTitle();
				objectId = dto.getId();
			}
		}

		if (eventChannel != null) {
			// record the activity participation event start
			EventMessageDTO eventMessageDTO = eventMessageService
					.save(EventMessageBuilder.buildEventMessageDTO(commentDTO).title(objectTitle).get());
			if (null != eventMessageDTO.getId()) {
				messageService.createMessageForEvent(eventMessageDTO);
			}
			// record the activity participation event end
		}
		return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
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
		List<QueryDTO> queryDTOs = Lists.newArrayList();
		Page<CommentDTO> page = commentService.findAll(pageable, spec);
		// List<ThumbsUpDTO> thumbsUpDTOs = thumbsUpService.findAll(spec);
		// page.getContent().parallelStream().forEach(comment ->
		// comment.setThumbsUpDTOs(thumbsUpDTOs.stream()
		// .filter(thb ->
		// thb.getObjectId().equals(comment.getId())).collect(Collectors.toSet())));
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
