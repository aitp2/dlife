package com.aitp.dlife.service;

import com.aitp.dlife.domain.PinFanActivity;
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


/**
 * Service Implementation for managing PinFanActivity.
 */
@Service
@Transactional
public class PinFanActivityService {

    private final Logger log = LoggerFactory.getLogger(PinFanActivityService.class);

    private final PinFanActivityRepository pinFanActivityRepository;

    private final PinFanActivityMapper pinFanActivityMapper;

    public PinFanActivityService(PinFanActivityRepository pinFanActivityRepository, PinFanActivityMapper pinFanActivityMapper) {
        this.pinFanActivityRepository = pinFanActivityRepository;
        this.pinFanActivityMapper = pinFanActivityMapper;
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
                activity.setPinfanPics(null);
                activity.setRates(null);
//                activity.setAttendees(null);
//                if(!Hibernate.isInitialized(activity.getPinfanPics())){
//                    Hibernate.initialize(activity.getPinfanPics());
//                }
//                if(!Hibernate.isInitialized(activity.getRates())){
//                    Hibernate.initialize(activity.getRates());
//                }
                if(!Hibernate.isInitialized(activity.getAttendees())){
                    Hibernate.initialize(activity.getAttendees());
                }
            }
        }
        return all.map(pinFanActivityMapper::toDto);
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
        if(pinFanActivity!=null){
            if(!Hibernate.isInitialized(pinFanActivity.getPinfanPics())){
                Hibernate.initialize(pinFanActivity.getPinfanPics());
            }
            if(!Hibernate.isInitialized(pinFanActivity.getRates())){
                Hibernate.initialize(pinFanActivity.getRates());
            }
            if(!Hibernate.isInitialized(pinFanActivity.getAttendees())){
                Hibernate.initialize(pinFanActivity.getAttendees());
            }
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
}
