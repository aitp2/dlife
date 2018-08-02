package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.QuestionPic;
import com.aitp.dlife.repository.QuestionPicRepository;
import com.aitp.dlife.service.QuestionPicService;
import com.aitp.dlife.service.dto.QuestionPicDTO;
import com.aitp.dlife.service.mapper.QuestionPicMapper;
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
 * Test class for the QuestionPicResource REST controller.
 *
 * @see QuestionPicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class QuestionPicResourceIntTest {

    private static final String DEFAULT_OSS_PATH = "AAAAAAAAAA";
    private static final String UPDATED_OSS_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private QuestionPicRepository questionPicRepository;


    @Autowired
    private QuestionPicMapper questionPicMapper;
    

    @Autowired
    private QuestionPicService questionPicService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionPicMockMvc;

    private QuestionPic questionPic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionPicResource questionPicResource = new QuestionPicResource(questionPicService);
        this.restQuestionPicMockMvc = MockMvcBuilders.standaloneSetup(questionPicResource)
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
    public static QuestionPic createEntity(EntityManager em) {
        QuestionPic questionPic = new QuestionPic()
            .ossPath(DEFAULT_OSS_PATH)
            .createTime(DEFAULT_CREATE_TIME);
        return questionPic;
    }

    @Before
    public void initTest() {
        questionPic = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionPic() throws Exception {
        int databaseSizeBeforeCreate = questionPicRepository.findAll().size();

        // Create the QuestionPic
        QuestionPicDTO questionPicDTO = questionPicMapper.toDto(questionPic);
        restQuestionPicMockMvc.perform(post("/api/question-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionPicDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionPic in the database
        List<QuestionPic> questionPicList = questionPicRepository.findAll();
        assertThat(questionPicList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionPic testQuestionPic = questionPicList.get(questionPicList.size() - 1);
        assertThat(testQuestionPic.getOssPath()).isEqualTo(DEFAULT_OSS_PATH);
        assertThat(testQuestionPic.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createQuestionPicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionPicRepository.findAll().size();

        // Create the QuestionPic with an existing ID
        questionPic.setId(1L);
        QuestionPicDTO questionPicDTO = questionPicMapper.toDto(questionPic);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionPicMockMvc.perform(post("/api/question-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionPicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionPic in the database
        List<QuestionPic> questionPicList = questionPicRepository.findAll();
        assertThat(questionPicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionPics() throws Exception {
        // Initialize the database
        questionPicRepository.saveAndFlush(questionPic);

        // Get all the questionPicList
        restQuestionPicMockMvc.perform(get("/api/question-pics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionPic.getId().intValue())))
            .andExpect(jsonPath("$.[*].ossPath").value(hasItem(DEFAULT_OSS_PATH.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getQuestionPic() throws Exception {
        // Initialize the database
        questionPicRepository.saveAndFlush(questionPic);

        // Get the questionPic
        restQuestionPicMockMvc.perform(get("/api/question-pics/{id}", questionPic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionPic.getId().intValue()))
            .andExpect(jsonPath("$.ossPath").value(DEFAULT_OSS_PATH.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionPic() throws Exception {
        // Get the questionPic
        restQuestionPicMockMvc.perform(get("/api/question-pics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionPic() throws Exception {
        // Initialize the database
        questionPicRepository.saveAndFlush(questionPic);

        int databaseSizeBeforeUpdate = questionPicRepository.findAll().size();

        // Update the questionPic
        QuestionPic updatedQuestionPic = questionPicRepository.findById(questionPic.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionPic are not directly saved in db
        em.detach(updatedQuestionPic);
        updatedQuestionPic
            .ossPath(UPDATED_OSS_PATH)
            .createTime(UPDATED_CREATE_TIME);
        QuestionPicDTO questionPicDTO = questionPicMapper.toDto(updatedQuestionPic);

        restQuestionPicMockMvc.perform(put("/api/question-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionPicDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionPic in the database
        List<QuestionPic> questionPicList = questionPicRepository.findAll();
        assertThat(questionPicList).hasSize(databaseSizeBeforeUpdate);
        QuestionPic testQuestionPic = questionPicList.get(questionPicList.size() - 1);
        assertThat(testQuestionPic.getOssPath()).isEqualTo(UPDATED_OSS_PATH);
        assertThat(testQuestionPic.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionPic() throws Exception {
        int databaseSizeBeforeUpdate = questionPicRepository.findAll().size();

        // Create the QuestionPic
        QuestionPicDTO questionPicDTO = questionPicMapper.toDto(questionPic);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionPicMockMvc.perform(put("/api/question-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionPicDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionPic in the database
        List<QuestionPic> questionPicList = questionPicRepository.findAll();
        assertThat(questionPicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionPic() throws Exception {
        // Initialize the database
        questionPicRepository.saveAndFlush(questionPic);

        int databaseSizeBeforeDelete = questionPicRepository.findAll().size();

        // Get the questionPic
        restQuestionPicMockMvc.perform(delete("/api/question-pics/{id}", questionPic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionPic> questionPicList = questionPicRepository.findAll();
        assertThat(questionPicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionPic.class);
        QuestionPic questionPic1 = new QuestionPic();
        questionPic1.setId(1L);
        QuestionPic questionPic2 = new QuestionPic();
        questionPic2.setId(questionPic1.getId());
        assertThat(questionPic1).isEqualTo(questionPic2);
        questionPic2.setId(2L);
        assertThat(questionPic1).isNotEqualTo(questionPic2);
        questionPic1.setId(null);
        assertThat(questionPic1).isNotEqualTo(questionPic2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionPicDTO.class);
        QuestionPicDTO questionPicDTO1 = new QuestionPicDTO();
        questionPicDTO1.setId(1L);
        QuestionPicDTO questionPicDTO2 = new QuestionPicDTO();
        assertThat(questionPicDTO1).isNotEqualTo(questionPicDTO2);
        questionPicDTO2.setId(questionPicDTO1.getId());
        assertThat(questionPicDTO1).isEqualTo(questionPicDTO2);
        questionPicDTO2.setId(2L);
        assertThat(questionPicDTO1).isNotEqualTo(questionPicDTO2);
        questionPicDTO1.setId(null);
        assertThat(questionPicDTO1).isNotEqualTo(questionPicDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionPicMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionPicMapper.fromId(null)).isNull();
    }
}
