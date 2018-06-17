package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.Evaluate;
import com.aitp.dlife.repository.EvaluateRepository;
import com.aitp.dlife.service.EvaluateService;
import com.aitp.dlife.service.dto.EvaluateDTO;
import com.aitp.dlife.service.mapper.EvaluateMapper;
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
 * Test class for the EvaluateResource REST controller.
 *
 * @see EvaluateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class EvaluateResourceIntTest {

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_TASTE_SCORE = 1;
    private static final Integer UPDATED_TASTE_SCORE = 2;

    private static final Integer DEFAULT_SERVICE_SCORE = 1;
    private static final Integer UPDATED_SERVICE_SCORE = 2;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EvaluateRepository evaluateRepository;

    @Autowired
    private EvaluateMapper evaluateMapper;

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEvaluateMockMvc;

    private Evaluate evaluate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EvaluateResource evaluateResource = new EvaluateResource(evaluateService);
        this.restEvaluateMockMvc = MockMvcBuilders.standaloneSetup(evaluateResource)
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
    public static Evaluate createEntity(EntityManager em) {
        Evaluate evaluate = new Evaluate()
            .parentId(DEFAULT_PARENT_ID)
            .content(DEFAULT_CONTENT)
            .tasteScore(DEFAULT_TASTE_SCORE)
            .serviceScore(DEFAULT_SERVICE_SCORE)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return evaluate;
    }

    @Before
    public void initTest() {
        evaluate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluate() throws Exception {
        int databaseSizeBeforeCreate = evaluateRepository.findAll().size();

        // Create the Evaluate
        EvaluateDTO evaluateDTO = evaluateMapper.toDto(evaluate);
        restEvaluateMockMvc.perform(post("/api/evaluates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluateDTO)))
            .andExpect(status().isCreated());

        // Validate the Evaluate in the database
        List<Evaluate> evaluateList = evaluateRepository.findAll();
        assertThat(evaluateList).hasSize(databaseSizeBeforeCreate + 1);
        Evaluate testEvaluate = evaluateList.get(evaluateList.size() - 1);
        assertThat(testEvaluate.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testEvaluate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testEvaluate.getTasteScore()).isEqualTo(DEFAULT_TASTE_SCORE);
        assertThat(testEvaluate.getServiceScore()).isEqualTo(DEFAULT_SERVICE_SCORE);
        assertThat(testEvaluate.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testEvaluate.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createEvaluateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluateRepository.findAll().size();

        // Create the Evaluate with an existing ID
        evaluate.setId(1L);
        EvaluateDTO evaluateDTO = evaluateMapper.toDto(evaluate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluateMockMvc.perform(post("/api/evaluates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Evaluate in the database
        List<Evaluate> evaluateList = evaluateRepository.findAll();
        assertThat(evaluateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEvaluates() throws Exception {
        // Initialize the database
        evaluateRepository.saveAndFlush(evaluate);

        // Get all the evaluateList
        restEvaluateMockMvc.perform(get("/api/evaluates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluate.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].tasteScore").value(hasItem(DEFAULT_TASTE_SCORE)))
            .andExpect(jsonPath("$.[*].serviceScore").value(hasItem(DEFAULT_SERVICE_SCORE)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));
    }

    @Test
    @Transactional
    public void getEvaluate() throws Exception {
        // Initialize the database
        evaluateRepository.saveAndFlush(evaluate);

        // Get the evaluate
        restEvaluateMockMvc.perform(get("/api/evaluates/{id}", evaluate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evaluate.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.tasteScore").value(DEFAULT_TASTE_SCORE))
            .andExpect(jsonPath("$.serviceScore").value(DEFAULT_SERVICE_SCORE))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluate() throws Exception {
        // Get the evaluate
        restEvaluateMockMvc.perform(get("/api/evaluates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluate() throws Exception {
        // Initialize the database
        evaluateRepository.saveAndFlush(evaluate);
        int databaseSizeBeforeUpdate = evaluateRepository.findAll().size();

        // Update the evaluate
        Evaluate updatedEvaluate = evaluateRepository.findOne(evaluate.getId());
        // Disconnect from session so that the updates on updatedEvaluate are not directly saved in db
        em.detach(updatedEvaluate);
        updatedEvaluate
            .parentId(UPDATED_PARENT_ID)
            .content(UPDATED_CONTENT)
            .tasteScore(UPDATED_TASTE_SCORE)
            .serviceScore(UPDATED_SERVICE_SCORE)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        EvaluateDTO evaluateDTO = evaluateMapper.toDto(updatedEvaluate);

        restEvaluateMockMvc.perform(put("/api/evaluates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluateDTO)))
            .andExpect(status().isOk());

        // Validate the Evaluate in the database
        List<Evaluate> evaluateList = evaluateRepository.findAll();
        assertThat(evaluateList).hasSize(databaseSizeBeforeUpdate);
        Evaluate testEvaluate = evaluateList.get(evaluateList.size() - 1);
        assertThat(testEvaluate.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testEvaluate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testEvaluate.getTasteScore()).isEqualTo(UPDATED_TASTE_SCORE);
        assertThat(testEvaluate.getServiceScore()).isEqualTo(UPDATED_SERVICE_SCORE);
        assertThat(testEvaluate.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testEvaluate.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluate() throws Exception {
        int databaseSizeBeforeUpdate = evaluateRepository.findAll().size();

        // Create the Evaluate
        EvaluateDTO evaluateDTO = evaluateMapper.toDto(evaluate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEvaluateMockMvc.perform(put("/api/evaluates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluateDTO)))
            .andExpect(status().isCreated());

        // Validate the Evaluate in the database
        List<Evaluate> evaluateList = evaluateRepository.findAll();
        assertThat(evaluateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEvaluate() throws Exception {
        // Initialize the database
        evaluateRepository.saveAndFlush(evaluate);
        int databaseSizeBeforeDelete = evaluateRepository.findAll().size();

        // Get the evaluate
        restEvaluateMockMvc.perform(delete("/api/evaluates/{id}", evaluate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Evaluate> evaluateList = evaluateRepository.findAll();
        assertThat(evaluateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Evaluate.class);
        Evaluate evaluate1 = new Evaluate();
        evaluate1.setId(1L);
        Evaluate evaluate2 = new Evaluate();
        evaluate2.setId(evaluate1.getId());
        assertThat(evaluate1).isEqualTo(evaluate2);
        evaluate2.setId(2L);
        assertThat(evaluate1).isNotEqualTo(evaluate2);
        evaluate1.setId(null);
        assertThat(evaluate1).isNotEqualTo(evaluate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluateDTO.class);
        EvaluateDTO evaluateDTO1 = new EvaluateDTO();
        evaluateDTO1.setId(1L);
        EvaluateDTO evaluateDTO2 = new EvaluateDTO();
        assertThat(evaluateDTO1).isNotEqualTo(evaluateDTO2);
        evaluateDTO2.setId(evaluateDTO1.getId());
        assertThat(evaluateDTO1).isEqualTo(evaluateDTO2);
        evaluateDTO2.setId(2L);
        assertThat(evaluateDTO1).isNotEqualTo(evaluateDTO2);
        evaluateDTO1.setId(null);
        assertThat(evaluateDTO1).isNotEqualTo(evaluateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(evaluateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(evaluateMapper.fromId(null)).isNull();
    }
}
