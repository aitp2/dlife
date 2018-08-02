package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.repository.FitnessActivityRepository;
import com.aitp.dlife.service.FitnessActivityService;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.mapper.FitnessActivityMapper;
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
 * Test class for the FitnessActivityResource REST controller.
 *
 * @see FitnessActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class FitnessActivityResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ROLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_SIGN_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SIGN_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SIGN_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SIGN_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVITY_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVITY_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVITY_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVITY_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_COMMENT_COUNT = 1;
    private static final Integer UPDATED_COMMENT_COUNT = 2;

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_READING_COUNT = 1;
    private static final Integer UPDATED_READING_COUNT = 2;

    @Autowired
    private FitnessActivityRepository fitnessActivityRepository;


    @Autowired
    private FitnessActivityMapper fitnessActivityMapper;
    

    @Autowired
    private FitnessActivityService fitnessActivityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFitnessActivityMockMvc;

    private FitnessActivity fitnessActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FitnessActivityResource fitnessActivityResource = new FitnessActivityResource(fitnessActivityService);
        this.restFitnessActivityMockMvc = MockMvcBuilders.standaloneSetup(fitnessActivityResource)
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
    public static FitnessActivity createEntity(EntityManager em) {
        FitnessActivity fitnessActivity = new FitnessActivity()
            .title(DEFAULT_TITLE)
            .descrption(DEFAULT_DESCRPTION)
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .nickName(DEFAULT_NICK_NAME)
            .avatar(DEFAULT_AVATAR)
            .project(DEFAULT_PROJECT)
            .companyRole(DEFAULT_COMPANY_ROLE)
            .signStartTime(DEFAULT_SIGN_START_TIME)
            .signEndTime(DEFAULT_SIGN_END_TIME)
            .activityStartTime(DEFAULT_ACTIVITY_START_TIME)
            .activityEndTime(DEFAULT_ACTIVITY_END_TIME)
            .commentCount(DEFAULT_COMMENT_COUNT)
            .modifyTime(DEFAULT_MODIFY_TIME)
            .readingCount(DEFAULT_READING_COUNT);
        return fitnessActivity;
    }

    @Before
    public void initTest() {
        fitnessActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createFitnessActivity() throws Exception {
        int databaseSizeBeforeCreate = fitnessActivityRepository.findAll().size();

        // Create the FitnessActivity
        FitnessActivityDTO fitnessActivityDTO = fitnessActivityMapper.toDto(fitnessActivity);
        restFitnessActivityMockMvc.perform(post("/api/fitness-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fitnessActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the FitnessActivity in the database
        List<FitnessActivity> fitnessActivityList = fitnessActivityRepository.findAll();
        assertThat(fitnessActivityList).hasSize(databaseSizeBeforeCreate + 1);
        FitnessActivity testFitnessActivity = fitnessActivityList.get(fitnessActivityList.size() - 1);
        assertThat(testFitnessActivity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFitnessActivity.getDescrption()).isEqualTo(DEFAULT_DESCRPTION);
        assertThat(testFitnessActivity.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testFitnessActivity.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testFitnessActivity.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testFitnessActivity.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testFitnessActivity.getCompanyRole()).isEqualTo(DEFAULT_COMPANY_ROLE);
        assertThat(testFitnessActivity.getSignStartTime()).isEqualTo(DEFAULT_SIGN_START_TIME);
        assertThat(testFitnessActivity.getSignEndTime()).isEqualTo(DEFAULT_SIGN_END_TIME);
        assertThat(testFitnessActivity.getActivityStartTime()).isEqualTo(DEFAULT_ACTIVITY_START_TIME);
        assertThat(testFitnessActivity.getActivityEndTime()).isEqualTo(DEFAULT_ACTIVITY_END_TIME);
        assertThat(testFitnessActivity.getCommentCount()).isEqualTo(DEFAULT_COMMENT_COUNT);
        assertThat(testFitnessActivity.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
        assertThat(testFitnessActivity.getReadingCount()).isEqualTo(DEFAULT_READING_COUNT);
    }

    @Test
    @Transactional
    public void createFitnessActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fitnessActivityRepository.findAll().size();

        // Create the FitnessActivity with an existing ID
        fitnessActivity.setId(1L);
        FitnessActivityDTO fitnessActivityDTO = fitnessActivityMapper.toDto(fitnessActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFitnessActivityMockMvc.perform(post("/api/fitness-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fitnessActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FitnessActivity in the database
        List<FitnessActivity> fitnessActivityList = fitnessActivityRepository.findAll();
        assertThat(fitnessActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFitnessActivities() throws Exception {
        // Initialize the database
        fitnessActivityRepository.saveAndFlush(fitnessActivity);

        // Get all the fitnessActivityList
        restFitnessActivityMockMvc.perform(get("/api/fitness-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fitnessActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].descrption").value(hasItem(DEFAULT_DESCRPTION.toString())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].companyRole").value(hasItem(DEFAULT_COMPANY_ROLE.toString())))
            .andExpect(jsonPath("$.[*].signStartTime").value(hasItem(DEFAULT_SIGN_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].signEndTime").value(hasItem(DEFAULT_SIGN_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].activityStartTime").value(hasItem(DEFAULT_ACTIVITY_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].activityEndTime").value(hasItem(DEFAULT_ACTIVITY_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].commentCount").value(hasItem(DEFAULT_COMMENT_COUNT)))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())))
            .andExpect(jsonPath("$.[*].readingCount").value(hasItem(DEFAULT_READING_COUNT)));
    }
    

    @Test
    @Transactional
    public void getFitnessActivity() throws Exception {
        // Initialize the database
        fitnessActivityRepository.saveAndFlush(fitnessActivity);

        // Get the fitnessActivity
        restFitnessActivityMockMvc.perform(get("/api/fitness-activities/{id}", fitnessActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fitnessActivity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.descrption").value(DEFAULT_DESCRPTION.toString()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.companyRole").value(DEFAULT_COMPANY_ROLE.toString()))
            .andExpect(jsonPath("$.signStartTime").value(DEFAULT_SIGN_START_TIME.toString()))
            .andExpect(jsonPath("$.signEndTime").value(DEFAULT_SIGN_END_TIME.toString()))
            .andExpect(jsonPath("$.activityStartTime").value(DEFAULT_ACTIVITY_START_TIME.toString()))
            .andExpect(jsonPath("$.activityEndTime").value(DEFAULT_ACTIVITY_END_TIME.toString()))
            .andExpect(jsonPath("$.commentCount").value(DEFAULT_COMMENT_COUNT))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()))
            .andExpect(jsonPath("$.readingCount").value(DEFAULT_READING_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingFitnessActivity() throws Exception {
        // Get the fitnessActivity
        restFitnessActivityMockMvc.perform(get("/api/fitness-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFitnessActivity() throws Exception {
        // Initialize the database
        fitnessActivityRepository.saveAndFlush(fitnessActivity);

        int databaseSizeBeforeUpdate = fitnessActivityRepository.findAll().size();

        // Update the fitnessActivity
        FitnessActivity updatedFitnessActivity = fitnessActivityRepository.findById(fitnessActivity.getId()).get();
        // Disconnect from session so that the updates on updatedFitnessActivity are not directly saved in db
        em.detach(updatedFitnessActivity);
        updatedFitnessActivity
            .title(UPDATED_TITLE)
            .descrption(UPDATED_DESCRPTION)
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .nickName(UPDATED_NICK_NAME)
            .avatar(UPDATED_AVATAR)
            .project(UPDATED_PROJECT)
            .companyRole(UPDATED_COMPANY_ROLE)
            .signStartTime(UPDATED_SIGN_START_TIME)
            .signEndTime(UPDATED_SIGN_END_TIME)
            .activityStartTime(UPDATED_ACTIVITY_START_TIME)
            .activityEndTime(UPDATED_ACTIVITY_END_TIME)
            .commentCount(UPDATED_COMMENT_COUNT)
            .modifyTime(UPDATED_MODIFY_TIME)
            .readingCount(UPDATED_READING_COUNT);
        FitnessActivityDTO fitnessActivityDTO = fitnessActivityMapper.toDto(updatedFitnessActivity);

        restFitnessActivityMockMvc.perform(put("/api/fitness-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fitnessActivityDTO)))
            .andExpect(status().isOk());

        // Validate the FitnessActivity in the database
        List<FitnessActivity> fitnessActivityList = fitnessActivityRepository.findAll();
        assertThat(fitnessActivityList).hasSize(databaseSizeBeforeUpdate);
        FitnessActivity testFitnessActivity = fitnessActivityList.get(fitnessActivityList.size() - 1);
        assertThat(testFitnessActivity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFitnessActivity.getDescrption()).isEqualTo(UPDATED_DESCRPTION);
        assertThat(testFitnessActivity.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testFitnessActivity.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testFitnessActivity.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testFitnessActivity.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testFitnessActivity.getCompanyRole()).isEqualTo(UPDATED_COMPANY_ROLE);
        assertThat(testFitnessActivity.getSignStartTime()).isEqualTo(UPDATED_SIGN_START_TIME);
        assertThat(testFitnessActivity.getSignEndTime()).isEqualTo(UPDATED_SIGN_END_TIME);
        assertThat(testFitnessActivity.getActivityStartTime()).isEqualTo(UPDATED_ACTIVITY_START_TIME);
        assertThat(testFitnessActivity.getActivityEndTime()).isEqualTo(UPDATED_ACTIVITY_END_TIME);
        assertThat(testFitnessActivity.getCommentCount()).isEqualTo(UPDATED_COMMENT_COUNT);
        assertThat(testFitnessActivity.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
        assertThat(testFitnessActivity.getReadingCount()).isEqualTo(UPDATED_READING_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingFitnessActivity() throws Exception {
        int databaseSizeBeforeUpdate = fitnessActivityRepository.findAll().size();

        // Create the FitnessActivity
        FitnessActivityDTO fitnessActivityDTO = fitnessActivityMapper.toDto(fitnessActivity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFitnessActivityMockMvc.perform(put("/api/fitness-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fitnessActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FitnessActivity in the database
        List<FitnessActivity> fitnessActivityList = fitnessActivityRepository.findAll();
        assertThat(fitnessActivityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFitnessActivity() throws Exception {
        // Initialize the database
        fitnessActivityRepository.saveAndFlush(fitnessActivity);

        int databaseSizeBeforeDelete = fitnessActivityRepository.findAll().size();

        // Get the fitnessActivity
        restFitnessActivityMockMvc.perform(delete("/api/fitness-activities/{id}", fitnessActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FitnessActivity> fitnessActivityList = fitnessActivityRepository.findAll();
        assertThat(fitnessActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FitnessActivity.class);
        FitnessActivity fitnessActivity1 = new FitnessActivity();
        fitnessActivity1.setId(1L);
        FitnessActivity fitnessActivity2 = new FitnessActivity();
        fitnessActivity2.setId(fitnessActivity1.getId());
        assertThat(fitnessActivity1).isEqualTo(fitnessActivity2);
        fitnessActivity2.setId(2L);
        assertThat(fitnessActivity1).isNotEqualTo(fitnessActivity2);
        fitnessActivity1.setId(null);
        assertThat(fitnessActivity1).isNotEqualTo(fitnessActivity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FitnessActivityDTO.class);
        FitnessActivityDTO fitnessActivityDTO1 = new FitnessActivityDTO();
        fitnessActivityDTO1.setId(1L);
        FitnessActivityDTO fitnessActivityDTO2 = new FitnessActivityDTO();
        assertThat(fitnessActivityDTO1).isNotEqualTo(fitnessActivityDTO2);
        fitnessActivityDTO2.setId(fitnessActivityDTO1.getId());
        assertThat(fitnessActivityDTO1).isEqualTo(fitnessActivityDTO2);
        fitnessActivityDTO2.setId(2L);
        assertThat(fitnessActivityDTO1).isNotEqualTo(fitnessActivityDTO2);
        fitnessActivityDTO1.setId(null);
        assertThat(fitnessActivityDTO1).isNotEqualTo(fitnessActivityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fitnessActivityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fitnessActivityMapper.fromId(null)).isNull();
    }
}
