package com.aitp.dlife.web.rest;

import com.aitp.dlife.AitpapiApp;

import com.aitp.dlife.domain.EventMessage;
import com.aitp.dlife.repository.EventMessageRepository;
import com.aitp.dlife.service.EventMessageService;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.mapper.EventMessageMapper;
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

import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.domain.enumeration.EventChannel;
/**
 * Test class for the EventMessageResource REST controller.
 *
 * @see EventMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AitpapiApp.class)
public class EventMessageResourceIntTest {

    private static final String DEFAULT_WECHAT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_WECHAT_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final EventType DEFAULT_TYPE = EventType.CREATE;
    private static final EventType UPDATED_TYPE = EventType.ATTEND;

    private static final EventChannel DEFAULT_CHANNEL = EventChannel.FITNESS;
    private static final EventChannel UPDATED_CHANNEL = EventChannel.PINFAN;

    private static final Long DEFAULT_OBJECT_ID = 1L;
    private static final Long UPDATED_OBJECT_ID = 2L;

    private static final String DEFAULT_OBJECT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EventMessageRepository eventMessageRepository;


    @Autowired
    private EventMessageMapper eventMessageMapper;
    

    @Autowired
    private EventMessageService eventMessageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventMessageMockMvc;

    private EventMessage eventMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventMessageResource eventMessageResource = new EventMessageResource(eventMessageService);
        this.restEventMessageMockMvc = MockMvcBuilders.standaloneSetup(eventMessageResource)
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
    public static EventMessage createEntity(EntityManager em) {
        EventMessage eventMessage = new EventMessage()
            .wechatUserId(DEFAULT_WECHAT_USER_ID)
            .avatar(DEFAULT_AVATAR)
            .nickName(DEFAULT_NICK_NAME)
            .type(DEFAULT_TYPE)
            .channel(DEFAULT_CHANNEL)
            .objectId(DEFAULT_OBJECT_ID)
            .objectTitle(DEFAULT_OBJECT_TITLE)
            .createTime(DEFAULT_CREATE_TIME);
        return eventMessage;
    }

    @Before
    public void initTest() {
        eventMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventMessage() throws Exception {
        int databaseSizeBeforeCreate = eventMessageRepository.findAll().size();

        // Create the EventMessage
        EventMessageDTO eventMessageDTO = eventMessageMapper.toDto(eventMessage);
        restEventMessageMockMvc.perform(post("/api/event-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventMessageDTO)))
            .andExpect(status().isCreated());

        // Validate the EventMessage in the database
        List<EventMessage> eventMessageList = eventMessageRepository.findAll();
        assertThat(eventMessageList).hasSize(databaseSizeBeforeCreate + 1);
        EventMessage testEventMessage = eventMessageList.get(eventMessageList.size() - 1);
        assertThat(testEventMessage.getWechatUserId()).isEqualTo(DEFAULT_WECHAT_USER_ID);
        assertThat(testEventMessage.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testEventMessage.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testEventMessage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEventMessage.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testEventMessage.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testEventMessage.getObjectTitle()).isEqualTo(DEFAULT_OBJECT_TITLE);
        assertThat(testEventMessage.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createEventMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventMessageRepository.findAll().size();

        // Create the EventMessage with an existing ID
        eventMessage.setId(1L);
        EventMessageDTO eventMessageDTO = eventMessageMapper.toDto(eventMessage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMessageMockMvc.perform(post("/api/event-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventMessage in the database
        List<EventMessage> eventMessageList = eventMessageRepository.findAll();
        assertThat(eventMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEventMessages() throws Exception {
        // Initialize the database
        eventMessageRepository.saveAndFlush(eventMessage);

        // Get all the eventMessageList
        restEventMessageMockMvc.perform(get("/api/event-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].wechatUserId").value(hasItem(DEFAULT_WECHAT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].objectTitle").value(hasItem(DEFAULT_OBJECT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    

    @Test
    @Transactional
    public void getEventMessage() throws Exception {
        // Initialize the database
        eventMessageRepository.saveAndFlush(eventMessage);

        // Get the eventMessage
        restEventMessageMockMvc.perform(get("/api/event-messages/{id}", eventMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventMessage.getId().intValue()))
            .andExpect(jsonPath("$.wechatUserId").value(DEFAULT_WECHAT_USER_ID.toString()))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.intValue()))
            .andExpect(jsonPath("$.objectTitle").value(DEFAULT_OBJECT_TITLE.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEventMessage() throws Exception {
        // Get the eventMessage
        restEventMessageMockMvc.perform(get("/api/event-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventMessage() throws Exception {
        // Initialize the database
        eventMessageRepository.saveAndFlush(eventMessage);

        int databaseSizeBeforeUpdate = eventMessageRepository.findAll().size();

        // Update the eventMessage
        EventMessage updatedEventMessage = eventMessageRepository.findById(eventMessage.getId()).get();
        // Disconnect from session so that the updates on updatedEventMessage are not directly saved in db
        em.detach(updatedEventMessage);
        updatedEventMessage
            .wechatUserId(UPDATED_WECHAT_USER_ID)
            .avatar(UPDATED_AVATAR)
            .nickName(UPDATED_NICK_NAME)
            .type(UPDATED_TYPE)
            .channel(UPDATED_CHANNEL)
            .objectId(UPDATED_OBJECT_ID)
            .objectTitle(UPDATED_OBJECT_TITLE)
            .createTime(UPDATED_CREATE_TIME);
        EventMessageDTO eventMessageDTO = eventMessageMapper.toDto(updatedEventMessage);

        restEventMessageMockMvc.perform(put("/api/event-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventMessageDTO)))
            .andExpect(status().isOk());

        // Validate the EventMessage in the database
        List<EventMessage> eventMessageList = eventMessageRepository.findAll();
        assertThat(eventMessageList).hasSize(databaseSizeBeforeUpdate);
        EventMessage testEventMessage = eventMessageList.get(eventMessageList.size() - 1);
        assertThat(testEventMessage.getWechatUserId()).isEqualTo(UPDATED_WECHAT_USER_ID);
        assertThat(testEventMessage.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testEventMessage.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testEventMessage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEventMessage.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testEventMessage.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testEventMessage.getObjectTitle()).isEqualTo(UPDATED_OBJECT_TITLE);
        assertThat(testEventMessage.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingEventMessage() throws Exception {
        int databaseSizeBeforeUpdate = eventMessageRepository.findAll().size();

        // Create the EventMessage
        EventMessageDTO eventMessageDTO = eventMessageMapper.toDto(eventMessage);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventMessageMockMvc.perform(put("/api/event-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventMessage in the database
        List<EventMessage> eventMessageList = eventMessageRepository.findAll();
        assertThat(eventMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEventMessage() throws Exception {
        // Initialize the database
        eventMessageRepository.saveAndFlush(eventMessage);

        int databaseSizeBeforeDelete = eventMessageRepository.findAll().size();

        // Get the eventMessage
        restEventMessageMockMvc.perform(delete("/api/event-messages/{id}", eventMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EventMessage> eventMessageList = eventMessageRepository.findAll();
        assertThat(eventMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventMessage.class);
        EventMessage eventMessage1 = new EventMessage();
        eventMessage1.setId(1L);
        EventMessage eventMessage2 = new EventMessage();
        eventMessage2.setId(eventMessage1.getId());
        assertThat(eventMessage1).isEqualTo(eventMessage2);
        eventMessage2.setId(2L);
        assertThat(eventMessage1).isNotEqualTo(eventMessage2);
        eventMessage1.setId(null);
        assertThat(eventMessage1).isNotEqualTo(eventMessage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventMessageDTO.class);
        EventMessageDTO eventMessageDTO1 = new EventMessageDTO();
        eventMessageDTO1.setId(1L);
        EventMessageDTO eventMessageDTO2 = new EventMessageDTO();
        assertThat(eventMessageDTO1).isNotEqualTo(eventMessageDTO2);
        eventMessageDTO2.setId(eventMessageDTO1.getId());
        assertThat(eventMessageDTO1).isEqualTo(eventMessageDTO2);
        eventMessageDTO2.setId(2L);
        assertThat(eventMessageDTO1).isNotEqualTo(eventMessageDTO2);
        eventMessageDTO1.setId(null);
        assertThat(eventMessageDTO1).isNotEqualTo(eventMessageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventMessageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventMessageMapper.fromId(null)).isNull();
    }
}
