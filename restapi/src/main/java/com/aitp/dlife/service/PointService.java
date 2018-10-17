package com.aitp.dlife.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.PointEventType;
import com.aitp.dlife.service.dto.EventResultDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.dto.QuestionDTO;
import com.aitp.dlife.service.dto.UserEventDTO;

/**
 * The Class TaskEngineService.
 */
@Service
@Transactional
public class PointService 
{
	@Autowired
	private TaskEngineService taskEngineService;
	@Autowired
	private FitnessActivityService fitnessActivityService;
	@Autowired
	private PinFanActivityService pinFanActivityService;
	@Autowired
	private QuestionService questionService;
	
	/**
	 * Save new event.
	 *
	 * @param userId
	 * @param eventName
	 * @param eventType
	 * @param targetSystem
	 * @param comment 
	 * @return the event result DTO
	 */
	public EventResultDTO saveNewEvent(String userId,String eventName,PointEventType eventType,String targetSystem, String comment) {
		UserEventDTO userEventDTO =new UserEventDTO();
		userEventDTO.setUserid(userId);
		userEventDTO.setEventName(eventName);
		userEventDTO.setEventType(eventType);
		userEventDTO.setTargetSystem(targetSystem);
		userEventDTO.setUuid(UUID.randomUUID().toString());
		if(StringUtils.isNotBlank(comment))
		{
			userEventDTO.setParem2(comment);
		}
		userEventDTO.setEventTime(ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
		return taskEngineService.saveNewEvent(userEventDTO);
	}
	
	public EventResultDTO saveNewEventWithComment(String userId,String eventName,PointEventType eventType,String targetSystem, Long objectId) {
		String title=null;
		if(objectId!=null)
		{
			if(CommentChannel.FIT.toString().equals(targetSystem))
			{
				FitnessActivityDTO fit = fitnessActivityService.findOne(objectId);
				title=fit!=null?fit.getTitle():null;
			}
			else if(CommentChannel.PIN.toString().equals(targetSystem))
			{
				PinFanActivityDTO pin = pinFanActivityService.findOne(objectId);
				title=pin!=null?pin.getActivitiyTile():null;
			}
			else if(CommentChannel.FAQS.toString().equals(targetSystem))
			{
				Optional<QuestionDTO> question = questionService.findOne(objectId);
				title=question.isPresent()?question.get().getTitle():null;
			}
		}
		return saveNewEvent( userId, eventName, eventType, targetSystem,title);
	}
}
