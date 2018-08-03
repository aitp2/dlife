package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.CommentPic;
import com.aitp.dlife.repository.CommentPicRepository;
import com.aitp.dlife.service.CommentPicService;
import com.aitp.dlife.service.dto.CommentPicDTO;
import com.aitp.dlife.service.mapper.CommentPicMapper;
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

/**
 * Test class for the CommentPicResource REST controller.
 *
 * @see CommentPicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class CommentPicResourceIntTest {

    private static final String DEFAULT_OSS_PATH = "AAAAAAAAAA";
    private static final String UPDATED_OSS_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommentPicRepository commentPicRepository;


    @Autowired
    private CommentPicMapper commentPicMapper;
    

    @Autowired
    private CommentPicService commentPicService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentPicMockMvc;

    private CommentPic commentPic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentPicResource commentPicResource = new CommentPicResource(commentPicService);
        this.restCommentPicMockMvc = MockMvcBuilders.standaloneSetup(commentPicResource)
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
    public static CommentPic createEntity(EntityManager em) {
        CommentPic commentPic = new CommentPic()
            .ossPath(DEFAULT_OSS_PATH)
            .createTime(DEFAULT_CREATE_TIME);
        return commentPic;
    }

    @Before
    public void initTest() {
        commentPic = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentPic() throws Exception {
        int databaseSizeBeforeCreate = commentPicRepository.findAll().size();

        // Create the CommentPic
        CommentPicDTO commentPicDTO = commentPicMapper.toDto(commentPic);
        restCommentPicMockMvc.perform(post("/api/comment-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentPicDTO)))
            .andExpect(status().isCreated());

        // Validate the CommentPic in the database
        List<CommentPic> commentPicList = commentPicRepository.findAll();
        assertThat(commentPicList).hasSize(databaseSizeBeforeCreate + 1);
        CommentPic testCommentPic = commentPicList.get(commentPicList.size() - 1);
        assertThat(testCommentPic.getOssPath()).isEqualTo(DEFAULT_OSS_PATH);
        assertThat(testCommentPic.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createCommentPicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentPicRepository.findAll().size();

        // Create the CommentPic with an existing ID
        commentPic.setId(1L);
        CommentPicDTO commentPicDTO = commentPicMapper.toDto(commentPic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentPicMockMvc.perform(post("/api/comment-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentPicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommentPic in the database
        List<CommentPic> commentPicList = commentPicRepository.findAll();
        assertThat(commentPicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommentPics() throws Exception {
        // Initialize the database
        commentPicRepository.saveAndFlush(commentPic);

        // Get all the commentPicList
        restCommentPicMockMvc.perform(get("/api/comment-pics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentPic.getId().intValue())))
            .andExpect(jsonPath("$.[*].ossPath").value(hasItem(DEFAULT_OSS_PATH.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getCommentPic() throws Exception {
        // Initialize the database
        commentPicRepository.saveAndFlush(commentPic);

        // Get the commentPic
        restCommentPicMockMvc.perform(get("/api/comment-pics/{id}", commentPic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentPic.getId().intValue()))
            .andExpect(jsonPath("$.ossPath").value(DEFAULT_OSS_PATH.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCommentPic() throws Exception {
        // Get the commentPic
        restCommentPicMockMvc.perform(get("/api/comment-pics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentPic() throws Exception {
        // Initialize the database
        commentPicRepository.saveAndFlush(commentPic);

        int databaseSizeBeforeUpdate = commentPicRepository.findAll().size();

        // Update the commentPic
        CommentPic updatedCommentPic = commentPicRepository.findById(commentPic.getId()).get();
        // Disconnect from session so that the updates on updatedCommentPic are not directly saved in db
        em.detach(updatedCommentPic);
        updatedCommentPic
            .ossPath(UPDATED_OSS_PATH)
            .createTime(UPDATED_CREATE_TIME);
        CommentPicDTO commentPicDTO = commentPicMapper.toDto(updatedCommentPic);

        restCommentPicMockMvc.perform(put("/api/comment-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentPicDTO)))
            .andExpect(status().isOk());

        // Validate the CommentPic in the database
        List<CommentPic> commentPicList = commentPicRepository.findAll();
        assertThat(commentPicList).hasSize(databaseSizeBeforeUpdate);
        CommentPic testCommentPic = commentPicList.get(commentPicList.size() - 1);
        assertThat(testCommentPic.getOssPath()).isEqualTo(UPDATED_OSS_PATH);
        assertThat(testCommentPic.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentPic() throws Exception {
        int databaseSizeBeforeUpdate = commentPicRepository.findAll().size();

        // Create the CommentPic
        CommentPicDTO commentPicDTO = commentPicMapper.toDto(commentPic);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentPicMockMvc.perform(put("/api/comment-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentPicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommentPic in the database
        List<CommentPic> commentPicList = commentPicRepository.findAll();
        assertThat(commentPicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommentPic() throws Exception {
        // Initialize the database
        commentPicRepository.saveAndFlush(commentPic);

        int databaseSizeBeforeDelete = commentPicRepository.findAll().size();

        // Get the commentPic
        restCommentPicMockMvc.perform(delete("/api/comment-pics/{id}", commentPic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommentPic> commentPicList = commentPicRepository.findAll();
        assertThat(commentPicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentPic.class);
        CommentPic commentPic1 = new CommentPic();
        commentPic1.setId(1L);
        CommentPic commentPic2 = new CommentPic();
        commentPic2.setId(commentPic1.getId());
        assertThat(commentPic1).isEqualTo(commentPic2);
        commentPic2.setId(2L);
        assertThat(commentPic1).isNotEqualTo(commentPic2);
        commentPic1.setId(null);
        assertThat(commentPic1).isNotEqualTo(commentPic2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentPicDTO.class);
        CommentPicDTO commentPicDTO1 = new CommentPicDTO();
        commentPicDTO1.setId(1L);
        CommentPicDTO commentPicDTO2 = new CommentPicDTO();
        assertThat(commentPicDTO1).isNotEqualTo(commentPicDTO2);
        commentPicDTO2.setId(commentPicDTO1.getId());
        assertThat(commentPicDTO1).isEqualTo(commentPicDTO2);
        commentPicDTO2.setId(2L);
        assertThat(commentPicDTO1).isNotEqualTo(commentPicDTO2);
        commentPicDTO1.setId(null);
        assertThat(commentPicDTO1).isNotEqualTo(commentPicDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commentPicMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commentPicMapper.fromId(null)).isNull();
    }
}
