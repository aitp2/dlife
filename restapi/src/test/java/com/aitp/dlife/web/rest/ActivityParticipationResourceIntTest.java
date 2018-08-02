package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.ActivityParticipation;
import com.aitp.dlife.repository.ActivityParticipationRepository;
import com.aitp.dlife.service.ActivityParticipationService;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.service.mapper.ActivityParticipationMapper;
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
 * Test class for the ActivityParticipationResource REST controller.
 *
 * @see ActivityParticipationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class ActivityParticipationResourceIntTest {

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    private static final Instant DEFAULT_PARTICIPATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PARTICIPATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TOTAL_PARTICIPATE_DAYS = 1;
    private static final Integer UPDATED_TOTAL_PARTICIPATE_DAYS = 2;

    private static final Integer DEFAULT_TOTAL_CLOCKIN_DAYS = 1;
    private static final Integer UPDATED_TOTAL_CLOCKIN_DAYS = 2;

    private static final Integer DEFAULT_CURRENT_CONTINUE_DAYS = 1;
    private static final Integer UPDATED_CURRENT_CONTINUE_DAYS = 2;

    private static final Integer DEFAULT_LONGEST_CONTINUE_DAYS = 1;
    private static final Integer UPDATED_LONGEST_CONTINUE_DAYS = 2;

    private static final Instant DEFAULT_LATEST_CLOCKIN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LATEST_CLOCKIN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ActivityParticipationRepository activityParticipationRepository;


    @Autowired
    private ActivityParticipationMapper activityParticipationMapper;
    

    @Autowired
    private ActivityParticipationService activityParticipationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityParticipationMockMvc;

    private ActivityParticipation activityParticipation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityParticipationResource activityParticipationResource = new ActivityParticipationResource(activityParticipationService);
        this.restActivityParticipationMockMvc = MockMvcBuilders.standaloneSetup(activityParticipationResource)
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
    public static ActivityParticipation createEntity(EntityManager em) {
        ActivityParticipation activityParticipation = new ActivityParticipation()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .nickName(DEFAULT_NICK_NAME)
            .avatar(DEFAULT_AVATAR)
            .project(DEFAULT_PROJECT)
            .participationTime(DEFAULT_PARTICIPATION_TIME)
            .totalParticipateDays(DEFAULT_TOTAL_PARTICIPATE_DAYS)
            .totalClockinDays(DEFAULT_TOTAL_CLOCKIN_DAYS)
            .currentContinueDays(DEFAULT_CURRENT_CONTINUE_DAYS)
            .longestContinueDays(DEFAULT_LONGEST_CONTINUE_DAYS)
            .latestClockinTime(DEFAULT_LATEST_CLOCKIN_TIME);
        return activityParticipation;
    }

    @Before
    public void initTest() {
        activityParticipation = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityParticipation() throws Exception {
        int databaseSizeBeforeCreate = activityParticipationRepository.findAll().size();

        // Create the ActivityParticipation
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(activityParticipation);
        restActivityParticipationMockMvc.perform(post("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityParticipation testActivityParticipation = activityParticipationList.get(activityParticipationList.size() - 1);
        assertThat(testActivityParticipation.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testActivityParticipation.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testActivityParticipation.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testActivityParticipation.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testActivityParticipation.getParticipationTime()).isEqualTo(DEFAULT_PARTICIPATION_TIME);
        assertThat(testActivityParticipation.getTotalParticipateDays()).isEqualTo(DEFAULT_TOTAL_PARTICIPATE_DAYS);
        assertThat(testActivityParticipation.getTotalClockinDays()).isEqualTo(DEFAULT_TOTAL_CLOCKIN_DAYS);
        assertThat(testActivityParticipation.getCurrentContinueDays()).isEqualTo(DEFAULT_CURRENT_CONTINUE_DAYS);
        assertThat(testActivityParticipation.getLongestContinueDays()).isEqualTo(DEFAULT_LONGEST_CONTINUE_DAYS);
        assertThat(testActivityParticipation.getLatestClockinTime()).isEqualTo(DEFAULT_LATEST_CLOCKIN_TIME);
    }

    @Test
    @Transactional
    public void createActivityParticipationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityParticipationRepository.findAll().size();

        // Create the ActivityParticipation with an existing ID
        activityParticipation.setId(1L);
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(activityParticipation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityParticipationMockMvc.perform(post("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivityParticipations() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);

        // Get all the activityParticipationList
        restActivityParticipationMockMvc.perform(get("/api/activity-participations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityParticipation.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].participationTime").value(hasItem(DEFAULT_PARTICIPATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].totalParticipateDays").value(hasItem(DEFAULT_TOTAL_PARTICIPATE_DAYS)))
            .andExpect(jsonPath("$.[*].totalClockinDays").value(hasItem(DEFAULT_TOTAL_CLOCKIN_DAYS)))
            .andExpect(jsonPath("$.[*].currentContinueDays").value(hasItem(DEFAULT_CURRENT_CONTINUE_DAYS)))
            .andExpect(jsonPath("$.[*].longestContinueDays").value(hasItem(DEFAULT_LONGEST_CONTINUE_DAYS)))
            .andExpect(jsonPath("$.[*].latestClockinTime").value(hasItem(DEFAULT_LATEST_CLOCKIN_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getActivityParticipation() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);

        // Get the activityParticipation
        restActivityParticipationMockMvc.perform(get("/api/activity-participations/{id}", activityParticipation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityParticipation.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.participationTime").value(DEFAULT_PARTICIPATION_TIME.toString()))
            .andExpect(jsonPath("$.totalParticipateDays").value(DEFAULT_TOTAL_PARTICIPATE_DAYS))
            .andExpect(jsonPath("$.totalClockinDays").value(DEFAULT_TOTAL_CLOCKIN_DAYS))
            .andExpect(jsonPath("$.currentContinueDays").value(DEFAULT_CURRENT_CONTINUE_DAYS))
            .andExpect(jsonPath("$.longestContinueDays").value(DEFAULT_LONGEST_CONTINUE_DAYS))
            .andExpect(jsonPath("$.latestClockinTime").value(DEFAULT_LATEST_CLOCKIN_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingActivityParticipation() throws Exception {
        // Get the activityParticipation
        restActivityParticipationMockMvc.perform(get("/api/activity-participations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityParticipation() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);

        int databaseSizeBeforeUpdate = activityParticipationRepository.findAll().size();

        // Update the activityParticipation
        ActivityParticipation updatedActivityParticipation = activityParticipationRepository.findById(activityParticipation.getId()).get();
        // Disconnect from session so that the updates on updatedActivityParticipation are not directly saved in db
        em.detach(updatedActivityParticipation);
        updatedActivityParticipation
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .nickName(UPDATED_NICK_NAME)
            .avatar(UPDATED_AVATAR)
            .project(UPDATED_PROJECT)
            .participationTime(UPDATED_PARTICIPATION_TIME)
            .totalParticipateDays(UPDATED_TOTAL_PARTICIPATE_DAYS)
            .totalClockinDays(UPDATED_TOTAL_CLOCKIN_DAYS)
            .currentContinueDays(UPDATED_CURRENT_CONTINUE_DAYS)
            .longestContinueDays(UPDATED_LONGEST_CONTINUE_DAYS)
            .latestClockinTime(UPDATED_LATEST_CLOCKIN_TIME);
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(updatedActivityParticipation);

        restActivityParticipationMockMvc.perform(put("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeUpdate);
        ActivityParticipation testActivityParticipation = activityParticipationList.get(activityParticipationList.size() - 1);
        assertThat(testActivityParticipation.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testActivityParticipation.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testActivityParticipation.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testActivityParticipation.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testActivityParticipation.getParticipationTime()).isEqualTo(UPDATED_PARTICIPATION_TIME);
        assertThat(testActivityParticipation.getTotalParticipateDays()).isEqualTo(UPDATED_TOTAL_PARTICIPATE_DAYS);
        assertThat(testActivityParticipation.getTotalClockinDays()).isEqualTo(UPDATED_TOTAL_CLOCKIN_DAYS);
        assertThat(testActivityParticipation.getCurrentContinueDays()).isEqualTo(UPDATED_CURRENT_CONTINUE_DAYS);
        assertThat(testActivityParticipation.getLongestContinueDays()).isEqualTo(UPDATED_LONGEST_CONTINUE_DAYS);
        assertThat(testActivityParticipation.getLatestClockinTime()).isEqualTo(UPDATED_LATEST_CLOCKIN_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityParticipation() throws Exception {
        int databaseSizeBeforeUpdate = activityParticipationRepository.findAll().size();

        // Create the ActivityParticipation
        ActivityParticipationDTO activityParticipationDTO = activityParticipationMapper.toDto(activityParticipation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityParticipationMockMvc.perform(put("/api/activity-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityParticipationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityParticipation in the database
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivityParticipation() throws Exception {
        // Initialize the database
        activityParticipationRepository.saveAndFlush(activityParticipation);

        int databaseSizeBeforeDelete = activityParticipationRepository.findAll().size();

        // Get the activityParticipation
        restActivityParticipationMockMvc.perform(delete("/api/activity-participations/{id}", activityParticipation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActivityParticipation> activityParticipationList = activityParticipationRepository.findAll();
        assertThat(activityParticipationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityParticipation.class);
        ActivityParticipation activityParticipation1 = new ActivityParticipation();
        activityParticipation1.setId(1L);
        ActivityParticipation activityParticipation2 = new ActivityParticipation();
        activityParticipation2.setId(activityParticipation1.getId());
        assertThat(activityParticipation1).isEqualTo(activityParticipation2);
        activityParticipation2.setId(2L);
        assertThat(activityParticipation1).isNotEqualTo(activityParticipation2);
        activityParticipation1.setId(null);
        assertThat(activityParticipation1).isNotEqualTo(activityParticipation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityParticipationDTO.class);
        ActivityParticipationDTO activityParticipationDTO1 = new ActivityParticipationDTO();
        activityParticipationDTO1.setId(1L);
        ActivityParticipationDTO activityParticipationDTO2 = new ActivityParticipationDTO();
        assertThat(activityParticipationDTO1).isNotEqualTo(activityParticipationDTO2);
        activityParticipationDTO2.setId(activityParticipationDTO1.getId());
        assertThat(activityParticipationDTO1).isEqualTo(activityParticipationDTO2);
        activityParticipationDTO2.setId(2L);
        assertThat(activityParticipationDTO1).isNotEqualTo(activityParticipationDTO2);
        activityParticipationDTO1.setId(null);
        assertThat(activityParticipationDTO1).isNotEqualTo(activityParticipationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activityParticipationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activityParticipationMapper.fromId(null)).isNull();
    }
}
