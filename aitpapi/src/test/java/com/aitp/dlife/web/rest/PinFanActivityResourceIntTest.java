package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.PinFanActivity;
import com.aitp.dlife.repository.PinFanActivityRepository;
import com.aitp.dlife.service.PinFanActivityService;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.mapper.PinFanActivityMapper;
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
 * Test class for the PinFanActivityResource REST controller.
 *
 * @see PinFanActivityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class PinFanActivityResourceIntTest {

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

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
    private PinFanActivityRepository pinFanActivityRepository;

    @Autowired
    private PinFanActivityMapper pinFanActivityMapper;

    @Autowired
    private PinFanActivityService pinFanActivityService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPinFanActivityMockMvc;

    private PinFanActivity pinFanActivity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PinFanActivityResource pinFanActivityResource = new PinFanActivityResource(pinFanActivityService);
        this.restPinFanActivityMockMvc = MockMvcBuilders.standaloneSetup(pinFanActivityResource)
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
    public static PinFanActivity createEntity(EntityManager em) {
        PinFanActivity pinFanActivity = new PinFanActivity()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
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
        return pinFanActivity;
    }

    @Before
    public void initTest() {
        pinFanActivity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPinFanActivity() throws Exception {
        int databaseSizeBeforeCreate = pinFanActivityRepository.findAll().size();

        // Create the PinFanActivity
        PinFanActivityDTO pinFanActivityDTO = pinFanActivityMapper.toDto(pinFanActivity);
        restPinFanActivityMockMvc.perform(post("/api/pin-fan-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinFanActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the PinFanActivity in the database
        List<PinFanActivity> pinFanActivityList = pinFanActivityRepository.findAll();
        assertThat(pinFanActivityList).hasSize(databaseSizeBeforeCreate + 1);
        PinFanActivity testPinFanActivity = pinFanActivityList.get(pinFanActivityList.size() - 1);
        assertThat(testPinFanActivity.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testPinFanActivity.getActivitiyType()).isEqualTo(DEFAULT_ACTIVITIY_TYPE);
        assertThat(testPinFanActivity.getDescrption()).isEqualTo(DEFAULT_DESCRPTION);
        assertThat(testPinFanActivity.getOrganizeUser()).isEqualTo(DEFAULT_ORGANIZE_USER);
        assertThat(testPinFanActivity.getCoverPicture()).isEqualTo(DEFAULT_COVER_PICTURE);
        assertThat(testPinFanActivity.getAppointDatetime()).isEqualTo(DEFAULT_APPOINT_DATETIME);
        assertThat(testPinFanActivity.getLowerLimit()).isEqualTo(DEFAULT_LOWER_LIMIT);
        assertThat(testPinFanActivity.getUpperLimit()).isEqualTo(DEFAULT_UPPER_LIMIT);
        assertThat(testPinFanActivity.getPayType()).isEqualTo(DEFAULT_PAY_TYPE);
        assertThat(testPinFanActivity.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
        assertThat(testPinFanActivity.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPinFanActivity.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void createPinFanActivityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pinFanActivityRepository.findAll().size();

        // Create the PinFanActivity with an existing ID
        pinFanActivity.setId(1L);
        PinFanActivityDTO pinFanActivityDTO = pinFanActivityMapper.toDto(pinFanActivity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPinFanActivityMockMvc.perform(post("/api/pin-fan-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinFanActivityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PinFanActivity in the database
        List<PinFanActivity> pinFanActivityList = pinFanActivityRepository.findAll();
        assertThat(pinFanActivityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPinFanActivities() throws Exception {
        // Initialize the database
        pinFanActivityRepository.saveAndFlush(pinFanActivity);

        // Get all the pinFanActivityList
        restPinFanActivityMockMvc.perform(get("/api/pin-fan-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pinFanActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
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
    public void getPinFanActivity() throws Exception {
        // Initialize the database
        pinFanActivityRepository.saveAndFlush(pinFanActivity);

        // Get the pinFanActivity
        restPinFanActivityMockMvc.perform(get("/api/pin-fan-activities/{id}", pinFanActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pinFanActivity.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
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
    public void getNonExistingPinFanActivity() throws Exception {
        // Get the pinFanActivity
        restPinFanActivityMockMvc.perform(get("/api/pin-fan-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePinFanActivity() throws Exception {
        // Initialize the database
        pinFanActivityRepository.saveAndFlush(pinFanActivity);
        int databaseSizeBeforeUpdate = pinFanActivityRepository.findAll().size();

        // Update the pinFanActivity
        PinFanActivity updatedPinFanActivity = pinFanActivityRepository.findOne(pinFanActivity.getId());
        // Disconnect from session so that the updates on updatedPinFanActivity are not directly saved in db
        em.detach(updatedPinFanActivity);
        updatedPinFanActivity
            .wechatUserId(UPDATED_WECHAT_USER_ID)
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
        PinFanActivityDTO pinFanActivityDTO = pinFanActivityMapper.toDto(updatedPinFanActivity);

        restPinFanActivityMockMvc.perform(put("/api/pin-fan-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinFanActivityDTO)))
            .andExpect(status().isOk());

        // Validate the PinFanActivity in the database
        List<PinFanActivity> pinFanActivityList = pinFanActivityRepository.findAll();
        assertThat(pinFanActivityList).hasSize(databaseSizeBeforeUpdate);
        PinFanActivity testPinFanActivity = pinFanActivityList.get(pinFanActivityList.size() - 1);
        assertThat(testPinFanActivity.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testPinFanActivity.getActivitiyType()).isEqualTo(UPDATED_ACTIVITIY_TYPE);
        assertThat(testPinFanActivity.getDescrption()).isEqualTo(UPDATED_DESCRPTION);
        assertThat(testPinFanActivity.getOrganizeUser()).isEqualTo(UPDATED_ORGANIZE_USER);
        assertThat(testPinFanActivity.getCoverPicture()).isEqualTo(UPDATED_COVER_PICTURE);
        assertThat(testPinFanActivity.getAppointDatetime()).isEqualTo(UPDATED_APPOINT_DATETIME);
        assertThat(testPinFanActivity.getLowerLimit()).isEqualTo(UPDATED_LOWER_LIMIT);
        assertThat(testPinFanActivity.getUpperLimit()).isEqualTo(UPDATED_UPPER_LIMIT);
        assertThat(testPinFanActivity.getPayType()).isEqualTo(UPDATED_PAY_TYPE);
        assertThat(testPinFanActivity.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testPinFanActivity.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPinFanActivity.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPinFanActivity() throws Exception {
        int databaseSizeBeforeUpdate = pinFanActivityRepository.findAll().size();

        // Create the PinFanActivity
        PinFanActivityDTO pinFanActivityDTO = pinFanActivityMapper.toDto(pinFanActivity);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPinFanActivityMockMvc.perform(put("/api/pin-fan-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinFanActivityDTO)))
            .andExpect(status().isCreated());

        // Validate the PinFanActivity in the database
        List<PinFanActivity> pinFanActivityList = pinFanActivityRepository.findAll();
        assertThat(pinFanActivityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePinFanActivity() throws Exception {
        // Initialize the database
        pinFanActivityRepository.saveAndFlush(pinFanActivity);
        int databaseSizeBeforeDelete = pinFanActivityRepository.findAll().size();

        // Get the pinFanActivity
        restPinFanActivityMockMvc.perform(delete("/api/pin-fan-activities/{id}", pinFanActivity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PinFanActivity> pinFanActivityList = pinFanActivityRepository.findAll();
        assertThat(pinFanActivityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PinFanActivity.class);
        PinFanActivity pinFanActivity1 = new PinFanActivity();
        pinFanActivity1.setId(1L);
        PinFanActivity pinFanActivity2 = new PinFanActivity();
        pinFanActivity2.setId(pinFanActivity1.getId());
        assertThat(pinFanActivity1).isEqualTo(pinFanActivity2);
        pinFanActivity2.setId(2L);
        assertThat(pinFanActivity1).isNotEqualTo(pinFanActivity2);
        pinFanActivity1.setId(null);
        assertThat(pinFanActivity1).isNotEqualTo(pinFanActivity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PinFanActivityDTO.class);
        PinFanActivityDTO pinFanActivityDTO1 = new PinFanActivityDTO();
        pinFanActivityDTO1.setId(1L);
        PinFanActivityDTO pinFanActivityDTO2 = new PinFanActivityDTO();
        assertThat(pinFanActivityDTO1).isNotEqualTo(pinFanActivityDTO2);
        pinFanActivityDTO2.setId(pinFanActivityDTO1.getId());
        assertThat(pinFanActivityDTO1).isEqualTo(pinFanActivityDTO2);
        pinFanActivityDTO2.setId(2L);
        assertThat(pinFanActivityDTO1).isNotEqualTo(pinFanActivityDTO2);
        pinFanActivityDTO1.setId(null);
        assertThat(pinFanActivityDTO1).isNotEqualTo(pinFanActivityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pinFanActivityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pinFanActivityMapper.fromId(null)).isNull();
    }
}
