package com.aitp.dlife.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.repository.FitnessActivityRepository;
import com.aitp.dlife.service.dto.EventMessageDTO;
import com.aitp.dlife.service.dto.FitnessActivityDTO;
import com.aitp.dlife.service.enums.Status;
import com.aitp.dlife.service.mapper.FitnessActivityMapper;
import com.aitp.dlife.web.rest.errors.CustomParameterizedException;


/**
 * Service Implementation for managing FitnessActivity.
 */
@Service
@Transactional
public class FitnessActivityService {

    private final Logger log = LoggerFactory.getLogger(FitnessActivityService.class);

    private final FitnessActivityRepository fitnessActivityRepository;

    private final FitnessActivityMapper fitnessActivityMapper;

    private final EventMessageService eventMessageService;

    public FitnessActivityService(FitnessActivityRepository fitnessActivityRepository, FitnessActivityMapper fitnessActivityMapper, EventMessageService eventMessageService) {
        this.fitnessActivityRepository = fitnessActivityRepository;
        this.fitnessActivityMapper = fitnessActivityMapper;
        this.eventMessageService = eventMessageService;
    }

    /**
     * Save a fitnessActivity.
     *
     * @param fitnessActivityDTO the entity to save
     * @return the persisted entity
     */
    public FitnessActivityDTO save(FitnessActivityDTO fitnessActivityDTO) {
        log.debug("Request to save FitnessActivity : {}", fitnessActivityDTO);
        FitnessActivity fitnessActivity = fitnessActivityMapper.toEntity(fitnessActivityDTO);
        fitnessActivity = fitnessActivityRepository.save(fitnessActivity);
        return fitnessActivityMapper.toDto(fitnessActivity);
    }

    /**
     * Get all the fitnessActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FitnessActivityDTO> findAll(Pageable pageable,Specification<FitnessActivity> spec) {
        log.debug("Request to get all FitnessActivities");
        return fitnessActivityRepository.findAll(spec,pageable)
            .map(fitnessActivityMapper::toDto);
    }

    public List<FitnessActivityDTO> findAll(Specification<FitnessActivity> spec){
    	  log.debug("Request to get all FitnessActivities");
    	return fitnessActivityRepository.findAll(spec).stream().map(fitnessActivityMapper::toDto).collect(Collectors.toList());
    }
    /**
     * Get all the fitnessActivities.
     *
     * @param pageable the pagination information
     * @param eventCount the event count
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FitnessActivityDTO> findAllAndEvent(Pageable pageable,Specification<FitnessActivity> spec, Integer eventCount) {
        log.debug("Request to get all FitnessActivities");
        Page<FitnessActivityDTO> result = fitnessActivityRepository.findAll(spec,pageable)
            .map(fitnessActivityMapper::toDto);
        if(result!=null){
            Sort.Order order = new Sort.Order(Sort.Direction.DESC,"createTime");
            Sort sort = new Sort(order);
            PageRequest eventPageable = new PageRequest(0,eventCount,sort);
            for(FitnessActivityDTO fitnessActivityDTO:result){
                List<EventMessageDTO> eventMessageDTOSet = eventMessageService.findAllForObjectId(eventPageable,
                    fitnessActivityDTO.getId()+"").stream().collect(Collectors.toList());
                fitnessActivityDTO.setEventMessages(eventMessageDTOSet);
            }
        }

        return result;
    }

    /**
     * Get one fitnessActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FitnessActivityDTO findOne(Long id) {
        log.debug("Request to get FitnessActivity : {}", id);
        FitnessActivity fitnessActivity = fitnessActivityRepository.findById(id).get();
        return fitnessActivityMapper.toDto(fitnessActivity);
    }

    /**
     * Delete the fitnessActivity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FitnessActivity : {}", id);
        fitnessActivityRepository.deleteById(id);
    }

	public List<FitnessActivityDTO> getActivitiesByWechatUserId(String wechatUserId) {
		return fitnessActivityRepository.findActivitiesByWechatUserId(wechatUserId).stream()
	            .map(fitnessActivityMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));
	}


	/**
	 * According to state query
	 * @param state
	 * @return
	 */
	@Deprecated
	public List<FitnessActivityDTO> getActivitiesByState(Integer state){
		 Date nowDate = new Date();
		 SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String nowDateString =   sdf.format(nowDate);
		 switch (Status.prease(state)) {
		case OPEND:
			return fitnessActivityRepository.findOpend(nowDateString).stream().map(fitnessActivityMapper::toDto).collect(Collectors.toList());
		case IN_PROGRESS:
			return fitnessActivityRepository.findInProgress(nowDateString).stream().map(fitnessActivityMapper::toDto).collect(Collectors.toList());
		case END:
			return fitnessActivityRepository.findEnd(nowDateString).stream().map(fitnessActivityMapper::toDto).collect(Collectors.toList());
		default:
			throw new CustomParameterizedException("not have the status "+state+" code.","status");
		}

	}

	public void ActivityStatus(FitnessActivityDTO fitnessActivityDTO){
        Instant now = Instant.now();
        FitnessActivity fitnessActivity = fitnessActivityMapper.toEntity(fitnessActivityDTO);
        if (null !=fitnessActivity) {
            if (fitnessActivity.getActivityStartTime().isBefore(now) && fitnessActivity.getActivityEndTime().isAfter(now)) {
                fitnessActivityDTO.setStatus(1);
            }
            else if (fitnessActivity.getActivityEndTime().isBefore(now)){
                fitnessActivityDTO.setStatus(2);
            }else
            {
                fitnessActivityDTO.setStatus(0);
            }
        }
    }


}
