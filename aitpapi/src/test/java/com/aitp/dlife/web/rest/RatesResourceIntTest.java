package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.Rates;
import com.aitp.dlife.repository.RatesRepository;
import com.aitp.dlife.service.PinfanPicsService;
import com.aitp.dlife.service.RatesService;
import com.aitp.dlife.service.dto.RatesDTO;
import com.aitp.dlife.service.mapper.RatesMapper;
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
 * Test class for the RatesResource REST controller.
 *
 * @see RatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class RatesResourceIntTest {

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RatesRepository ratesRepository;

    @Autowired
    private PinfanPicsService pinfanPicsService;

    @Autowired
    private RatesMapper ratesMapper;

    @Autowired
    private RatesService ratesService;

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
        final RatesResource ratesResource = new RatesResource(ratesService,pinfanPicsService);
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
            .avatar(DEFAULT_AVATAR)
            .nickName(DEFAULT_NICK_NAME)
            .comments(DEFAULT_COMMENTS)
            .rating(DEFAULT_RATING)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return rates;
    }

    @Before
    public void initTest() {
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
        assertThat(testRates.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testRates.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testRates.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testRates.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testRates.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testRates.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
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
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));
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
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()));
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
        int databaseSizeBeforeUpdate = ratesRepository.findAll().size();

        // Update the rates
        Rates updatedRates = ratesRepository.findOne(rates.getId());
        // Disconnect from session so that the updates on updatedRates are not directly saved in db
        em.detach(updatedRates);
        updatedRates
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .avatar(UPDATED_AVATAR)
            .nickName(UPDATED_NICK_NAME)
            .comments(UPDATED_COMMENTS)
            .rating(UPDATED_RATING)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
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
        assertThat(testRates.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testRates.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testRates.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testRates.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testRates.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRates.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
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
        int databaseSizeBeforeDelete = ratesRepository.findAll().size();

        // Get the rates
        restRatesMockMvc.perform(delete("/api/rates/{id}", rates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rates> ratesList = ratesRepository.findAll();
        assertThat(ratesList).hasSize(databaseSizeBeforeDelete - 1);
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
