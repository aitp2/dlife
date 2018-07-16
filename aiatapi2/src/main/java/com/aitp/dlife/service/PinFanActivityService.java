package com.aitp.dlife.service;

import com.aitp.dlife.domain.Attendee;
import com.aitp.dlife.domain.PinFanActivity;
import com.aitp.dlife.repository.AttendeeRepository;
import com.aitp.dlife.repository.PinFanActivityRepository;
import com.aitp.dlife.service.dto.PinFanActivityDTO;
import com.aitp.dlife.service.mapper.PinFanActivityMapper;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing PinFanActivity.
 */
@Service
@Transactional
public class PinFanActivityService {

    private final Logger log = LoggerFactory.getLogger(PinFanActivityService.class);

    private final PinFanActivityRepository pinFanActivityRepository;

    private final AttendeeRepository attendeeRepository;

    private final PinFanActivityMapper pinFanActivityMapper;

    public PinFanActivityService(PinFanActivityRepository pinFanActivityRepository, PinFanActivityMapper pinFanActivityMapper,AttendeeRepository attendeeRepository) {
        this.pinFanActivityRepository = pinFanActivityRepository;
        this.pinFanActivityMapper = pinFanActivityMapper;
        this.attendeeRepository=attendeeRepository;
    }

    /**
     * Save a pinFanActivity.
     *
     * @param pinFanActivityDTO the entity to save
     * @return the persisted entity
     */
    public PinFanActivityDTO save(PinFanActivityDTO pinFanActivityDTO) {
        log.debug("Request to save PinFanActivity : {}", pinFanActivityDTO);
        PinFanActivity pinFanActivity = pinFanActivityMapper.toEntity(pinFanActivityDTO);
        pinFanActivity = pinFanActivityRepository.save(pinFanActivity);
        return pinFanActivityMapper.toDto(pinFanActivity);
    }

    /**
     * Get all the pinFanActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
      @Transactional(readOnly = true)
    public Page<PinFanActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PinFanActivities");
        Page<PinFanActivity> all = pinFanActivityRepository.findAll(pageable);

        if(all!=null){
            for(PinFanActivity activity:all){
                if(activity.getAppointEndDatetime() != null && (new Date().toInstant()).compareTo(activity.getAppointEndDatetime()) >= 0
                    &&0==activity.getStatus()){
                    activity.setStatus(1);
                }
                activity.setPinfanPics(null);
                if(!Hibernate.isInitialized(activity.getAttendees())){
                    Hibernate.initialize(activity.getAttendees());
                }
            }
        }
        return all.map(pinFanActivityMapper::toDto);
    }
    @Transactional(readOnly = true)
    public Page<PinFanActivityDTO> findAllByWechatUserId(Pageable pageable,String wechatUserId) {
        log.debug("Request to get all PinFanActivities");
        Page<PinFanActivity> all = pinFanActivityRepository.findAllByWechatUserId(pageable,wechatUserId);
        if(all!=null){
            for(PinFanActivity activity:all){
                if(activity.getAppointEndDatetime() != null && (new Date().toInstant()).compareTo(activity.getAppointEndDatetime()) >= 0
                    &&0==activity.getStatus()){
                    activity.setStatus(1);
                }
                activity.setPinfanPics(null);
                if(!Hibernate.isInitialized(activity.getAttendees())){
                    Hibernate.initialize(activity.getAttendees());
                }
            }
        }
        return all.map(pinFanActivityMapper::toDto);
    }
    @Transactional(readOnly = true)
    public List<PinFanActivityDTO> findAllAttendedByWechatUserId(String wechatUserId) {
        log.debug("Request to get all PinFanActivities");
        List<Attendee> attendees = attendeeRepository.findAllByWechatUserId(wechatUserId);
        if(attendees==null || attendees.isEmpty()){
            return null;
        }
        final Set<Long> actIds=new HashSet<>();

        attendees.forEach(a->{actIds.add(a.getPinFanActivity().getId());});
        List<PinFanActivity> activities = pinFanActivityRepository.findAllByIdIn(actIds);
        if(activities!=null){
            for(PinFanActivity activity:activities){
                if(activity.getAppointEndDatetime() != null && (new Date().toInstant()).compareTo(activity.getAppointEndDatetime()) >= 0
                    &&0==activity.getStatus()){
                    activity.setStatus(1);
                }
                activity.setPinfanPics(null);
                if(!Hibernate.isInitialized(activity.getAttendees())){
                    Hibernate.initialize(activity.getAttendees());
                }
            }
        }
       return pinFanActivityMapper.toDto(activities);
    }
    /**
     * Get one pinFanActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PinFanActivityDTO findOne(Long id) {
        log.debug("Request to get PinFanActivity : {}", id);
        PinFanActivity pinFanActivity = pinFanActivityRepository.findOne(id);
        if(pinFanActivity.getAppointEndDatetime() != null &&
            (new Date().toInstant()).compareTo(pinFanActivity.getAppointEndDatetime()) >= 0
            &&0==pinFanActivity.getStatus()){
            pinFanActivity.setStatus(1);
        }
        if(pinFanActivity!=null){
            if(!Hibernate.isInitialized(pinFanActivity.getPinfanPics())){
                Hibernate.initialize(pinFanActivity.getPinfanPics());
            }
            if(!Hibernate.isInitialized(pinFanActivity.getAttendees())){
                Hibernate.initialize(pinFanActivity.getAttendees());
            }
        }
        if(pinFanActivity.getAttendees() !=null && !pinFanActivity.getAttendees().isEmpty()){
            ArrayList<Attendee> attendees = new ArrayList<>(pinFanActivity.getAttendees());
            Collections.sort(attendees,(a1,a2) -> {return a1.getParticipationTime().compareTo(a2.getParticipationTime());});
            pinFanActivity.setAttendees(new HashSet<>(attendees));
        }
        return pinFanActivityMapper.toDto(pinFanActivity);
    }

    /**
     * Delete the pinFanActivity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PinFanActivity : {}", id);
        pinFanActivityRepository.delete(id);
    }

    public List<PinFanActivityDTO> getPinFanActivityForTomorrow(){
        List<PinFanActivity> all = pinFanActivityRepository.getPinFanActivitiesByStartTime(getTimeString(1),getTimeString(2));
        return all.stream().map(pinFanActivityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private String getTimeString(int days){
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,days);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

}
