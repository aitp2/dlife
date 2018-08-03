package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.repository.CommentRepository;
import com.aitp.dlife.service.CommentService;
import com.aitp.dlife.service.dto.CommentDTO;
import com.aitp.dlife.service.mapper.CommentMapper;
import com.aitp.dlife.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.aitp.dlife.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aitp.dlife.domain.enumeration.CommentChannel;
/**
 * Test class for the CommentResource REST controller.
 *
 * @see CommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class CommentResourceIntTest {

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Long DEFAULT_OBJECT_ID = 1L;
    private static final Long UPDATED_OBJECT_ID = 2L;

    private static final CommentChannel DEFAULT_CHANNEL = CommentChannel.COOK;
    private static final CommentChannel UPDATED_CHANNEL = CommentChannel.FIT;

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING_1 = 1;
    private static final Integer UPDATED_RATING_1 = 2;

    private static final Integer DEFAULT_RATING_2 = 1;
    private static final Integer UPDATED_RATING_2 = 2;

    private static final Integer DEFAULT_RATING_3 = 1;
    private static final Integer UPDATED_RATING_3 = 2;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private CommentMapper commentMapper;
    

    @Autowired
    private CommentService commentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentMockMvc;

    private Comment comment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentResource commentResource = new CommentResource(commentService);
        this.restCommentMockMvc = MockMvcBuilders.standaloneSetup(commentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .parentId(DEFAULT_PARENT_ID)
            .objectId(DEFAULT_OBJECT_ID)
            .channel(DEFAULT_CHANNEL)
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .avatar(DEFAULT_AVATAR)
            .nickName(DEFAULT_NICK_NAME)
            .content(DEFAULT_CONTENT)
            .rating1(DEFAULT_RATING_1)
            .rating2(DEFAULT_RATING_2)
            .rating3(DEFAULT_RATING_3)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return comment;
    }

    @Before
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testComment.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testComment.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testComment.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testComment.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testComment.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testComment.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testComment.getRating1()).isEqualTo(DEFAULT_RATING_1);
        assertThat(testComment.getRating2()).isEqualTo(DEFAULT_RATING_2);
        assertThat(testComment.getRating3()).isEqualTo(DEFAULT_RATING_3);
        assertThat(testComment.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testComment.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment with an existing ID
        comment.setId(1L);
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].rating1").value(hasItem(DEFAULT_RATING_1)))
            .andExpect(jsonPath("$.[*].rating2").value(hasItem(DEFAULT_RATING_2)))
            .andExpect(jsonPath("$.[*].rating3").value(hasItem(DEFAULT_RATING_3)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.intValue()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.rating1").value(DEFAULT_RATING_1))
            .andExpect(jsonPath("$.rating2").value(DEFAULT_RATING_2))
            .andExpect(jsonPath("$.rating3").value(DEFAULT_RATING_3))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).get();
        // Disconnect from session so that the updates on updatedComment are not directly saved in db
        em.detach(updatedComment);
        updatedComment
            .parentId(UPDATED_PARENT_ID)
            .objectId(UPDATED_OBJECT_ID)
            .channel(UPDATED_CHANNEL)
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .avatar(UPDATED_AVATAR)
            .nickName(UPDATED_NICK_NAME)
            .content(UPDATED_CONTENT)
            .rating1(UPDATED_RATING_1)
            .rating2(UPDATED_RATING_2)
            .rating3(UPDATED_RATING_3)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        CommentDTO commentDTO = commentMapper.toDto(updatedComment);

        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testComment.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testComment.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testComment.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testComment.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testComment.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testComment.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testComment.getRating1()).isEqualTo(UPDATED_RATING_1);
        assertThat(testComment.getRating2()).isEqualTo(UPDATED_RATING_2);
        assertThat(testComment.getRating3()).isEqualTo(UPDATED_RATING_3);
        assertThat(testComment.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testComment.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Get the comment
        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = new Comment();
        comment1.setId(1L);
        Comment comment2 = new Comment();
        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);
        comment2.setId(2L);
        assertThat(comment1).isNotEqualTo(comment2);
        comment1.setId(null);
        assertThat(comment1).isNotEqualTo(comment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentDTO.class);
        CommentDTO commentDTO1 = new CommentDTO();
        commentDTO1.setId(1L);
        CommentDTO commentDTO2 = new CommentDTO();
        assertThat(commentDTO1).isNotEqualTo(commentDTO2);
        commentDTO2.setId(commentDTO1.getId());
        assertThat(commentDTO1).isEqualTo(commentDTO2);
        commentDTO2.setId(2L);
        assertThat(commentDTO1).isNotEqualTo(commentDTO2);
        commentDTO1.setId(null);
        assertThat(commentDTO1).isNotEqualTo(commentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commentMapper.fromId(null)).isNull();
    }
}
