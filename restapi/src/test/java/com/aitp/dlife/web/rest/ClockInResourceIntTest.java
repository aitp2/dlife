package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.ClockIn;
import com.aitp.dlife.repository.ClockInRepository;
import com.aitp.dlife.service.ClockInService;
import com.aitp.dlife.service.dto.ClockInDTO;
import com.aitp.dlife.service.mapper.ClockInMapper;
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
 * Test class for the ClockInResource REST controller.
 *
 * @see ClockInResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class ClockInResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SIGN_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_SIGN_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PUNCH_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUNCH_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ACTIVITY_ID = 1;
    private static final Integer UPDATED_ACTIVITY_ID = 2;

    @Autowired
    private ClockInRepository clockInRepository;


    @Autowired
    private ClockInMapper clockInMapper;
    

    @Autowired
    private ClockInService clockInService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClockInMockMvc;

    private ClockIn clockIn;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClockInResource clockInResource = new ClockInResource(clockInService);
        this.restClockInMockMvc = MockMvcBuilders.standaloneSetup(clockInResource)
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
    public static ClockIn createEntity(EntityManager em) {
        ClockIn clockIn = new ClockIn()
            .title(DEFAULT_TITLE)
            .signNote(DEFAULT_SIGN_NOTE)
            .punchDateTime(DEFAULT_PUNCH_DATE_TIME)
            .activityId(DEFAULT_ACTIVITY_ID);
        return clockIn;
    }

    @Before
    public void initTest() {
        clockIn = createEntity(em);
    }

    @Test
    @Transactional
    public void createClockIn() throws Exception {
        int databaseSizeBeforeCreate = clockInRepository.findAll().size();

        // Create the ClockIn
        ClockInDTO clockInDTO = clockInMapper.toDto(clockIn);
        restClockInMockMvc.perform(post("/api/clock-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockInDTO)))
            .andExpect(status().isCreated());

        // Validate the ClockIn in the database
        List<ClockIn> clockInList = clockInRepository.findAll();
        assertThat(clockInList).hasSize(databaseSizeBeforeCreate + 1);
        ClockIn testClockIn = clockInList.get(clockInList.size() - 1);
        assertThat(testClockIn.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testClockIn.getSignNote()).isEqualTo(DEFAULT_SIGN_NOTE);
        assertThat(testClockIn.getPunchDateTime()).isEqualTo(DEFAULT_PUNCH_DATE_TIME);
        assertThat(testClockIn.getActivityId()).isEqualTo(DEFAULT_ACTIVITY_ID);
    }

    @Test
    @Transactional
    public void createClockInWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clockInRepository.findAll().size();

        // Create the ClockIn with an existing ID
        clockIn.setId(1L);
        ClockInDTO clockInDTO = clockInMapper.toDto(clockIn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClockInMockMvc.perform(post("/api/clock-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClockIn in the database
        List<ClockIn> clockInList = clockInRepository.findAll();
        assertThat(clockInList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClockIns() throws Exception {
        // Initialize the database
        clockInRepository.saveAndFlush(clockIn);

        // Get all the clockInList
        restClockInMockMvc.perform(get("/api/clock-ins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clockIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].signNote").value(hasItem(DEFAULT_SIGN_NOTE.toString())))
            .andExpect(jsonPath("$.[*].punchDateTime").value(hasItem(DEFAULT_PUNCH_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].activityId").value(hasItem(DEFAULT_ACTIVITY_ID)));
    }
    

    @Test
    @Transactional
    public void getClockIn() throws Exception {
        // Initialize the database
        clockInRepository.saveAndFlush(clockIn);

        // Get the clockIn
        restClockInMockMvc.perform(get("/api/clock-ins/{id}", clockIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clockIn.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.signNote").value(DEFAULT_SIGN_NOTE.toString()))
            .andExpect(jsonPath("$.punchDateTime").value(DEFAULT_PUNCH_DATE_TIME.toString()))
            .andExpect(jsonPath("$.activityId").value(DEFAULT_ACTIVITY_ID));
    }
    @Test
    @Transactional
    public void getNonExistingClockIn() throws Exception {
        // Get the clockIn
        restClockInMockMvc.perform(get("/api/clock-ins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClockIn() throws Exception {
        // Initialize the database
        clockInRepository.saveAndFlush(clockIn);

        int databaseSizeBeforeUpdate = clockInRepository.findAll().size();

        // Update the clockIn
        ClockIn updatedClockIn = clockInRepository.findById(clockIn.getId()).get();
        // Disconnect from session so that the updates on updatedClockIn are not directly saved in db
        em.detach(updatedClockIn);
        updatedClockIn
            .title(UPDATED_TITLE)
            .signNote(UPDATED_SIGN_NOTE)
            .punchDateTime(UPDATED_PUNCH_DATE_TIME)
            .activityId(UPDATED_ACTIVITY_ID);
        ClockInDTO clockInDTO = clockInMapper.toDto(updatedClockIn);

        restClockInMockMvc.perform(put("/api/clock-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockInDTO)))
            .andExpect(status().isOk());

        // Validate the ClockIn in the database
        List<ClockIn> clockInList = clockInRepository.findAll();
        assertThat(clockInList).hasSize(databaseSizeBeforeUpdate);
        ClockIn testClockIn = clockInList.get(clockInList.size() - 1);
        assertThat(testClockIn.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClockIn.getSignNote()).isEqualTo(UPDATED_SIGN_NOTE);
        assertThat(testClockIn.getPunchDateTime()).isEqualTo(UPDATED_PUNCH_DATE_TIME);
        assertThat(testClockIn.getActivityId()).isEqualTo(UPDATED_ACTIVITY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClockIn() throws Exception {
        int databaseSizeBeforeUpdate = clockInRepository.findAll().size();

        // Create the ClockIn
        ClockInDTO clockInDTO = clockInMapper.toDto(clockIn);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClockInMockMvc.perform(put("/api/clock-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClockIn in the database
        List<ClockIn> clockInList = clockInRepository.findAll();
        assertThat(clockInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClockIn() throws Exception {
        // Initialize the database
        clockInRepository.saveAndFlush(clockIn);

        int databaseSizeBeforeDelete = clockInRepository.findAll().size();

        // Get the clockIn
        restClockInMockMvc.perform(delete("/api/clock-ins/{id}", clockIn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClockIn> clockInList = clockInRepository.findAll();
        assertThat(clockInList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClockIn.class);
        ClockIn clockIn1 = new ClockIn();
        clockIn1.setId(1L);
        ClockIn clockIn2 = new ClockIn();
        clockIn2.setId(clockIn1.getId());
        assertThat(clockIn1).isEqualTo(clockIn2);
        clockIn2.setId(2L);
        assertThat(clockIn1).isNotEqualTo(clockIn2);
        clockIn1.setId(null);
        assertThat(clockIn1).isNotEqualTo(clockIn2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClockInDTO.class);
        ClockInDTO clockInDTO1 = new ClockInDTO();
        clockInDTO1.setId(1L);
        ClockInDTO clockInDTO2 = new ClockInDTO();
        assertThat(clockInDTO1).isNotEqualTo(clockInDTO2);
        clockInDTO2.setId(clockInDTO1.getId());
        assertThat(clockInDTO1).isEqualTo(clockInDTO2);
        clockInDTO2.setId(2L);
        assertThat(clockInDTO1).isNotEqualTo(clockInDTO2);
        clockInDTO1.setId(null);
        assertThat(clockInDTO1).isNotEqualTo(clockInDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clockInMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clockInMapper.fromId(null)).isNull();
    }
}
