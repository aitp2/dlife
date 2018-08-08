package com.aitp.dlife.service;

import com.aitp.dlife.domain.EventMessage;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.repository.EventMessageRepository;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.mapper.EventMessageMapper;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;
/**
 * Service Implementation for managing EventMessage.
 */
@Service
@Transactional
public class EventMessageService {

    private final Logger log = LoggerFactory.getLogger(EventMessageService.class);

    private final EventMessageRepository eventMessageRepository;

    private final EventMessageMapper eventMessageMapper;

    public EventMessageService(EventMessageRepository eventMessageRepository, EventMessageMapper eventMessageMapper) {
        this.eventMessageRepository = eventMessageRepository;
        this.eventMessageMapper = eventMessageMapper;
    }

    /**
     * Save a eventMessage.
     *
     * @param eventMessageDTO the entity to save
     * @return the persisted entity
     */
    public EventMessageDTO save(EventMessageDTO eventMessageDTO) {
        log.debug("Request to save EventMessage : {}", eventMessageDTO);
        EventMessage eventMessage = eventMessageMapper.toEntity(eventMessageDTO);
        eventMessage = eventMessageRepository.save(eventMessage);
        return eventMessageMapper.toDto(eventMessage);
    }

    /**
     * Get all the eventMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EventMessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventMessages");
        return eventMessageRepository.findAll(pageable)
            .map(eventMessageMapper::toDto);
    }

    /**
     * Get all the event message by channel.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EventMessageDTO> findAllForChannel(Pageable pageable, String channel) {
        log.debug("Request to get all Event for channel");

        for(EventChannel eventChannel:EventChannel.values()){
            if(channel.toUpperCase().equals(eventChannel.toString())){
                return eventMessageRepository.findAllForChannel(pageable,eventChannel)
                    .map(eventMessageMapper::toDto);
            }
        }
        throw new BadRequestAlertException("There is no event for current request","EventMessage","暂无动态");
    }

    /**
     * Get all the event message by object id.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EventMessageDTO> findAllForObjectId(Pageable pageable,String objectId) {
        log.debug("Request to get all Event for object");

        return eventMessageRepository.findAllForObjectId(pageable,Long.valueOf(objectId))
            .map(eventMessageMapper::toDto);
    }


    /**
     * Get one eventMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EventMessageDTO> findOne(Long id) {
        log.debug("Request to get EventMessage : {}", id);
        return eventMessageRepository.findById(id)
            .map(eventMessageMapper::toDto);
    }

    /**
     * Delete the eventMessage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EventMessage : {}", id);
        eventMessageRepository.deleteById(id);
    }

    /**
     * record the event message
     *
     * @param eventChannel the event channel
     * @param createTime the event create time
     * @param eventType the event type
     * @param wechatUserId the event trigger user's wechat user id
     * @param objectTitle the object title
     * @param objectId the object id
     * @param avatar the event trigger user's avatar
     * @param nickName the event trigger user's nickName
     * @return EventMessageDTO
     */
    public EventMessageDTO recordEventMessage(EventChannel eventChannel, String createTime, EventType eventType,
                                   String wechatUserId, String objectTitle, Long objectId, String avatar,
                                   String nickName){
        EventMessageDTO eventMessageDTO = new EventMessageDTO();
        eventMessageDTO.setChannel(eventChannel);
        eventMessageDTO.setCreateTime(createTime);
        eventMessageDTO.setType(eventType);
        eventMessageDTO.setWechatUserId(wechatUserId);
        eventMessageDTO.setObjectTitle(objectTitle);
        eventMessageDTO.setObjectId(objectId);
        eventMessageDTO.setAvatar(avatar);
        eventMessageDTO.setNickName(nickName);
        return save(eventMessageDTO);
    }
}
