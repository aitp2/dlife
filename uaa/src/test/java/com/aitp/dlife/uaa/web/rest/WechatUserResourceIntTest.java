package com.aitp.dlife.uaa.web.rest;

import com.aitp.dlife.uaa.UaaApp;

import com.aitp.dlife.uaa.config.SecurityBeanOverrideConfiguration;

import com.aitp.dlife.uaa.domain.WechatUser;
import com.aitp.dlife.uaa.repository.WechatUserRepository;
import com.aitp.dlife.uaa.service.WechatUserService;
import com.aitp.dlife.uaa.repository.search.WechatUserSearchRepository;
import com.aitp.dlife.uaa.service.dto.WechatUserDTO;
import com.aitp.dlife.uaa.service.mapper.WechatUserMapper;
import com.aitp.dlife.uaa.web.rest.errors.ExceptionTranslator;

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

import static com.aitp.dlife.uaa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WechatUserResource REST controller.
 *
 * @see WechatUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
public class WechatUserResourceIntTest {

    private static final String DEFAULT_OPEN_ID = "AAAAAAAAAA";
    private static final String UPDATED_OPEN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_WECHAT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT = "BBBBBBBBBB";

    private static final String DEFAULT_SEAT = "AAAAAAAAAA";
    private static final String UPDATED_SEAT = "BBBBBBBBBB";

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @Autowired
    private WechatUserMapper wechatUserMapper;

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WechatUserSearchRepository wechatUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWechatUserMockMvc;

    private WechatUser wechatUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WechatUserResource wechatUserResource = new WechatUserResource(wechatUserService);
        this.restWechatUserMockMvc = MockMvcBuilders.standaloneSetup(wechatUserResource)
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
    public static WechatUser createEntity(EntityManager em) {
        WechatUser wechatUser = new WechatUser()
            .openId(DEFAULT_OPEN_ID)
            .wechatCode(DEFAULT_WECHAT_CODE)
            .userName(DEFAULT_USER_NAME)
            .nickName(DEFAULT_NICK_NAME)
            .avatar(DEFAULT_AVATAR)
            .mobileNum(DEFAULT_MOBILE_NUM)
            .project(DEFAULT_PROJECT)
            .seat(DEFAULT_SEAT);
        return wechatUser;
    }

    @Before
    public void initTest() {
        wechatUserSearchRepository.deleteAll();
        wechatUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createWechatUser() throws Exception {
        int databaseSizeBeforeCreate = wechatUserRepository.findAll().size();

        // Create the WechatUser
        WechatUserDTO wechatUserDTO = wechatUserMapper.toDto(wechatUser);
        restWechatUserMockMvc.perform(post("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatUserDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatUser in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeCreate + 1);
        WechatUser testWechatUser = wechatUserList.get(wechatUserList.size() - 1);
        assertThat(testWechatUser.getOpenId()).isEqualTo(DEFAULT_OPEN_ID);
        assertThat(testWechatUser.getWechatCode()).isEqualTo(DEFAULT_WECHAT_CODE);
        assertThat(testWechatUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testWechatUser.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testWechatUser.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testWechatUser.getMobileNum()).isEqualTo(DEFAULT_MOBILE_NUM);
        assertThat(testWechatUser.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testWechatUser.getSeat()).isEqualTo(DEFAULT_SEAT);

        // Validate the WechatUser in Elasticsearch
        WechatUser wechatUserEs = wechatUserSearchRepository.findOne(testWechatUser.getId());
        assertThat(wechatUserEs).isEqualToIgnoringGivenFields(testWechatUser);
    }

    @Test
    @Transactional
    public void createWechatUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wechatUserRepository.findAll().size();

        // Create the WechatUser with an existing ID
        wechatUser.setId(1L);
        WechatUserDTO wechatUserDTO = wechatUserMapper.toDto(wechatUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWechatUserMockMvc.perform(post("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WechatUser in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOpenIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = wechatUserRepository.findAll().size();
        // set the field null
        wechatUser.setOpenId(null);

        // Create the WechatUser, which fails.
        WechatUserDTO wechatUserDTO = wechatUserMapper.toDto(wechatUser);

        restWechatUserMockMvc.perform(post("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatUserDTO)))
            .andExpect(status().isBadRequest());

        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWechatUsers() throws Exception {
        // Initialize the database
        wechatUserRepository.saveAndFlush(wechatUser);

        // Get all the wechatUserList
        restWechatUserMockMvc.perform(get("/api/wechat-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].openId").value(hasItem(DEFAULT_OPEN_ID.toString())))
            .andExpect(jsonPath("$.[*].wechatCode").value(hasItem(DEFAULT_WECHAT_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].mobileNum").value(hasItem(DEFAULT_MOBILE_NUM.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].seat").value(hasItem(DEFAULT_SEAT.toString())));
    }

    @Test
    @Transactional
    public void getWechatUser() throws Exception {
        // Initialize the database
        wechatUserRepository.saveAndFlush(wechatUser);

        // Get the wechatUser
        restWechatUserMockMvc.perform(get("/api/wechat-users/{id}", wechatUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wechatUser.getId().intValue()))
            .andExpect(jsonPath("$.openId").value(DEFAULT_OPEN_ID.toString()))
            .andExpect(jsonPath("$.wechatCode").value(DEFAULT_WECHAT_CODE.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.mobileNum").value(DEFAULT_MOBILE_NUM.toString()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.toString()))
            .andExpect(jsonPath("$.seat").value(DEFAULT_SEAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWechatUser() throws Exception {
        // Get the wechatUser
        restWechatUserMockMvc.perform(get("/api/wechat-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWechatUser() throws Exception {
        // Initialize the database
        wechatUserRepository.saveAndFlush(wechatUser);
        wechatUserSearchRepository.save(wechatUser);
        int databaseSizeBeforeUpdate = wechatUserRepository.findAll().size();

        // Update the wechatUser
        WechatUser updatedWechatUser = wechatUserRepository.findOne(wechatUser.getId());
        // Disconnect from session so that the updates on updatedWechatUser are not directly saved in db
        em.detach(updatedWechatUser);
        updatedWechatUser
            .openId(UPDATED_OPEN_ID)
            .wechatCode(UPDATED_WECHAT_CODE)
            .userName(UPDATED_USER_NAME)
            .nickName(UPDATED_NICK_NAME)
            .avatar(UPDATED_AVATAR)
            .mobileNum(UPDATED_MOBILE_NUM)
            .project(UPDATED_PROJECT)
            .seat(UPDATED_SEAT);
        WechatUserDTO wechatUserDTO = wechatUserMapper.toDto(updatedWechatUser);

        restWechatUserMockMvc.perform(put("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatUserDTO)))
            .andExpect(status().isOk());

        // Validate the WechatUser in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeUpdate);
        WechatUser testWechatUser = wechatUserList.get(wechatUserList.size() - 1);
        assertThat(testWechatUser.getOpenId()).isEqualTo(UPDATED_OPEN_ID);
        assertThat(testWechatUser.getWechatCode()).isEqualTo(UPDATED_WECHAT_CODE);
        assertThat(testWechatUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testWechatUser.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testWechatUser.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testWechatUser.getMobileNum()).isEqualTo(UPDATED_MOBILE_NUM);
        assertThat(testWechatUser.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testWechatUser.getSeat()).isEqualTo(UPDATED_SEAT);

        // Validate the WechatUser in Elasticsearch
        WechatUser wechatUserEs = wechatUserSearchRepository.findOne(testWechatUser.getId());
        assertThat(wechatUserEs).isEqualToIgnoringGivenFields(testWechatUser);
    }

    @Test
    @Transactional
    public void updateNonExistingWechatUser() throws Exception {
        int databaseSizeBeforeUpdate = wechatUserRepository.findAll().size();

        // Create the WechatUser
        WechatUserDTO wechatUserDTO = wechatUserMapper.toDto(wechatUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWechatUserMockMvc.perform(put("/api/wechat-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wechatUserDTO)))
            .andExpect(status().isCreated());

        // Validate the WechatUser in the database
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWechatUser() throws Exception {
        // Initialize the database
        wechatUserRepository.saveAndFlush(wechatUser);
        wechatUserSearchRepository.save(wechatUser);
        int databaseSizeBeforeDelete = wechatUserRepository.findAll().size();

        // Get the wechatUser
        restWechatUserMockMvc.perform(delete("/api/wechat-users/{id}", wechatUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean wechatUserExistsInEs = wechatUserSearchRepository.exists(wechatUser.getId());
        assertThat(wechatUserExistsInEs).isFalse();

        // Validate the database is empty
        List<WechatUser> wechatUserList = wechatUserRepository.findAll();
        assertThat(wechatUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWechatUser() throws Exception {
        // Initialize the database
        wechatUserRepository.saveAndFlush(wechatUser);
        wechatUserSearchRepository.save(wechatUser);

        // Search the wechatUser
        restWechatUserMockMvc.perform(get("/api/_search/wechat-users?query=id:" + wechatUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wechatUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].openId").value(hasItem(DEFAULT_OPEN_ID.toString())))
            .andExpect(jsonPath("$.[*].wechatCode").value(hasItem(DEFAULT_WECHAT_CODE.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].mobileNum").value(hasItem(DEFAULT_MOBILE_NUM.toString())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].seat").value(hasItem(DEFAULT_SEAT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatUser.class);
        WechatUser wechatUser1 = new WechatUser();
        wechatUser1.setId(1L);
        WechatUser wechatUser2 = new WechatUser();
        wechatUser2.setId(wechatUser1.getId());
        assertThat(wechatUser1).isEqualTo(wechatUser2);
        wechatUser2.setId(2L);
        assertThat(wechatUser1).isNotEqualTo(wechatUser2);
        wechatUser1.setId(null);
        assertThat(wechatUser1).isNotEqualTo(wechatUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WechatUserDTO.class);
        WechatUserDTO wechatUserDTO1 = new WechatUserDTO();
        wechatUserDTO1.setId(1L);
        WechatUserDTO wechatUserDTO2 = new WechatUserDTO();
        assertThat(wechatUserDTO1).isNotEqualTo(wechatUserDTO2);
        wechatUserDTO2.setId(wechatUserDTO1.getId());
        assertThat(wechatUserDTO1).isEqualTo(wechatUserDTO2);
        wechatUserDTO2.setId(2L);
        assertThat(wechatUserDTO1).isNotEqualTo(wechatUserDTO2);
        wechatUserDTO1.setId(null);
        assertThat(wechatUserDTO1).isNotEqualTo(wechatUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wechatUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wechatUserMapper.fromId(null)).isNull();
    }
}
