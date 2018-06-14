package com.aitp.dlife.pinfan.web.rest;

import com.aitp.dlife.pinfan.PinfanApp;

import com.aitp.dlife.pinfan.config.SecurityBeanOverrideConfiguration;

import com.aitp.dlife.pinfan.domain.Attendee;
import com.aitp.dlife.pinfan.repository.AttendeeRepository;
import com.aitp.dlife.pinfan.service.AttendeeService;
import com.aitp.dlife.pinfan.repository.search.AttendeeSearchRepository;
import com.aitp.dlife.pinfan.service.dto.AttendeeDTO;
import com.aitp.dlife.pinfan.service.mapper.AttendeeMapper;
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
 * Test class for the AttendeeResource REST controller.
 *
 * @see AttendeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PinfanApp.class, SecurityBeanOverrideConfiguration.class})
public class AttendeeResourceIntTest {

    private static final Long DEFAULT_WECHAT_USER_ID = 1L;
    private static final Long UPDATED_WECHAT_USER_ID = 2L;

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private AttendeeMapper attendeeMapper;

    @Autowired
    private AttendeeService attendeeService;

    @Autowired
    private AttendeeSearchRepository attendeeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttendeeMockMvc;

    private Attendee attendee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendeeResource attendeeResource = new AttendeeResource(attendeeService);
        this.restAttendeeMockMvc = MockMvcBuilders.standaloneSetup(attendeeResource)
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
    public static Attendee createEntity(EntityManager em) {
        Attendee attendee = new Attendee()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .nickName(DEFAULT_NICK_NAME)
            .avatar(DEFAULT_AVATAR);
        return attendee;
    }

    @Before
    public void initTest() {
        attendeeSearchRepository.deleteAll();
        attendee = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendee() throws Exception {
        int databaseSizeBeforeCreate = attendeeRepository.findAll().size();

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);
        restAttendeeMockMvc.perform(post("/api/attendees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeCreate + 1);
        Attendee testAttendee = attendeeList.get(attendeeList.size() - 1);
        assertThat(testAttendee.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testAttendee.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testAttendee.getAvatar()).isEqualTo(DEFAULT_AVATAR);

        // Validate the Attendee in Elasticsearch
        Attendee attendeeEs = attendeeSearchRepository.findOne(testAttendee.getId());
        assertThat(attendeeEs).isEqualToIgnoringGivenFields(testAttendee);
    }

    @Test
    @Transactional
    public void createAttendeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendeeRepository.findAll().size();

        // Create the Attendee with an existing ID
        attendee.setId(1L);
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendeeMockMvc.perform(post("/api/attendees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAttendees() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        // Get all the attendeeList
        restAttendeeMockMvc.perform(get("/api/attendees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendee.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())));
    }

    @Test
    @Transactional
    public void getAttendee() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);

        // Get the attendee
        restAttendeeMockMvc.perform(get("/api/attendees/{id}", attendee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendee.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.intValue()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttendee() throws Exception {
        // Get the attendee
        restAttendeeMockMvc.perform(get("/api/attendees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendee() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);
        attendeeSearchRepository.save(attendee);
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();

        // Update the attendee
        Attendee updatedAttendee = attendeeRepository.findOne(attendee.getId());
        // Disconnect from session so that the updates on updatedAttendee are not directly saved in db
        em.detach(updatedAttendee);
        updatedAttendee
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .nickName(UPDATED_NICK_NAME)
            .avatar(UPDATED_AVATAR);
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(updatedAttendee);

        restAttendeeMockMvc.perform(put("/api/attendees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendeeDTO)))
            .andExpect(status().isOk());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate);
        Attendee testAttendee = attendeeList.get(attendeeList.size() - 1);
        assertThat(testAttendee.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testAttendee.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testAttendee.getAvatar()).isEqualTo(UPDATED_AVATAR);

        // Validate the Attendee in Elasticsearch
        Attendee attendeeEs = attendeeSearchRepository.findOne(testAttendee.getId());
        assertThat(attendeeEs).isEqualToIgnoringGivenFields(testAttendee);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendee() throws Exception {
        int databaseSizeBeforeUpdate = attendeeRepository.findAll().size();

        // Create the Attendee
        AttendeeDTO attendeeDTO = attendeeMapper.toDto(attendee);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttendeeMockMvc.perform(put("/api/attendees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendee in the database
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttendee() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);
        attendeeSearchRepository.save(attendee);
        int databaseSizeBeforeDelete = attendeeRepository.findAll().size();

        // Get the attendee
        restAttendeeMockMvc.perform(delete("/api/attendees/{id}", attendee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean attendeeExistsInEs = attendeeSearchRepository.exists(attendee.getId());
        assertThat(attendeeExistsInEs).isFalse();

        // Validate the database is empty
        List<Attendee> attendeeList = attendeeRepository.findAll();
        assertThat(attendeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAttendee() throws Exception {
        // Initialize the database
        attendeeRepository.saveAndFlush(attendee);
        attendeeSearchRepository.save(attendee);

        // Search the attendee
        restAttendeeMockMvc.perform(get("/api/_search/attendees?query=id:" + attendee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendee.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendee.class);
        Attendee attendee1 = new Attendee();
        attendee1.setId(1L);
        Attendee attendee2 = new Attendee();
        attendee2.setId(attendee1.getId());
        assertThat(attendee1).isEqualTo(attendee2);
        attendee2.setId(2L);
        assertThat(attendee1).isNotEqualTo(attendee2);
        attendee1.setId(null);
        assertThat(attendee1).isNotEqualTo(attendee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendeeDTO.class);
        AttendeeDTO attendeeDTO1 = new AttendeeDTO();
        attendeeDTO1.setId(1L);
        AttendeeDTO attendeeDTO2 = new AttendeeDTO();
        assertThat(attendeeDTO1).isNotEqualTo(attendeeDTO2);
        attendeeDTO2.setId(attendeeDTO1.getId());
        assertThat(attendeeDTO1).isEqualTo(attendeeDTO2);
        attendeeDTO2.setId(2L);
        assertThat(attendeeDTO1).isNotEqualTo(attendeeDTO2);
        attendeeDTO1.setId(null);
        assertThat(attendeeDTO1).isNotEqualTo(attendeeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attendeeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attendeeMapper.fromId(null)).isNull();
    }
}
