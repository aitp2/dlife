package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.PinfanPics;
import com.aitp.dlife.repository.PinfanPicsRepository;
import com.aitp.dlife.service.PinfanPicsService;
import com.aitp.dlife.service.dto.PinfanPicsDTO;
import com.aitp.dlife.service.mapper.PinfanPicsMapper;
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
 * Test class for the PinfanPicsResource REST controller.
 *
 * @see PinfanPicsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class PinfanPicsResourceIntTest {

    private static final String DEFAULT_OSS_PATH = "AAAAAAAAAA";
    private static final String UPDATED_OSS_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PinfanPicsRepository pinfanPicsRepository;


    @Autowired
    private PinfanPicsMapper pinfanPicsMapper;
    

    @Autowired
    private PinfanPicsService pinfanPicsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPinfanPicsMockMvc;

    private PinfanPics pinfanPics;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PinfanPicsResource pinfanPicsResource = new PinfanPicsResource(pinfanPicsService);
        this.restPinfanPicsMockMvc = MockMvcBuilders.standaloneSetup(pinfanPicsResource)
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
    public static PinfanPics createEntity(EntityManager em) {
        PinfanPics pinfanPics = new PinfanPics()
            .ossPath(DEFAULT_OSS_PATH)
            .createTime(DEFAULT_CREATE_TIME);
        return pinfanPics;
    }

    @Before
    public void initTest() {
        pinfanPics = createEntity(em);
    }

    @Test
    @Transactional
    public void createPinfanPics() throws Exception {
        int databaseSizeBeforeCreate = pinfanPicsRepository.findAll().size();

        // Create the PinfanPics
        PinfanPicsDTO pinfanPicsDTO = pinfanPicsMapper.toDto(pinfanPics);
        restPinfanPicsMockMvc.perform(post("/api/pinfan-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinfanPicsDTO)))
            .andExpect(status().isCreated());

        // Validate the PinfanPics in the database
        List<PinfanPics> pinfanPicsList = pinfanPicsRepository.findAll();
        assertThat(pinfanPicsList).hasSize(databaseSizeBeforeCreate + 1);
        PinfanPics testPinfanPics = pinfanPicsList.get(pinfanPicsList.size() - 1);
        assertThat(testPinfanPics.getOssPath()).isEqualTo(DEFAULT_OSS_PATH);
        assertThat(testPinfanPics.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createPinfanPicsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pinfanPicsRepository.findAll().size();

        // Create the PinfanPics with an existing ID
        pinfanPics.setId(1L);
        PinfanPicsDTO pinfanPicsDTO = pinfanPicsMapper.toDto(pinfanPics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPinfanPicsMockMvc.perform(post("/api/pinfan-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinfanPicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PinfanPics in the database
        List<PinfanPics> pinfanPicsList = pinfanPicsRepository.findAll();
        assertThat(pinfanPicsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPinfanPics() throws Exception {
        // Initialize the database
        pinfanPicsRepository.saveAndFlush(pinfanPics);

        // Get all the pinfanPicsList
        restPinfanPicsMockMvc.perform(get("/api/pinfan-pics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pinfanPics.getId().intValue())))
            .andExpect(jsonPath("$.[*].ossPath").value(hasItem(DEFAULT_OSS_PATH.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getPinfanPics() throws Exception {
        // Initialize the database
        pinfanPicsRepository.saveAndFlush(pinfanPics);

        // Get the pinfanPics
        restPinfanPicsMockMvc.perform(get("/api/pinfan-pics/{id}", pinfanPics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pinfanPics.getId().intValue()))
            .andExpect(jsonPath("$.ossPath").value(DEFAULT_OSS_PATH.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPinfanPics() throws Exception {
        // Get the pinfanPics
        restPinfanPicsMockMvc.perform(get("/api/pinfan-pics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePinfanPics() throws Exception {
        // Initialize the database
        pinfanPicsRepository.saveAndFlush(pinfanPics);

        int databaseSizeBeforeUpdate = pinfanPicsRepository.findAll().size();

        // Update the pinfanPics
        PinfanPics updatedPinfanPics = pinfanPicsRepository.findById(pinfanPics.getId()).get();
        // Disconnect from session so that the updates on updatedPinfanPics are not directly saved in db
        em.detach(updatedPinfanPics);
        updatedPinfanPics
            .ossPath(UPDATED_OSS_PATH)
            .createTime(UPDATED_CREATE_TIME);
        PinfanPicsDTO pinfanPicsDTO = pinfanPicsMapper.toDto(updatedPinfanPics);

        restPinfanPicsMockMvc.perform(put("/api/pinfan-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinfanPicsDTO)))
            .andExpect(status().isOk());

        // Validate the PinfanPics in the database
        List<PinfanPics> pinfanPicsList = pinfanPicsRepository.findAll();
        assertThat(pinfanPicsList).hasSize(databaseSizeBeforeUpdate);
        PinfanPics testPinfanPics = pinfanPicsList.get(pinfanPicsList.size() - 1);
        assertThat(testPinfanPics.getOssPath()).isEqualTo(UPDATED_OSS_PATH);
        assertThat(testPinfanPics.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPinfanPics() throws Exception {
        int databaseSizeBeforeUpdate = pinfanPicsRepository.findAll().size();

        // Create the PinfanPics
        PinfanPicsDTO pinfanPicsDTO = pinfanPicsMapper.toDto(pinfanPics);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPinfanPicsMockMvc.perform(put("/api/pinfan-pics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pinfanPicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PinfanPics in the database
        List<PinfanPics> pinfanPicsList = pinfanPicsRepository.findAll();
        assertThat(pinfanPicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePinfanPics() throws Exception {
        // Initialize the database
        pinfanPicsRepository.saveAndFlush(pinfanPics);

        int databaseSizeBeforeDelete = pinfanPicsRepository.findAll().size();

        // Get the pinfanPics
        restPinfanPicsMockMvc.perform(delete("/api/pinfan-pics/{id}", pinfanPics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PinfanPics> pinfanPicsList = pinfanPicsRepository.findAll();
        assertThat(pinfanPicsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PinfanPics.class);
        PinfanPics pinfanPics1 = new PinfanPics();
        pinfanPics1.setId(1L);
        PinfanPics pinfanPics2 = new PinfanPics();
        pinfanPics2.setId(pinfanPics1.getId());
        assertThat(pinfanPics1).isEqualTo(pinfanPics2);
        pinfanPics2.setId(2L);
        assertThat(pinfanPics1).isNotEqualTo(pinfanPics2);
        pinfanPics1.setId(null);
        assertThat(pinfanPics1).isNotEqualTo(pinfanPics2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PinfanPicsDTO.class);
        PinfanPicsDTO pinfanPicsDTO1 = new PinfanPicsDTO();
        pinfanPicsDTO1.setId(1L);
        PinfanPicsDTO pinfanPicsDTO2 = new PinfanPicsDTO();
        assertThat(pinfanPicsDTO1).isNotEqualTo(pinfanPicsDTO2);
        pinfanPicsDTO2.setId(pinfanPicsDTO1.getId());
        assertThat(pinfanPicsDTO1).isEqualTo(pinfanPicsDTO2);
        pinfanPicsDTO2.setId(2L);
        assertThat(pinfanPicsDTO1).isNotEqualTo(pinfanPicsDTO2);
        pinfanPicsDTO1.setId(null);
        assertThat(pinfanPicsDTO1).isNotEqualTo(pinfanPicsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pinfanPicsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pinfanPicsMapper.fromId(null)).isNull();
    }
}
