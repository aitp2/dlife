package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.RecipeOrder;
import com.aitp.dlife.repository.RecipeOrderRepository;
import com.aitp.dlife.service.RecipeOrderService;
import com.aitp.dlife.service.dto.RecipeOrderDTO;
import com.aitp.dlife.service.mapper.RecipeOrderMapper;
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
 * Test class for the RecipeOrderResource REST controller.
 *
 * @see RecipeOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class RecipeOrderResourceIntTest {

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECIPE_VERSION = 9;
    private static final Integer UPDATED_RECIPE_VERSION = 8;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecipeOrderRepository recipeOrderRepository;


    @Autowired
    private RecipeOrderMapper recipeOrderMapper;
    

    @Autowired
    private RecipeOrderService recipeOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecipeOrderMockMvc;

    private RecipeOrder recipeOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipeOrderResource recipeOrderResource = new RecipeOrderResource(recipeOrderService);
        this.restRecipeOrderMockMvc = MockMvcBuilders.standaloneSetup(recipeOrderResource)
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
    public static RecipeOrder createEntity(EntityManager em) {
        RecipeOrder recipeOrder = new RecipeOrder()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .avatar(DEFAULT_AVATAR)
            .nickName(DEFAULT_NICK_NAME)
            .recipeVersion(DEFAULT_RECIPE_VERSION)
            .price(DEFAULT_PRICE)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return recipeOrder;
    }

    @Before
    public void initTest() {
        recipeOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipeOrder() throws Exception {
        int databaseSizeBeforeCreate = recipeOrderRepository.findAll().size();

        // Create the RecipeOrder
        RecipeOrderDTO recipeOrderDTO = recipeOrderMapper.toDto(recipeOrder);
        restRecipeOrderMockMvc.perform(post("/api/recipe-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeOrder in the database
        List<RecipeOrder> recipeOrderList = recipeOrderRepository.findAll();
        assertThat(recipeOrderList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeOrder testRecipeOrder = recipeOrderList.get(recipeOrderList.size() - 1);
        assertThat(testRecipeOrder.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testRecipeOrder.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testRecipeOrder.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testRecipeOrder.getRecipeVersion()).isEqualTo(DEFAULT_RECIPE_VERSION);
        assertThat(testRecipeOrder.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRecipeOrder.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testRecipeOrder.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createRecipeOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeOrderRepository.findAll().size();

        // Create the RecipeOrder with an existing ID
        recipeOrder.setId(1L);
        RecipeOrderDTO recipeOrderDTO = recipeOrderMapper.toDto(recipeOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeOrderMockMvc.perform(post("/api/recipe-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeOrder in the database
        List<RecipeOrder> recipeOrderList = recipeOrderRepository.findAll();
        assertThat(recipeOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecipeOrders() throws Exception {
        // Initialize the database
        recipeOrderRepository.saveAndFlush(recipeOrder);

        // Get all the recipeOrderList
        restRecipeOrderMockMvc.perform(get("/api/recipe-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].recipeVersion").value(hasItem(DEFAULT_RECIPE_VERSION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getRecipeOrder() throws Exception {
        // Initialize the database
        recipeOrderRepository.saveAndFlush(recipeOrder);

        // Get the recipeOrder
        restRecipeOrderMockMvc.perform(get("/api/recipe-orders/{id}", recipeOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipeOrder.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.recipeVersion").value(DEFAULT_RECIPE_VERSION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRecipeOrder() throws Exception {
        // Get the recipeOrder
        restRecipeOrderMockMvc.perform(get("/api/recipe-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipeOrder() throws Exception {
        // Initialize the database
        recipeOrderRepository.saveAndFlush(recipeOrder);

        int databaseSizeBeforeUpdate = recipeOrderRepository.findAll().size();

        // Update the recipeOrder
        RecipeOrder updatedRecipeOrder = recipeOrderRepository.findById(recipeOrder.getId()).get();
        // Disconnect from session so that the updates on updatedRecipeOrder are not directly saved in db
        em.detach(updatedRecipeOrder);
        updatedRecipeOrder
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .avatar(UPDATED_AVATAR)
            .nickName(UPDATED_NICK_NAME)
            .recipeVersion(UPDATED_RECIPE_VERSION)
            .price(UPDATED_PRICE)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        RecipeOrderDTO recipeOrderDTO = recipeOrderMapper.toDto(updatedRecipeOrder);

        restRecipeOrderMockMvc.perform(put("/api/recipe-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeOrderDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeOrder in the database
        List<RecipeOrder> recipeOrderList = recipeOrderRepository.findAll();
        assertThat(recipeOrderList).hasSize(databaseSizeBeforeUpdate);
        RecipeOrder testRecipeOrder = recipeOrderList.get(recipeOrderList.size() - 1);
        assertThat(testRecipeOrder.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testRecipeOrder.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testRecipeOrder.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testRecipeOrder.getRecipeVersion()).isEqualTo(UPDATED_RECIPE_VERSION);
        assertThat(testRecipeOrder.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRecipeOrder.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testRecipeOrder.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipeOrder() throws Exception {
        int databaseSizeBeforeUpdate = recipeOrderRepository.findAll().size();

        // Create the RecipeOrder
        RecipeOrderDTO recipeOrderDTO = recipeOrderMapper.toDto(recipeOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecipeOrderMockMvc.perform(put("/api/recipe-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeOrder in the database
        List<RecipeOrder> recipeOrderList = recipeOrderRepository.findAll();
        assertThat(recipeOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipeOrder() throws Exception {
        // Initialize the database
        recipeOrderRepository.saveAndFlush(recipeOrder);

        int databaseSizeBeforeDelete = recipeOrderRepository.findAll().size();

        // Get the recipeOrder
        restRecipeOrderMockMvc.perform(delete("/api/recipe-orders/{id}", recipeOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecipeOrder> recipeOrderList = recipeOrderRepository.findAll();
        assertThat(recipeOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeOrder.class);
        RecipeOrder recipeOrder1 = new RecipeOrder();
        recipeOrder1.setId(1L);
        RecipeOrder recipeOrder2 = new RecipeOrder();
        recipeOrder2.setId(recipeOrder1.getId());
        assertThat(recipeOrder1).isEqualTo(recipeOrder2);
        recipeOrder2.setId(2L);
        assertThat(recipeOrder1).isNotEqualTo(recipeOrder2);
        recipeOrder1.setId(null);
        assertThat(recipeOrder1).isNotEqualTo(recipeOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeOrderDTO.class);
        RecipeOrderDTO recipeOrderDTO1 = new RecipeOrderDTO();
        recipeOrderDTO1.setId(1L);
        RecipeOrderDTO recipeOrderDTO2 = new RecipeOrderDTO();
        assertThat(recipeOrderDTO1).isNotEqualTo(recipeOrderDTO2);
        recipeOrderDTO2.setId(recipeOrderDTO1.getId());
        assertThat(recipeOrderDTO1).isEqualTo(recipeOrderDTO2);
        recipeOrderDTO2.setId(2L);
        assertThat(recipeOrderDTO1).isNotEqualTo(recipeOrderDTO2);
        recipeOrderDTO1.setId(null);
        assertThat(recipeOrderDTO1).isNotEqualTo(recipeOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recipeOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recipeOrderMapper.fromId(null)).isNull();
    }
}
