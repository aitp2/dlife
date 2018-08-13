package com.aitp.dlife.service;

import com.aitp.dlife.domain.Message;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.repository.MessageRepository;
import com.aitp.dlife.service.dto.*;
import com.aitp.dlife.service.mapper.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final PinFanActivityService pinFanActivityService;

    private final FitnessActivityService fitnessActivityService;

    private final ActivityParticipationService activityParticipationService;

    private final QuestionService questionService;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper,PinFanActivityService pinFanActivityService,
                          FitnessActivityService fitnessActivityService,ActivityParticipationService activityParticipationService,
                          QuestionService questionService)
    {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.pinFanActivityService = pinFanActivityService;
        this.fitnessActivityService = fitnessActivityService;
        this.activityParticipationService = activityParticipationService;
        this.questionService = questionService;
    }

    /**
     * Save a message.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    public MessageDTO save(MessageDTO messageDTO) {
        log.debug("Request to save Message : {}", messageDTO);
        Message message = messageMapper.toEntity(messageDTO);
        message = messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        return messageRepository.findAll(pageable)
            .map(messageMapper::toDto);
    }


    /**
     * Get one message by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MessageDTO> findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        return messageRepository.findById(id)
            .map(messageMapper::toDto);
    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.deleteById(id);
    }

    /**
     * find the message by user.
     *
     * @param wechatUserId the id of the entity
     */
    public List<MessageDTO> findMessageByUser(String wechatUserId,String type, boolean read){

        List<Message>  list  = messageRepository.findMessageByUser(wechatUserId,read,EventType.getMessageTypeList(type).stream().map(EventType::toString).collect(Collectors.toList()));

        return list.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

    /**
     * read the message.
     *
     * @param toReadDTO the id of the entity
     */
    public void markAsRead(List<MessageDTO> toReadDTO){
        for (MessageDTO dto:toReadDTO){
            dto.setRead(true);
            save(dto);
        }
    }

    @Async
    public void createMessageForEvent(EventMessageDTO dto) {
        List<String> userIds = getUserListForMessage(dto);
        if (userIds.size()>0){
            this.insertMessage(userIds,dto);
        }
    }

    private List<String> getUserListForMessage(EventMessageDTO dto){
        List<String> userIds = new ArrayList<>();
        EventType type = dto.getType();
        EventChannel channel = dto.getChannel();
        //小邀约
        if(EventChannel.PINFAN.equals(channel)){
            PinFanActivityDTO pinFan = pinFanActivityService.findOne(dto.getObjectId());
            if (null==pinFan){
                return userIds;
            }
            //场景：加入小邀约/退出小邀约/评论小邀约，消息对象：发起人
            if(EventType.ATTEND.equals(type) || EventType.QUIT.equals(type) || EventType.COMMENT.equals(type)){
                userIds.add(pinFan.getWechatUserId());
            }
            //场景：修改小邀约/取消小邀约，消息对象：发起人和参与人
            else if (EventType.UPDATE.equals(type) || EventType.CANCEL.equals(type)){
                Set<AttendeeDTO> attendeeDTOS = pinFan.getAttendees();
                for(AttendeeDTO attendeeDTO :attendeeDTOS){
                    userIds.add(attendeeDTO.getWechatUserId());
                }
            }
        }
        //小目标
        else if (EventChannel.FITNESS.equals(channel)){
            FitnessActivityDTO fitness = fitnessActivityService.findOne(dto.getObjectId());
            if (null==fitness){
                return userIds;
            }
            //场景：加入小目标/退出小目标/评论小目标，消息对象：发起人
            if (EventType.ATTEND.equals(type) || EventType.QUIT.equals(type) || EventType.COMMENT.equals(type)){
                userIds.add(fitness.getWechatUserId());
            }
            //场景：修改小目标，消息对象：发起人和参与人
            else if (EventType.UPDATE.equals(type)){
                List<ActivityParticipationDTO> dtos = activityParticipationService.findByActivity(dto.getObjectId());
                if (!CollectionUtils.isEmpty(dtos)){
                    for (ActivityParticipationDTO activityParticipationDTO:dtos){
                        userIds.add(activityParticipationDTO.getWechatUserId());
                    }
                }
            }
        }
        //小问答
        else if(EventChannel.FAQS.equals(channel)){
            Optional<QuestionDTO> question = questionService.findOne(dto.getObjectId());
            QuestionDTO questionDTO;
            if (question.isPresent()){
                questionDTO = question.get();
            }else{
                return userIds;
            }
            //场景：回答小问答，消息对象：提问者
            if (EventType.COMMENT.equals(type)){
                userIds.add(questionDTO.getWechatUserId());
                return userIds;
            }
        }
        return  userIds;
    }

    private void insertMessage(List<String> userIds, EventMessageDTO dto){
        for(String id:userIds){
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setEventMessageId(dto.getId());
            messageDTO.setRead(false);
            messageDTO.setWechatUserId(id);
            messageDTO.setEventMessage(dto);
            save(messageDTO);
        }
    }
}
