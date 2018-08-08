package com.aitp.dlife.service;

import com.aitp.dlife.domain.Attendee;
import com.aitp.dlife.domain.enumeration.EventChannel;
import com.aitp.dlife.domain.enumeration.EventType;
import com.aitp.dlife.repository.AttendeeRepository;
import com.aitp.dlife.service.dto.AttendeeDTO;
import com.aitp.dlife.service.mapper.AttendeeMapper;
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
 * Service Implementation for managing Attendee.
 */
@Service
@Transactional
public class AttendeeService {

    private final Logger log = LoggerFactory.getLogger(AttendeeService.class);

    private final AttendeeRepository attendeeRepository;

    private final AttendeeMapper attendeeMapper;

    private final EventMessageService eventMessageService;

    public AttendeeService(AttendeeRepository attendeeRepository, AttendeeMapper attendeeMapper, EventMessageService eventMessageService) {
        this.attendeeRepository = attendeeRepository;
        this.attendeeMapper = attendeeMapper;
        this.eventMessageService = eventMessageService;
    }

    /**
     * Save a attendee.
     *
     * @param attendeeDTO the entity to save
     * @return the persisted entity
     */
    public AttendeeDTO save(AttendeeDTO attendeeDTO) {
        log.debug("Request to save Attendee : {}", attendeeDTO);
        Attendee attendee = attendeeMapper.toEntity(attendeeDTO);
        attendee = attendeeRepository.save(attendee);
        AttendeeDTO dto = attendeeMapper.toDto(attendee);

        //record the activity participation event start
        eventMessageService.recordEventMessage(EventChannel.PINFAN,DateUtil.getYMDDateString(new Date()), EventType.ATTEND,
            attendeeDTO.getWechatUserId(),attendeeDTO.getActivitiyTile(),dto.getPinFanActivityId(),attendeeDTO.getAvatar(),attendeeDTO.getNickName());
        //record the activity participation event end

        return dto;
    }

    /**
     * Get all the attendees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attendees");
        return attendeeRepository.findAll(pageable)
            .map(attendeeMapper::toDto);
    }

    /**
     * Get one attendee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AttendeeDTO findOne(Long id) {
        log.debug("Request to get Attendee : {}", id);
        Optional<Attendee> entity = attendeeRepository.findById(id);
        if (entity.isPresent()){
            Attendee attendee = entity.get();
            return attendeeMapper.toDto(attendee);
        }
        return null;
    }

    /**
     * Delete the attendee by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendee : {}", id);
        AttendeeDTO attendeeDTO = findOne(id);
        if (null != attendeeDTO){
            //record the activity quit event start
            eventMessageService.recordEventMessage(EventChannel.PINFAN,DateUtil.getYMDDateString(new Date()),EventType.QUIT,
                attendeeDTO.getWechatUserId(),attendeeDTO.getActivitiyTile(),
                attendeeDTO.getPinFanActivityId(),attendeeDTO.getAvatar(),
                attendeeDTO.getNickName());
            //record the activity quit event end
        }

        attendeeRepository.deleteById(id);
    }
}
