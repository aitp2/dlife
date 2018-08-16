package com.aitp.dlife.service.dto.builder;

import java.util.Date;

import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.service.dto.AttendeeDTO;
import com.aitp.dlife.service.dto.CommentDTO;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
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
				activityParticipationDTO.getActivityTitle(), DateUtil.getYMDDateString(new Date()), null));
	}

	public static EventMessageBuilder buildEventMessageDTO(WechatUserDTO wechatUserDTO,
			FitnessActivityDTO fitessActivityDTO) {
		return new EventMessageBuilder(new EventMessageDTO(wechatUserDTO.getId().toString(), wechatUserDTO.getAvatar(),
				wechatUserDTO.getNickName(), EventType.ATTEND, EventChannel.FITNESS, fitessActivityDTO.getId(),
				fitessActivityDTO.getTitle(), DateUtil.getYMDDateString(new Date()), null));
	}
	
	public static EventMessageBuilder buildEventMessageDTO(AttendeeDTO attendeeDTO) {
		return new EventMessageBuilder(new EventMessageDTO(attendeeDTO.getWechatUserId(), attendeeDTO.getAvatar(),
				attendeeDTO.getNickName(), EventType.ATTEND, EventChannel.PINFAN, attendeeDTO.getPinFanActivityId(),
				attendeeDTO.getActivitiyTile(), DateUtil.getYMDDateString(new Date()), null));
	}

	public static EventMessageBuilder buildEventMessageDTO(PinFanActivityDTO pinFanActivityDTO) {
		return new EventMessageBuilder(new EventMessageDTO(pinFanActivityDTO.getWechatUserId(), pinFanActivityDTO.getAvatar(),
				pinFanActivityDTO.getNickName(), EventType.UPDATE, EventChannel.PINFAN, pinFanActivityDTO.getId(),
				pinFanActivityDTO.getActivitiyTile(), DateUtil.getYMDDateString(new Date()), null));
	}

	public static  EventMessageBuilder buildEventMessageDTO(CommentDTO commentDTO) {
		return new EventMessageBuilder(new EventMessageDTO(commentDTO.getWechatUserId(), commentDTO.getAvatar(),
				commentDTO.getNickName(), EventType.UPDATE, EventChannel.PINFAN, commentDTO.getObjectId(),
				null, DateUtil.getYMDDateString(new Date()), commentDTO.getContent()));
	}
	
	
	public EventMessageDTO get() {
		
		return eventMessageDTO;
	}

	public EventMessageBuilder type(EventType eventType) {

		this.eventMessageDTO.setType(eventType);

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
