package com.aitp.dlife.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.ActivityParticipation;
import com.aitp.dlife.repository.ActivityParticipationRepository;
import com.aitp.dlife.service.dto.ActivityParticipationDTO;
import com.aitp.dlife.service.mapper.ActivityParticipationMapper;


/**
 * Service Implementation for managing ActivityParticipation.
 */
@Service
@Transactional
public class ActivityParticipationService {

    private final Logger log = LoggerFactory.getLogger(ActivityParticipationService.class);

    private final ActivityParticipationRepository activityParticipationRepository;

    private final ActivityParticipationMapper activityParticipationMapper;

    private final ClockInService clockInService;

    public ActivityParticipationService(ActivityParticipationRepository activityParticipationRepository, ActivityParticipationMapper activityParticipationMapper, ClockInService clockInService) {
        this.activityParticipationRepository = activityParticipationRepository;
        this.activityParticipationMapper = activityParticipationMapper;
        this.clockInService = clockInService;
    }

    /**
     * Save a activityParticipation.
     *
     * @param activityParticipationDTO the entity to save
     * @return the persisted entity
     */
    public ActivityParticipationDTO save(ActivityParticipationDTO activityParticipationDTO) {
        log.debug("Request to save ActivityParticipation : {}", activityParticipationDTO);
        ActivityParticipation activityParticipation = activityParticipationMapper.toEntity(activityParticipationDTO);
        activityParticipation = activityParticipationRepository.save(activityParticipation);
        return activityParticipationMapper.toDto(activityParticipation);
    }

    /**
     * Get all the activityParticipations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ActivityParticipationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityParticipations");
        return activityParticipationRepository.findAll(pageable)
            .map(activityParticipationMapper::toDto);
    }

    
    public List<ActivityParticipationDTO> findTodayClockActivityParticipation(List<Long> ids,String date,Long isClock){
    if(isClock.equals(1L)){
    	return activityParticipationRepository.findClockParticipation(ids, date+" 00:00:00", date+" 23:59:59").stream().map(activityParticipationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }else{
    	return activityParticipationRepository.findNonClockParticipation(ids, date+" 00:00:00", date+" 23:59:59").stream().map(activityParticipationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }
    	
    }
    /**
     * Get one activityParticipation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ActivityParticipationDTO findOne(Long id) {
        log.debug("Request to get ActivityParticipation : {}", id);
        ActivityParticipation activityParticipation = activityParticipationRepository.findOne(id);
        return activityParticipationMapper.toDto(activityParticipation);
    }

   
    
    /**
     * Delete the activityParticipation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ActivityParticipation : {}", id);

        // before we remove the repository, we need to remove the related ClockIn data
        clockInService.deleteByActivityParticipationId(id);
        activityParticipationRepository.delete(id);
    }

	public List<ActivityParticipationDTO> findByActivity(Long activityId) {
		return activityParticipationRepository.findByActivityId(activityId).stream()
	            .map(activityParticipationMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));

	}

	public ActivityParticipationDTO getByUidAndActivityId(Long activityId,String uid){
        ActivityParticipation activityParticipation = activityParticipationRepository.findByUidAndActivityId(activityId,uid);
        return activityParticipationMapper.toDto(activityParticipation);
    }
}
