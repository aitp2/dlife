package com.aitp.dlife.pinfan.web.rest;

import com.aitp.dlife.pinfan.PinfanApp;

import com.aitp.dlife.pinfan.config.SecurityBeanOverrideConfiguration;

import com.aitp.dlife.pinfan.domain.Activity;
import com.aitp.dlife.pinfan.repository.ActivityRepository;
import com.aitp.dlife.pinfan.service.ActivityService;
import com.aitp.dlife.pinfan.repository.search.ActivitySearchRepository;
import com.aitp.dlife.pinfan.service.dto.ActivityDTO;
import com.aitp.dlife.pinfan.service.mapper.ActivityMapper;
import com.aitp.dlife.pinfan.web.rest.errors.ExceptionTranslator;

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

import static com.aitp.dlife.pinfan.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActivityResource REST controller.
 *
 * @see ActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PinfanApp.class, SecurityBeanOverrideConfiguration.class})
public class ActivityResourceIntTest {

    private static final Integer DEFAULT_ACTIVITIY_TYPE = 1;
    private static final Integer UPDATED_ACTIVITIY_TYPE = 2;

    private static final String DEFAULT_DESCRPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZE_USER = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZE_USER = "BBBBBBBBBB";

    private static final String DEFAULT_COVER_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_COVER_PICTURE = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPOINT_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPOINT_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LOWER_LIMIT = 1;
    private static final Integer UPDATED_LOWER_LIMIT = 2;

    private static final Integer DEFAULT_UPPER_LIMIT = 1;
    private static final Integer UPDATED_UPPER_LIMIT = 2;

    private static final String DEFAULT_PAY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAY_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEADLINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEADLINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivitySearchRepository activitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActivityMockMvc;

    private Activity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityResource activityResource = new ActivityResource(activityService);
        this.restActivityMockMvc = MockMvcBuilders.standaloneSetup(activityResource)
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
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .activitiyType(DEFAULT_ACTIVITIY_TYPE)
            .descrption(DEFAULT_DESCRPTION)
            .organizeUser(DEFAULT_ORGANIZE_USER)
            .coverPicture(DEFAULT_COVER_PICTURE)
            .appointDatetime(DEFAULT_APPOINT_DATETIME)
            .lowerLimit(DEFAULT_LOWER_LIMIT)
            .upperLimit(DEFAULT_UPPER_LIMIT)
            .payType(DEFAULT_PAY_TYPE)
            .deadline(DEFAULT_DEADLINE)
            .comment(DEFAULT_COMMENT)
            .isActive(DEFAULT_IS_ACTIVE);
        return activity;
    }

    @Before
    public void initTest() {
        activitySearchRepository.deleteAll();
        activity = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivity() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate + 1);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivitiyType()).isEqualTo(DEFAULT_ACTIVITIY_TYPE);
        assertThat(testActivity.getDescrption()).isEqualTo(DEFAULT_DESCRPTION);
        assertThat(testActivity.getOrganizeUser()).isEqualTo(DEFAULT_ORGANIZE_USER);
        assertThat(testActivity.getCoverPicture()).isEqualTo(DEFAULT_COVER_PICTURE);
        assertThat(testActivity.getAppointDatetime()).isEqualTo(DEFAULT_APPOINT_DATETIME);
        assertThat(testActivity.getLowerLimit()).isEqualTo(DEFAULT_LOWER_LIMIT);
        assertThat(testActivity.getUpperLimit()).isEqualTo(DEFAULT_UPPER_LIMIT);
        assertThat(testActivity.getPayType()).isEqualTo(DEFAULT_PAY_TYPE);
        assertThat(testActivity.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
        assertThat(testActivity.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testActivity.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);

        // Validate the Activity in Elasticsearch
        Activity activityEs = activitySearchRepository.findOne(testActivity.getId());
        assertThat(activityEs).isEqualToIgnoringGivenFields(testActivity);
    }

    @Test
    @Transactional
    public void createActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityRepository.findAll().size();

        // Create the Activity with an existing ID
        activity.setId(1L);
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc.perform(post("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivities() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc.perform(get("/api/activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activitiyType").value(hasItem(DEFAULT_ACTIVITIY_TYPE)))
            .andExpect(jsonPath("$.[*].descrption").value(hasItem(DEFAULT_DESCRPTION.toString())))
            .andExpect(jsonPath("$.[*].organizeUser").value(hasItem(DEFAULT_ORGANIZE_USER.toString())))
            .andExpect(jsonPath("$.[*].coverPicture").value(hasItem(DEFAULT_COVER_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].appointDatetime").value(hasItem(DEFAULT_APPOINT_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].lowerLimit").value(hasItem(DEFAULT_LOWER_LIMIT)))
            .andExpect(jsonPath("$.[*].upperLimit").value(hasItem(DEFAULT_UPPER_LIMIT)))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.activitiyType").value(DEFAULT_ACTIVITIY_TYPE))
            .andExpect(jsonPath("$.descrption").value(DEFAULT_DESCRPTION.toString()))
            .andExpect(jsonPath("$.organizeUser").value(DEFAULT_ORGANIZE_USER.toString()))
            .andExpect(jsonPath("$.coverPicture").value(DEFAULT_COVER_PICTURE.toString()))
            .andExpect(jsonPath("$.appointDatetime").value(DEFAULT_APPOINT_DATETIME.toString()))
            .andExpect(jsonPath("$.lowerLimit").value(DEFAULT_LOWER_LIMIT))
            .andExpect(jsonPath("$.upperLimit").value(DEFAULT_UPPER_LIMIT))
            .andExpect(jsonPath("$.payType").value(DEFAULT_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get("/api/activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        activitySearchRepository.save(activity);
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Update the activity
        Activity updatedActivity = activityRepository.findOne(activity.getId());
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .activitiyType(UPDATED_ACTIVITIY_TYPE)
            .descrption(UPDATED_DESCRPTION)
            .organizeUser(UPDATED_ORGANIZE_USER)
            .coverPicture(UPDATED_COVER_PICTURE)
            .appointDatetime(UPDATED_APPOINT_DATETIME)
            .lowerLimit(UPDATED_LOWER_LIMIT)
            .upperLimit(UPDATED_UPPER_LIMIT)
            .payType(UPDATED_PAY_TYPE)
            .deadline(UPDATED_DEADLINE)
            .comment(UPDATED_COMMENT)
            .isActive(UPDATED_IS_ACTIVE);
        ActivityDTO activityDTO = activityMapper.toDto(updatedActivity);

        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isOk());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate);
        Activity testActivity = activityList.get(activityList.size() - 1);
        assertThat(testActivity.getActivitiyType()).isEqualTo(UPDATED_ACTIVITIY_TYPE);
        assertThat(testActivity.getDescrption()).isEqualTo(UPDATED_DESCRPTION);
        assertThat(testActivity.getOrganizeUser()).isEqualTo(UPDATED_ORGANIZE_USER);
        assertThat(testActivity.getCoverPicture()).isEqualTo(UPDATED_COVER_PICTURE);
        assertThat(testActivity.getAppointDatetime()).isEqualTo(UPDATED_APPOINT_DATETIME);
        assertThat(testActivity.getLowerLimit()).isEqualTo(UPDATED_LOWER_LIMIT);
        assertThat(testActivity.getUpperLimit()).isEqualTo(UPDATED_UPPER_LIMIT);
        assertThat(testActivity.getPayType()).isEqualTo(UPDATED_PAY_TYPE);
        assertThat(testActivity.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testActivity.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testActivity.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);

        // Validate the Activity in Elasticsearch
        Activity activityEs = activitySearchRepository.findOne(testActivity.getId());
        assertThat(activityEs).isEqualToIgnoringGivenFields(testActivity);
    }

    @Test
    @Transactional
    public void updateNonExistingActivity() throws Exception {
        int databaseSizeBeforeUpdate = activityRepository.findAll().size();

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActivityMockMvc.perform(put("/api/activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityDTO)))
            .andExpect(status().isCreated());

        // Validate the Activity in the database
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        activitySearchRepository.save(activity);
        int databaseSizeBeforeDelete = activityRepository.findAll().size();

        // Get the activity
        restActivityMockMvc.perform(delete("/api/activities/{id}", activity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean activityExistsInEs = activitySearchRepository.exists(activity.getId());
        assertThat(activityExistsInEs).isFalse();

        // Validate the database is empty
        List<Activity> activityList = activityRepository.findAll();
        assertThat(activityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchActivity() throws Exception {
        // Initialize the database
        activityRepository.saveAndFlush(activity);
        activitySearchRepository.save(activity);

        // Search the activity
        restActivityMockMvc.perform(get("/api/_search/activities?query=id:" + activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].activitiyType").value(hasItem(DEFAULT_ACTIVITIY_TYPE)))
            .andExpect(jsonPath("$.[*].descrption").value(hasItem(DEFAULT_DESCRPTION.toString())))
            .andExpect(jsonPath("$.[*].organizeUser").value(hasItem(DEFAULT_ORGANIZE_USER.toString())))
            .andExpect(jsonPath("$.[*].coverPicture").value(hasItem(DEFAULT_COVER_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].appointDatetime").value(hasItem(DEFAULT_APPOINT_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].lowerLimit").value(hasItem(DEFAULT_LOWER_LIMIT)))
            .andExpect(jsonPath("$.[*].upperLimit").value(hasItem(DEFAULT_UPPER_LIMIT)))
            .andExpect(jsonPath("$.[*].payType").value(hasItem(DEFAULT_PAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);
        activity2.setId(2L);
        assertThat(activity1).isNotEqualTo(activity2);
        activity1.setId(null);
        assertThat(activity1).isNotEqualTo(activity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityDTO.class);
        ActivityDTO activityDTO1 = new ActivityDTO();
        activityDTO1.setId(1L);
        ActivityDTO activityDTO2 = new ActivityDTO();
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
        activityDTO2.setId(activityDTO1.getId());
        assertThat(activityDTO1).isEqualTo(activityDTO2);
        activityDTO2.setId(2L);
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
        activityDTO1.setId(null);
        assertThat(activityDTO1).isNotEqualTo(activityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activityMapper.fromId(null)).isNull();
    }
}
