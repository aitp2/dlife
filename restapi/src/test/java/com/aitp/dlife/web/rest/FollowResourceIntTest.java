package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.Follow;
import com.aitp.dlife.repository.FollowRepository;
import com.aitp.dlife.service.FollowService;
import com.aitp.dlife.service.dto.FollowDTO;
import com.aitp.dlife.service.mapper.FollowMapper;
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
 * Test class for the FollowResource REST controller.
 *
 * @see FollowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class FollowResourceIntTest {

    private static final String DEFAULT_FOLLOW_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FOLLOW_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FOLLOW_USER_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_FOLLOW_USER_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FOLLOW_USERAVATAR = "AAAAAAAAAA";
    private static final String UPDATED_FOLLOW_USERAVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_FOLLOWED_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FOLLOWED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FOLLOWED_USER_NICKNAME = "AAAAAAAAAA";
    private static final String UPDATED_FOLLOWED_USER_NICKNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FOLLOWED_USERAVATAR = "AAAAAAAAAA";
    private static final String UPDATED_FOLLOWED_USERAVATAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FollowRepository followRepository;


    @Autowired
    private FollowMapper followMapper;
    

    @Autowired
    private FollowService followService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFollowMockMvc;

    private Follow follow;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FollowResource followResource = new FollowResource(followService);
        this.restFollowMockMvc = MockMvcBuilders.standaloneSetup(followResource)
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
    public static Follow createEntity(EntityManager em) {
        Follow follow = new Follow()
            .followUserId(DEFAULT_FOLLOW_USER_ID)
            .followUserNickname(DEFAULT_FOLLOW_USER_NICKNAME)
            .followUseravatar(DEFAULT_FOLLOW_USERAVATAR)
            .followedUserId(DEFAULT_FOLLOWED_USER_ID)
            .followedUserNickname(DEFAULT_FOLLOWED_USER_NICKNAME)
            .followedUseravatar(DEFAULT_FOLLOWED_USERAVATAR)
            .createTime(DEFAULT_CREATE_TIME)
            .modifyTime(DEFAULT_MODIFY_TIME);
        return follow;
    }

    @Before
    public void initTest() {
        follow = createEntity(em);
    }

    @Test
    @Transactional
    public void createFollow() throws Exception {
        int databaseSizeBeforeCreate = followRepository.findAll().size();

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);
        restFollowMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isCreated());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeCreate + 1);
        Follow testFollow = followList.get(followList.size() - 1);
        assertThat(testFollow.getFollowUserId()).isEqualTo(DEFAULT_FOLLOW_USER_ID);
        assertThat(testFollow.getFollowUserNickname()).isEqualTo(DEFAULT_FOLLOW_USER_NICKNAME);
        assertThat(testFollow.getFollowUseravatar()).isEqualTo(DEFAULT_FOLLOW_USERAVATAR);
        assertThat(testFollow.getFollowedUserId()).isEqualTo(DEFAULT_FOLLOWED_USER_ID);
        assertThat(testFollow.getFollowedUserNickname()).isEqualTo(DEFAULT_FOLLOWED_USER_NICKNAME);
        assertThat(testFollow.getFollowedUseravatar()).isEqualTo(DEFAULT_FOLLOWED_USERAVATAR);
        assertThat(testFollow.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testFollow.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void createFollowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followRepository.findAll().size();

        // Create the Follow with an existing ID
        follow.setId(1L);
        FollowDTO followDTO = followMapper.toDto(follow);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFollows() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get all the followList
        restFollowMockMvc.perform(get("/api/follows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follow.getId().intValue())))
            .andExpect(jsonPath("$.[*].followUserId").value(hasItem(DEFAULT_FOLLOW_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].followUserNickname").value(hasItem(DEFAULT_FOLLOW_USER_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].followUseravatar").value(hasItem(DEFAULT_FOLLOW_USERAVATAR.toString())))
            .andExpect(jsonPath("$.[*].followedUserId").value(hasItem(DEFAULT_FOLLOWED_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].followedUserNickname").value(hasItem(DEFAULT_FOLLOWED_USER_NICKNAME.toString())))
            .andExpect(jsonPath("$.[*].followedUseravatar").value(hasItem(DEFAULT_FOLLOWED_USERAVATAR.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(DEFAULT_MODIFY_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", follow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(follow.getId().intValue()))
            .andExpect(jsonPath("$.followUserId").value(DEFAULT_FOLLOW_USER_ID.toString()))
            .andExpect(jsonPath("$.followUserNickname").value(DEFAULT_FOLLOW_USER_NICKNAME.toString()))
            .andExpect(jsonPath("$.followUseravatar").value(DEFAULT_FOLLOW_USERAVATAR.toString()))
            .andExpect(jsonPath("$.followedUserId").value(DEFAULT_FOLLOWED_USER_ID.toString()))
            .andExpect(jsonPath("$.followedUserNickname").value(DEFAULT_FOLLOWED_USER_NICKNAME.toString()))
            .andExpect(jsonPath("$.followedUseravatar").value(DEFAULT_FOLLOWED_USERAVATAR.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.modifyTime").value(DEFAULT_MODIFY_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFollow() throws Exception {
        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        int databaseSizeBeforeUpdate = followRepository.findAll().size();

        // Update the follow
        Follow updatedFollow = followRepository.findById(follow.getId()).get();
        // Disconnect from session so that the updates on updatedFollow are not directly saved in db
        em.detach(updatedFollow);
        updatedFollow
            .followUserId(UPDATED_FOLLOW_USER_ID)
            .followUserNickname(UPDATED_FOLLOW_USER_NICKNAME)
            .followUseravatar(UPDATED_FOLLOW_USERAVATAR)
            .followedUserId(UPDATED_FOLLOWED_USER_ID)
            .followedUserNickname(UPDATED_FOLLOWED_USER_NICKNAME)
            .followedUseravatar(UPDATED_FOLLOWED_USERAVATAR)
            .createTime(UPDATED_CREATE_TIME)
            .modifyTime(UPDATED_MODIFY_TIME);
        FollowDTO followDTO = followMapper.toDto(updatedFollow);

        restFollowMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isOk());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeUpdate);
        Follow testFollow = followList.get(followList.size() - 1);
        assertThat(testFollow.getFollowUserId()).isEqualTo(UPDATED_FOLLOW_USER_ID);
        assertThat(testFollow.getFollowUserNickname()).isEqualTo(UPDATED_FOLLOW_USER_NICKNAME);
        assertThat(testFollow.getFollowUseravatar()).isEqualTo(UPDATED_FOLLOW_USERAVATAR);
        assertThat(testFollow.getFollowedUserId()).isEqualTo(UPDATED_FOLLOWED_USER_ID);
        assertThat(testFollow.getFollowedUserNickname()).isEqualTo(UPDATED_FOLLOWED_USER_NICKNAME);
        assertThat(testFollow.getFollowedUseravatar()).isEqualTo(UPDATED_FOLLOWED_USERAVATAR);
        assertThat(testFollow.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testFollow.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingFollow() throws Exception {
        int databaseSizeBeforeUpdate = followRepository.findAll().size();

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFollowMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        int databaseSizeBeforeDelete = followRepository.findAll().size();

        // Get the follow
        restFollowMockMvc.perform(delete("/api/follows/{id}", follow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follow.class);
        Follow follow1 = new Follow();
        follow1.setId(1L);
        Follow follow2 = new Follow();
        follow2.setId(follow1.getId());
        assertThat(follow1).isEqualTo(follow2);
        follow2.setId(2L);
        assertThat(follow1).isNotEqualTo(follow2);
        follow1.setId(null);
        assertThat(follow1).isNotEqualTo(follow2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FollowDTO.class);
        FollowDTO followDTO1 = new FollowDTO();
        followDTO1.setId(1L);
        FollowDTO followDTO2 = new FollowDTO();
        assertThat(followDTO1).isNotEqualTo(followDTO2);
        followDTO2.setId(followDTO1.getId());
        assertThat(followDTO1).isEqualTo(followDTO2);
        followDTO2.setId(2L);
        assertThat(followDTO1).isNotEqualTo(followDTO2);
        followDTO1.setId(null);
        assertThat(followDTO1).isNotEqualTo(followDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(followMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(followMapper.fromId(null)).isNull();
    }
}
