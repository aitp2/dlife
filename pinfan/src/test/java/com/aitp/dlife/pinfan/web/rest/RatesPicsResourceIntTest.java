package com.aitp.dlife.pinfan.web.rest;

import com.aitp.dlife.pinfan.PinfanApp;

import com.aitp.dlife.pinfan.config.SecurityBeanOverrideConfiguration;

import com.aitp.dlife.pinfan.domain.RatesPics;
import com.aitp.dlife.pinfan.repository.RatesPicsRepository;
import com.aitp.dlife.pinfan.service.RatesPicsService;
import com.aitp.dlife.pinfan.repository.search.RatesPicsSearchRepository;
import com.aitp.dlife.pinfan.service.dto.RatesPicsDTO;
import com.aitp.dlife.pinfan.service.mapper.RatesPicsMapper;
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
 * Test class for the RatesPicsResource REST controller.
 *
 * @see RatesPicsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PinfanApp.class, SecurityBeanOverrideConfiguration.class})
public class RatesPicsResourceIntTest {

    private static final String DEFAULT_OSS_PATH = "AAAAAAAAAA";
    private static final String UPDATED_OSS_PATH = "BBBBBBBBBB";

    @Autowired
    private RatesPicsRepository ratesPicsRepository;

    @Autowired
    private RatesPicsMapper ratesPicsMapper;

    @Autowired
    private RatesPicsService ratesPicsService;

    @Autowired
    private RatesPicsSearchRepository ratesPicsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRatesPicsMockMvc;

    private RatesPics ratesPics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RatesPicsResource ratesPicsResource = new RatesPicsResource(ratesPicsService);
        this.restRatesPicsMockMvc = MockMvcBuilders.standaloneSetup(ratesPicsResource)
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
    public static RatesPics createEntity(EntityManager em) {
        RatesPics ratesPics = new RatesPics()
            .ossPath(DEFAULT_OSS_PATH);
        return ratesPics;
    }

    @Before
    public void initTest() {
        ratesPicsSearchRepository.deleteAll();
        ratesPics = createEntity(em);
    }

    @Test
    @Transactional
    public void createRatesPics() throws Exception {
        int databaseSizeBeforeCreate = ratesPicsRepository.findAll().size();

        // Create the RatesPics
        RatesPicsDTO ratesPicsDTO = ratesPicsMapper.toDto(ratesPics);
        restRatesPicsMockMvc.perform(post("/api/rates-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesPicsDTO)))
            .andExpect(status().isCreated());

        // Validate the RatesPics in the database
        List<RatesPics> ratesPicsList = ratesPicsRepository.findAll();
        assertThat(ratesPicsList).hasSize(databaseSizeBeforeCreate + 1);
        RatesPics testRatesPics = ratesPicsList.get(ratesPicsList.size() - 1);
        assertThat(testRatesPics.getOssPath()).isEqualTo(DEFAULT_OSS_PATH);

        // Validate the RatesPics in Elasticsearch
        RatesPics ratesPicsEs = ratesPicsSearchRepository.findOne(testRatesPics.getId());
        assertThat(ratesPicsEs).isEqualToIgnoringGivenFields(testRatesPics);
    }

    @Test
    @Transactional
    public void createRatesPicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ratesPicsRepository.findAll().size();

        // Create the RatesPics with an existing ID
        ratesPics.setId(1L);
        RatesPicsDTO ratesPicsDTO = ratesPicsMapper.toDto(ratesPics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatesPicsMockMvc.perform(post("/api/rates-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesPicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RatesPics in the database
        List<RatesPics> ratesPicsList = ratesPicsRepository.findAll();
        assertThat(ratesPicsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRatesPics() throws Exception {
        // Initialize the database
        ratesPicsRepository.saveAndFlush(ratesPics);

        // Get all the ratesPicsList
        restRatesPicsMockMvc.perform(get("/api/rates-pics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratesPics.getId().intValue())))
            .andExpect(jsonPath("$.[*].ossPath").value(hasItem(DEFAULT_OSS_PATH.toString())));
    }

    @Test
    @Transactional
    public void getRatesPics() throws Exception {
        // Initialize the database
        ratesPicsRepository.saveAndFlush(ratesPics);

        // Get the ratesPics
        restRatesPicsMockMvc.perform(get("/api/rates-pics/{id}", ratesPics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ratesPics.getId().intValue()))
            .andExpect(jsonPath("$.ossPath").value(DEFAULT_OSS_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRatesPics() throws Exception {
        // Get the ratesPics
        restRatesPicsMockMvc.perform(get("/api/rates-pics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRatesPics() throws Exception {
        // Initialize the database
        ratesPicsRepository.saveAndFlush(ratesPics);
        ratesPicsSearchRepository.save(ratesPics);
        int databaseSizeBeforeUpdate = ratesPicsRepository.findAll().size();

        // Update the ratesPics
        RatesPics updatedRatesPics = ratesPicsRepository.findOne(ratesPics.getId());
        // Disconnect from session so that the updates on updatedRatesPics are not directly saved in db
        em.detach(updatedRatesPics);
        updatedRatesPics
            .ossPath(UPDATED_OSS_PATH);
        RatesPicsDTO ratesPicsDTO = ratesPicsMapper.toDto(updatedRatesPics);

        restRatesPicsMockMvc.perform(put("/api/rates-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesPicsDTO)))
            .andExpect(status().isOk());

        // Validate the RatesPics in the database
        List<RatesPics> ratesPicsList = ratesPicsRepository.findAll();
        assertThat(ratesPicsList).hasSize(databaseSizeBeforeUpdate);
        RatesPics testRatesPics = ratesPicsList.get(ratesPicsList.size() - 1);
        assertThat(testRatesPics.getOssPath()).isEqualTo(UPDATED_OSS_PATH);

        // Validate the RatesPics in Elasticsearch
        RatesPics ratesPicsEs = ratesPicsSearchRepository.findOne(testRatesPics.getId());
        assertThat(ratesPicsEs).isEqualToIgnoringGivenFields(testRatesPics);
    }

    @Test
    @Transactional
    public void updateNonExistingRatesPics() throws Exception {
        int databaseSizeBeforeUpdate = ratesPicsRepository.findAll().size();

        // Create the RatesPics
        RatesPicsDTO ratesPicsDTO = ratesPicsMapper.toDto(ratesPics);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRatesPicsMockMvc.perform(put("/api/rates-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ratesPicsDTO)))
            .andExpect(status().isCreated());

        // Validate the RatesPics in the database
        List<RatesPics> ratesPicsList = ratesPicsRepository.findAll();
        assertThat(ratesPicsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRatesPics() throws Exception {
        // Initialize the database
        ratesPicsRepository.saveAndFlush(ratesPics);
        ratesPicsSearchRepository.save(ratesPics);
        int databaseSizeBeforeDelete = ratesPicsRepository.findAll().size();

        // Get the ratesPics
        restRatesPicsMockMvc.perform(delete("/api/rates-pics/{id}", ratesPics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ratesPicsExistsInEs = ratesPicsSearchRepository.exists(ratesPics.getId());
        assertThat(ratesPicsExistsInEs).isFalse();

        // Validate the database is empty
        List<RatesPics> ratesPicsList = ratesPicsRepository.findAll();
        assertThat(ratesPicsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRatesPics() throws Exception {
        // Initialize the database
        ratesPicsRepository.saveAndFlush(ratesPics);
        ratesPicsSearchRepository.save(ratesPics);

        // Search the ratesPics
        restRatesPicsMockMvc.perform(get("/api/_search/rates-pics?query=id:" + ratesPics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ratesPics.getId().intValue())))
            .andExpect(jsonPath("$.[*].ossPath").value(hasItem(DEFAULT_OSS_PATH.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatesPics.class);
        RatesPics ratesPics1 = new RatesPics();
        ratesPics1.setId(1L);
        RatesPics ratesPics2 = new RatesPics();
        ratesPics2.setId(ratesPics1.getId());
        assertThat(ratesPics1).isEqualTo(ratesPics2);
        ratesPics2.setId(2L);
        assertThat(ratesPics1).isNotEqualTo(ratesPics2);
        ratesPics1.setId(null);
        assertThat(ratesPics1).isNotEqualTo(ratesPics2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RatesPicsDTO.class);
        RatesPicsDTO ratesPicsDTO1 = new RatesPicsDTO();
        ratesPicsDTO1.setId(1L);
        RatesPicsDTO ratesPicsDTO2 = new RatesPicsDTO();
        assertThat(ratesPicsDTO1).isNotEqualTo(ratesPicsDTO2);
        ratesPicsDTO2.setId(ratesPicsDTO1.getId());
        assertThat(ratesPicsDTO1).isEqualTo(ratesPicsDTO2);
        ratesPicsDTO2.setId(2L);
        assertThat(ratesPicsDTO1).isNotEqualTo(ratesPicsDTO2);
        ratesPicsDTO1.setId(null);
        assertThat(ratesPicsDTO1).isNotEqualTo(ratesPicsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ratesPicsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ratesPicsMapper.fromId(null)).isNull();
    }
}
