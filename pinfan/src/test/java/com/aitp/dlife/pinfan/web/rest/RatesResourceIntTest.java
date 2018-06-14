package com.aitp.dlife.pinfan.web.rest;

import com.aitp.dlife.pinfan.PinfanApp;

import com.aitp.dlife.pinfan.config.SecurityBeanOverrideConfiguration;

import com.aitp.dlife.pinfan.domain.Rates;
import com.aitp.dlife.pinfan.repository.RatesRepository;
import com.aitp.dlife.pinfan.service.RatesService;
import com.aitp.dlife.pinfan.repository.search.RatesSearchRepository;
import com.aitp.dlife.pinfan.service.dto.RatesDTO;
import com.aitp.dlife.pinfan.service.mapper.RatesMapper;
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
import java.util.List;

import static com.aitp.dlife.pinfan.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RatesResource REST controller.
 *
 * @see RatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PinfanApp.class, SecurityBeanOverrideConfiguration.class})
public class RatesResourceIntTest {

    private static final Long DEFAULT_WECHAT_USER_ID = 1L;
    private static final Long UPDATED_WECHAT_USER_ID = 2L;

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final Long DEFAULT_ACTIVITY_ID = 1L;
    private static final Long UPDATED_ACTIVITY_ID = 2L;

    @Autowired
    private RatesRepository ratesRepository;

    @Autowired
    private RatesMapper ratesMapper;

    @Autowired
    private RatesService ratesService;

    @Autowired
    private RatesSearchRepository ratesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRatesMockMvc;

    private Rates rates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RatesResource ratesResource = new RatesResource(ratesService);
        this.restRatesMockMvc = MockMvcBuilders.standaloneSetup(ratesResource)
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
    public static Rates createEntity(EntityManager em) {
        Rates rates = new Rates()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .nickName(DEFAULT_NICK_NAME)
            .avatar(DEFAULT_AVATAR)
            .activityId(DEFAULT_ACTIVITY_ID);
        return rates;
    }

    @Before
    public void initTest() {
        ratesSearchRepository.deleteAll();
        rates = createEntity(em);
    }

    @Test
    @Transactional
    public void createRates() throws Exception {
        int databaseSizeBeforeCreate = ratesRepository.findAll().size();

        // Create the Rates
        RatesDTO ratesDTO = ratesMapper.toDto(rates);
        restRatesMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isCreated());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeCreate + 1);
        Rates testRates = ratesList.get(ratesList.size() - 1);
        assertThat(testRates.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testRates.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testRates.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testRates.getActivityId()).isEqualTo(DEFAULT_ACTIVITY_ID);

        // Validate the Rates in Elasticsearch
        Rates ratesEs = ratesSearchRepository.findOne(testRates.getId());
        assertThat(ratesEs).isEqualToIgnoringGivenFields(testRates);
    }

    @Test
    @Transactional
    public void createRatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ratesRepository.findAll().size();

        // Create the Rates with an existing ID
        rates.setId(1L);
        RatesDTO ratesDTO = ratesMapper.toDto(rates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatesMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);

        // Get all the ratesList
        restRatesMockMvc.perform(get("/api/rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rates.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].activityId").value(hasItem(DEFAULT_ACTIVITY_ID.intValue())));
    }

    @Test
    @Transactional
    public void getRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);

        // Get the rates
        restRatesMockMvc.perform(get("/api/rates/{id}", rates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rates.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.intValue()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.activityId").value(DEFAULT_ACTIVITY_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRates() throws Exception {
        // Get the rates
        restRatesMockMvc.perform(get("/api/rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);
        ratesSearchRepository.save(rates);
        int databaseSizeBeforeUpdate = ratesRepository.findAll().size();

        // Update the rates
        Rates updatedRates = ratesRepository.findOne(rates.getId());
        // Disconnect from session so that the updates on updatedRates are not directly saved in db
        em.detach(updatedRates);
        updatedRates
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .nickName(UPDATED_NICK_NAME)
            .avatar(UPDATED_AVATAR)
            .activityId(UPDATED_ACTIVITY_ID);
        RatesDTO ratesDTO = ratesMapper.toDto(updatedRates);

        restRatesMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isOk());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeUpdate);
        Rates testRates = ratesList.get(ratesList.size() - 1);
        assertThat(testRates.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testRates.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testRates.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testRates.getActivityId()).isEqualTo(UPDATED_ACTIVITY_ID);

        // Validate the Rates in Elasticsearch
        Rates ratesEs = ratesSearchRepository.findOne(testRates.getId());
        assertThat(ratesEs).isEqualToIgnoringGivenFields(testRates);
    }

    @Test
    @Transactional
    public void updateNonExistingRates() throws Exception {
        int databaseSizeBeforeUpdate = ratesRepository.findAll().size();

        // Create the Rates
        RatesDTO ratesDTO = ratesMapper.toDto(rates);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRatesMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesDTO)))
            .andExpect(status().isCreated());

        // Validate the Rates in the database
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);
        ratesSearchRepository.save(rates);
        int databaseSizeBeforeDelete = ratesRepository.findAll().size();

        // Get the rates
        restRatesMockMvc.perform(delete("/api/rates/{id}", rates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ratesExistsInEs = ratesSearchRepository.exists(rates.getId());
        assertThat(ratesExistsInEs).isFalse();

        // Validate the database is empty
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRates() throws Exception {
        // Initialize the database
        ratesRepository.saveAndFlush(rates);
        ratesSearchRepository.save(rates);

        // Search the rates
        restRatesMockMvc.perform(get("/api/_search/rates?query=id:" + rates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rates.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].activityId").value(hasItem(DEFAULT_ACTIVITY_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rates.class);
        Rates rates1 = new Rates();
        rates1.setId(1L);
        Rates rates2 = new Rates();
        rates2.setId(rates1.getId());
        assertThat(rates1).isEqualTo(rates2);
        rates2.setId(2L);
        assertThat(rates1).isNotEqualTo(rates2);
        rates1.setId(null);
        assertThat(rates1).isNotEqualTo(rates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatesDTO.class);
        RatesDTO ratesDTO1 = new RatesDTO();
        ratesDTO1.setId(1L);
        RatesDTO ratesDTO2 = new RatesDTO();
        assertThat(ratesDTO1).isNotEqualTo(ratesDTO2);
        ratesDTO2.setId(ratesDTO1.getId());
        assertThat(ratesDTO1).isEqualTo(ratesDTO2);
        ratesDTO2.setId(2L);
        assertThat(ratesDTO1).isNotEqualTo(ratesDTO2);
        ratesDTO1.setId(null);
        assertThat(ratesDTO1).isNotEqualTo(ratesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ratesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ratesMapper.fromId(null)).isNull();
    }
}
