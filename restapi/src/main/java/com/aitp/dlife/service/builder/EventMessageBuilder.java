package com.aitp.dlife.service.builder;


import java.util.Date;

import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.service.dto.AttendeeDTO;
import com.aitp.dlife.service.dto.CommentDTO;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.dto.ReplyDTO;
import com.aitp.dlife.service.dto.ThumbsUpDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.web.rest.errors.EventMessageBuildErrorException;
import com.aitp.dlife.web.rest.util.DateUtil;

public class EventMessageBuilder {

	private EventMessageDTO eventMessageDTO;

	public EventMessageBuilder(EventMessageDTO eventMessageDTO) {
		super();
		this.eventMessageDTO = eventMessageDTO;
	}

	public static EventMessageBuilder buildEventMessageDTO(ActivityParticipationDTO activityParticipationDTO) {
		return new EventMessageBuilder(new EventMessageDTO(activityParticipationDTO.getWechatUserId(),
				activityParticipationDTO.getAvatar(), activityParticipationDTO.getNickName(), EventType.CLOCKIN,
				EventChannel.FITNESS, activityParticipationDTO.getActivityId(),
				activityParticipationDTO.getActivityTitle(), DateUtil.getYMDDateString(new Date()), null, null));
	}

	public static EventMessageBuilder buildEventMessageDTO(WechatUserDTO wechatUserDTO,
			FitnessActivityDTO fitessActivityDTO) {
		return new EventMessageBuilder(new EventMessageDTO(wechatUserDTO.getId().toString(), wechatUserDTO.getAvatar(),
				wechatUserDTO.getNickName(), EventType.ATTEND, EventChannel.FITNESS, fitessActivityDTO.getId(),
				fitessActivityDTO.getTitle(), DateUtil.getYMDDateString(new Date()), null,null));
	}
	
	public static EventMessageBuilder buildEventMessageDTO(AttendeeDTO attendeeDTO) {
		return new EventMessageBuilder(new EventMessageDTO(attendeeDTO.getWechatUserId(), attendeeDTO.getAvatar(),
				attendeeDTO.getNickName(), EventType.ATTEND, EventChannel.PINFAN, attendeeDTO.getPinFanActivityId(),
				attendeeDTO.getActivitiyTile(), DateUtil.getYMDDateString(new Date()), null,null));
	}

	public static EventMessageBuilder buildEventMessageDTO(PinFanActivityDTO pinFanActivityDTO) {
		return new EventMessageBuilder(new EventMessageDTO(pinFanActivityDTO.getWechatUserId(), pinFanActivityDTO.getAvatar(),
				pinFanActivityDTO.getNickName(), EventType.UPDATE, EventChannel.PINFAN, pinFanActivityDTO.getId(),
				pinFanActivityDTO.getActivitiyTile(), DateUtil.getYMDDateString(new Date()), null,null));
	}

	public static  EventMessageBuilder buildEventMessageDTO(CommentDTO commentDTO) {
		return new EventMessageBuilder(new EventMessageDTO(commentDTO.getWechatUserId(), commentDTO.getAvatar(),
				commentDTO.getNickName(), EventType.UPDATE, EventChannel.PINFAN, commentDTO.getObjectId(),
				null, DateUtil.getYMDDateString(new Date()), commentDTO.getContent(),commentDTO.getId()));
	}
	
	public static  EventMessageBuilder buildEventMessageDTO(ReplyDTO commentDTO) {
		return new EventMessageBuilder(new EventMessageDTO(commentDTO.getWechatUserId(), commentDTO.getAvatar(),
				commentDTO.getNickName(), EventType.REPLY, EventChannel.PINFAN,null ,
				null, DateUtil.getYMDDateString(new Date()), commentDTO.getContent(),commentDTO.getParentId(),commentDTO.getId()));
	}
	
	
	public static  EventMessageBuilder buildEventMessageDTO(ThumbsUpDTO thumbsUpDTO) {
		return new EventMessageBuilder(new EventMessageDTO(thumbsUpDTO.getWechatUserId(), thumbsUpDTO.getAvatar(),
				thumbsUpDTO.getNickName(), EventType.COMMENTTHUMBSUP, EventChannel.FITNESS,null ,
				null, DateUtil.getYMDDateString(new Date()), null,thumbsUpDTO.getObjectId(),null));
	}
	
	public EventMessageDTO get() {
		if(null==eventMessageDTO.getObjectId()){
			throw new EventMessageBuildErrorException();
		}
		return eventMessageDTO;
	}

	public EventMessageBuilder type(EventType eventType) {

		this.eventMessageDTO.setType(eventType);

		return this;
	}
	
	public EventMessageBuilder reply(Long replyId) {

		this.eventMessageDTO.setReplyId(replyId);

		return this;
	}
	
	public EventMessageBuilder object(Long objectId) {

		this.eventMessageDTO.setObjectId(objectId);

		return this;
	}

	public EventMessageBuilder title(String title) {
		this.eventMessageDTO.setObjectTitle(title);
		return this;
	}
	
	
	public EventMessageBuilder Channel(EventChannel channel) {
		this.eventMessageDTO.setChannel(channel);
		return this;
	}
}
