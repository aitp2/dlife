package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.ClockinSummary;
import com.aitp.dlife.repository.ClockinSummaryRepository;
import com.aitp.dlife.service.ClockinSummaryService;
import com.aitp.dlife.service.dto.ClockinSummaryDTO;
import com.aitp.dlife.service.mapper.ClockinSummaryMapper;
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
 * Test class for the ClockinSummaryResource REST controller.
 *
 * @see ClockinSummaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class ClockinSummaryResourceIntTest {

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTALLY_COUNT = 1;
    private static final Integer UPDATED_TOTALLY_COUNT = 2;

    private static final Integer DEFAULT_WEEKLY_COUNT = 1;
    private static final Integer UPDATED_WEEKLY_COUNT = 2;

    private static final Integer DEFAULT_SERIAL_COUNT = 1;
    private static final Integer UPDATED_SERIAL_COUNT = 2;

    private static final Instant DEFAULT_LAST_CLOCK_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_CLOCK_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ClockinSummaryRepository clockinSummaryRepository;


    @Autowired
    private ClockinSummaryMapper clockinSummaryMapper;
    

    @Autowired
    private ClockinSummaryService clockinSummaryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClockinSummaryMockMvc;

    private ClockinSummary clockinSummary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClockinSummaryResource clockinSummaryResource = new ClockinSummaryResource(clockinSummaryService);
        this.restClockinSummaryMockMvc = MockMvcBuilders.standaloneSetup(clockinSummaryResource)
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
    public static ClockinSummary createEntity(EntityManager em) {
        ClockinSummary clockinSummary = new ClockinSummary()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .totallyCount(DEFAULT_TOTALLY_COUNT)
            .weeklyCount(DEFAULT_WEEKLY_COUNT)
            .serialCount(DEFAULT_SERIAL_COUNT)
            .lastClockInTime(DEFAULT_LAST_CLOCK_IN_TIME);
        return clockinSummary;
    }

    @Before
    public void initTest() {
        clockinSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createClockinSummary() throws Exception {
        int databaseSizeBeforeCreate = clockinSummaryRepository.findAll().size();

        // Create the ClockinSummary
        ClockinSummaryDTO clockinSummaryDTO = clockinSummaryMapper.toDto(clockinSummary);
        restClockinSummaryMockMvc.perform(post("/api/clockin-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockinSummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the ClockinSummary in the database
        List<ClockinSummary> clockinSummaryList = clockinSummaryRepository.findAll();
        assertThat(clockinSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        ClockinSummary testClockinSummary = clockinSummaryList.get(clockinSummaryList.size() - 1);
        assertThat(testClockinSummary.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testClockinSummary.getTotallyCount()).isEqualTo(DEFAULT_TOTALLY_COUNT);
        assertThat(testClockinSummary.getWeeklyCount()).isEqualTo(DEFAULT_WEEKLY_COUNT);
        assertThat(testClockinSummary.getSerialCount()).isEqualTo(DEFAULT_SERIAL_COUNT);
        assertThat(testClockinSummary.getLastClockInTime()).isEqualTo(DEFAULT_LAST_CLOCK_IN_TIME);
    }

    @Test
    @Transactional
    public void createClockinSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clockinSummaryRepository.findAll().size();

        // Create the ClockinSummary with an existing ID
        clockinSummary.setId(1L);
        ClockinSummaryDTO clockinSummaryDTO = clockinSummaryMapper.toDto(clockinSummary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClockinSummaryMockMvc.perform(post("/api/clockin-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockinSummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClockinSummary in the database
        List<ClockinSummary> clockinSummaryList = clockinSummaryRepository.findAll();
        assertThat(clockinSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClockinSummaries() throws Exception {
        // Initialize the database
        clockinSummaryRepository.saveAndFlush(clockinSummary);

        // Get all the clockinSummaryList
        restClockinSummaryMockMvc.perform(get("/api/clockin-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clockinSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].totallyCount").value(hasItem(DEFAULT_TOTALLY_COUNT)))
            .andExpect(jsonPath("$.[*].weeklyCount").value(hasItem(DEFAULT_WEEKLY_COUNT)))
            .andExpect(jsonPath("$.[*].serialCount").value(hasItem(DEFAULT_SERIAL_COUNT)))
            .andExpect(jsonPath("$.[*].lastClockInTime").value(hasItem(DEFAULT_LAST_CLOCK_IN_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getClockinSummary() throws Exception {
        // Initialize the database
        clockinSummaryRepository.saveAndFlush(clockinSummary);

        // Get the clockinSummary
        restClockinSummaryMockMvc.perform(get("/api/clockin-summaries/{id}", clockinSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clockinSummary.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.totallyCount").value(DEFAULT_TOTALLY_COUNT))
            .andExpect(jsonPath("$.weeklyCount").value(DEFAULT_WEEKLY_COUNT))
            .andExpect(jsonPath("$.serialCount").value(DEFAULT_SERIAL_COUNT))
            .andExpect(jsonPath("$.lastClockInTime").value(DEFAULT_LAST_CLOCK_IN_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingClockinSummary() throws Exception {
        // Get the clockinSummary
        restClockinSummaryMockMvc.perform(get("/api/clockin-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClockinSummary() throws Exception {
        // Initialize the database
        clockinSummaryRepository.saveAndFlush(clockinSummary);

        int databaseSizeBeforeUpdate = clockinSummaryRepository.findAll().size();

        // Update the clockinSummary
        ClockinSummary updatedClockinSummary = clockinSummaryRepository.findById(clockinSummary.getId()).get();
        // Disconnect from session so that the updates on updatedClockinSummary are not directly saved in db
        em.detach(updatedClockinSummary);
        updatedClockinSummary
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .totallyCount(UPDATED_TOTALLY_COUNT)
            .weeklyCount(UPDATED_WEEKLY_COUNT)
            .serialCount(UPDATED_SERIAL_COUNT)
            .lastClockInTime(UPDATED_LAST_CLOCK_IN_TIME);
        ClockinSummaryDTO clockinSummaryDTO = clockinSummaryMapper.toDto(updatedClockinSummary);

        restClockinSummaryMockMvc.perform(put("/api/clockin-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockinSummaryDTO)))
            .andExpect(status().isOk());

        // Validate the ClockinSummary in the database
        List<ClockinSummary> clockinSummaryList = clockinSummaryRepository.findAll();
        assertThat(clockinSummaryList).hasSize(databaseSizeBeforeUpdate);
        ClockinSummary testClockinSummary = clockinSummaryList.get(clockinSummaryList.size() - 1);
        assertThat(testClockinSummary.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testClockinSummary.getTotallyCount()).isEqualTo(UPDATED_TOTALLY_COUNT);
        assertThat(testClockinSummary.getWeeklyCount()).isEqualTo(UPDATED_WEEKLY_COUNT);
        assertThat(testClockinSummary.getSerialCount()).isEqualTo(UPDATED_SERIAL_COUNT);
        assertThat(testClockinSummary.getLastClockInTime()).isEqualTo(UPDATED_LAST_CLOCK_IN_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingClockinSummary() throws Exception {
        int databaseSizeBeforeUpdate = clockinSummaryRepository.findAll().size();

        // Create the ClockinSummary
        ClockinSummaryDTO clockinSummaryDTO = clockinSummaryMapper.toDto(clockinSummary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClockinSummaryMockMvc.perform(put("/api/clockin-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clockinSummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClockinSummary in the database
        List<ClockinSummary> clockinSummaryList = clockinSummaryRepository.findAll();
        assertThat(clockinSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClockinSummary() throws Exception {
        // Initialize the database
        clockinSummaryRepository.saveAndFlush(clockinSummary);

        int databaseSizeBeforeDelete = clockinSummaryRepository.findAll().size();

        // Get the clockinSummary
        restClockinSummaryMockMvc.perform(delete("/api/clockin-summaries/{id}", clockinSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClockinSummary> clockinSummaryList = clockinSummaryRepository.findAll();
        assertThat(clockinSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClockinSummary.class);
        ClockinSummary clockinSummary1 = new ClockinSummary();
        clockinSummary1.setId(1L);
        ClockinSummary clockinSummary2 = new ClockinSummary();
        clockinSummary2.setId(clockinSummary1.getId());
        assertThat(clockinSummary1).isEqualTo(clockinSummary2);
        clockinSummary2.setId(2L);
        assertThat(clockinSummary1).isNotEqualTo(clockinSummary2);
        clockinSummary1.setId(null);
        assertThat(clockinSummary1).isNotEqualTo(clockinSummary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClockinSummaryDTO.class);
        ClockinSummaryDTO clockinSummaryDTO1 = new ClockinSummaryDTO();
        clockinSummaryDTO1.setId(1L);
        ClockinSummaryDTO clockinSummaryDTO2 = new ClockinSummaryDTO();
        assertThat(clockinSummaryDTO1).isNotEqualTo(clockinSummaryDTO2);
        clockinSummaryDTO2.setId(clockinSummaryDTO1.getId());
        assertThat(clockinSummaryDTO1).isEqualTo(clockinSummaryDTO2);
        clockinSummaryDTO2.setId(2L);
        assertThat(clockinSummaryDTO1).isNotEqualTo(clockinSummaryDTO2);
        clockinSummaryDTO1.setId(null);
        assertThat(clockinSummaryDTO1).isNotEqualTo(clockinSummaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clockinSummaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clockinSummaryMapper.fromId(null)).isNull();
    }
}
