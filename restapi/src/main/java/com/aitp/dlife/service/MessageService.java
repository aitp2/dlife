package com.aitp.dlife.service;

import java.util.*;
import java.util.stream.Collectors;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.domain.enumeration.MessageType;
import com.aitp.dlife.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.service.dto.AttendeeDTO;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.MessageDTO;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.dto.QuestionDTO;
import com.aitp.dlife.service.mapper.MessageMapper;
import org.thymeleaf.util.MapUtils;

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

    private final CommentRepository commentRepository;

    private final ClockInRepository clockInRepository;

    private final FollowRepository followRepository;

    private final QuestionRepository questionRepository;

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper, PinFanActivityService pinFanActivityService,
                          FitnessActivityService fitnessActivityService, ActivityParticipationService activityParticipationService,
                          QuestionRepository questionRepository, CommentRepository commentRepository,
                          ClockInRepository clockInRepository, FollowRepository followRepository)
    {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.pinFanActivityService = pinFanActivityService;
        this.fitnessActivityService = fitnessActivityService;
        this.activityParticipationService = activityParticipationService;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
        this.clockInRepository = clockInRepository;
        this.followRepository = followRepository;
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
        Map<MessageType,List<String>> userIds = getUserListForMessage(dto);
        if (userIds.size()>0){
            this.insertMessage(userIds,dto);
        }
    }

    private Map<MessageType,List<String>> getUserListForMessage(EventMessageDTO dto){
        Map<MessageType,List<String>> result = new HashMap<>();
        List<String> commonUserIds = new ArrayList<>();
        List<String> followUserIds = new ArrayList<>();
        result.put(MessageType.COMMON,commonUserIds);
        result.put(MessageType.FOLLOW,followUserIds);

        EventType type = dto.getType();
        EventChannel channel = dto.getChannel();
        //小邀约
        if(EventChannel.PINFAN.equals(channel)){
            PinFanActivityDTO pinFan = pinFanActivityService.findOne(dto.getObjectId());
            if (null==pinFan){
                return result;
            }
            //场景：加入小邀约/退出小邀约/评论小邀约，消息对象：发起人
            if(EventType.ATTEND.equals(type) || EventType.QUIT.equals(type) || EventType.COMMENT.equals(type)){
                commonUserIds.add(pinFan.getWechatUserId());
            }
            //场景：修改小邀约/取消小邀约，消息对象：发起人和参与人
            else if (EventType.UPDATE.equals(type) || EventType.CANCEL.equals(type)){
                Set<AttendeeDTO> attendeeDTOS = pinFan.getAttendees();
                for(AttendeeDTO attendeeDTO :attendeeDTOS){
                    commonUserIds.add(attendeeDTO.getWechatUserId());
                }
            }

            //场景：创建/参加小邀约 消息对象：创建人或者参加人的粉丝
            if (EventType.ATTEND.equals(type) || EventType.CREATE.equals(type)){
                List<String> follows = getFollowUsers(dto.getWechatUserId());
                if (!CollectionUtils.isEmpty(follows)){
                    followUserIds.addAll(follows);
                }
            }
        }
        //小目标
        else if (EventChannel.FITNESS.equals(channel)){
            FitnessActivityDTO fitness = fitnessActivityService.findOne(dto.getObjectId());
            if (null==fitness){
                return result;
            }
            //场景：加入小目标/退出小目标/评论小目标，消息对象：发起人
            if (EventType.ATTEND.equals(type) || EventType.QUIT.equals(type) || EventType.COMMENT.equals(type)){
                commonUserIds.add(fitness.getWechatUserId());
            }
            //场景：修改小目标，消息对象：发起人和参与人
            else if (EventType.UPDATE.equals(type)){
                List<ActivityParticipationDTO> dtos = activityParticipationService.findByActivity(dto.getObjectId());
                if (!CollectionUtils.isEmpty(dtos)){
                    for (ActivityParticipationDTO activityParticipationDTO:dtos){
                        commonUserIds.add(activityParticipationDTO.getWechatUserId());
                    }
                }
            }

            //场景：创建/打卡/参加小目标 消息对象：创建人或者参加人或者打卡人的粉丝
            if (EventType.ATTEND.equals(type) || EventType.CREATE.equals(type) || EventType.CLOCKIN.equals(type)){
                List<String> follows = getFollowUsers(dto.getWechatUserId());
                if (!CollectionUtils.isEmpty(follows)){
                    followUserIds.addAll(follows);
                }
            }
        }
        //小问答
        else if(EventChannel.FAQS.equals(channel)){
            Optional<Question> questionOptional = questionRepository.findById(dto.getObjectId());
            Question question;
            if (questionOptional.isPresent()){
                question = questionOptional.get();
            }else{
                return result;
            }
            //场景：回答小问答，消息对象：提问者
            if (EventType.COMMENT.equals(type)){
                commonUserIds.add(question.getWechatUserId());
            }

            //场景：创建/回答小问答 消息对象：创建人或者回答人的粉丝
            if (EventType.COMMENT.equals(type) || EventType.CREATE.equals(type)){
                List<String> follows = getFollowUsers(dto.getWechatUserId());
                if (!CollectionUtils.isEmpty(follows)){
                    followUserIds.addAll(follows);
                }
            }
        }
        //场景：回复场景任何回复信息提示
        if (EventType.REPLY.equals(type)){
         	Comment comment = commentRepository.getOne(dto.getReplyId());
            commonUserIds.add(comment.getRpWechatUserId().toString());
        }
        //场景：回答小问答，消息对象：提问者
        if (EventType.COMMENTTHUMBSUP.equals(type)){
        	Comment comment = commentRepository.getOne(dto.getParagraphId());
            commonUserIds.add(comment.getWechatUserId().toString());
        }
        if (EventType.CLOCKTHUMBSUP.equals(type)){
        	ClockIn clockIn = clockInRepository.getOne(dto.getParagraphId());
        	ActivityParticipationDTO activityParticipationDTO = activityParticipationService.findOne(clockIn.getActivityParticipation().getId()).get();
            commonUserIds.add(activityParticipationDTO.getWechatUserId());
        }
        //消息的触发人和接收人相同时不用发送消息
        Iterator<String> it =  commonUserIds.iterator();
        while (it.hasNext()){
            String id = it.next();
            if (dto.getWechatUserId().equals(id)){
                it.remove();
            }
        }
        return  result;
    }

    /**
     * insert the message
     *
     * @param userIds
     * @param dto
     */
    private void insertMessage(Map<MessageType,List<String>> userIds, EventMessageDTO dto){
        if (!MapUtils.isEmpty(userIds)){
            for(String id:userIds.get(MessageType.COMMON)){
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setEventMessageId(dto.getId());
                messageDTO.setMessageType(MessageType.COMMON);
                messageDTO.setRead(false);
                messageDTO.setWechatUserId(id);
                messageDTO.setEventMessage(dto);
                save(messageDTO);
            }
            for(String id:userIds.get(MessageType.FOLLOW)){
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setEventMessageId(dto.getId());
                messageDTO.setMessageType(MessageType.FOLLOW);
                messageDTO.setRead(false);
                messageDTO.setWechatUserId(id);
                messageDTO.setEventMessage(dto);
                save(messageDTO);
            }
        }

    }

    /**
     * get the follow users
     *
     * @param followedUserId
     * @return List<String>
     */
    protected List<String> getFollowUsers(String followedUserId){
        List<String> result = new ArrayList<>();

        List<Follow> follows = followRepository.findAllByFollowedUserId(followedUserId);
        if (!CollectionUtils.isEmpty(follows)){
            for(Follow follow : follows){
                result.add(follow.getFollowUserId());
            }
        }

        return result;
    }

    public int getMessageCount(String wechatUserId,String type, boolean read){
        return messageRepository.countMessageByUser(wechatUserId,read,EventType.getMessageTypeList(type).stream().map(EventType::toString).collect(Collectors.toList()));
    }
}
