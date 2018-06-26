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

import com.aitp.dlife.domain.ClockIn;
import com.aitp.dlife.repository.ClockInRepository;
import com.aitp.dlife.service.dto.ClockInDTO;
import com.aitp.dlife.service.mapper.ClockInMapper;


/**
 * Service Implementation for managing ClockIn.
 */
@Service
@Transactional
public class ClockInService {

    private final Logger log = LoggerFactory.getLogger(ClockInService.class);

    private final ClockInRepository clockInRepository;

    private final ClockInMapper clockInMapper;

    public ClockInService(ClockInRepository clockInRepository, ClockInMapper clockInMapper) {
        this.clockInRepository = clockInRepository;
        this.clockInMapper = clockInMapper;
    }

    /**
     * Save a clockIn.
     *
     * @param clockInDTO the entity to save
     * @return the persisted entity
     */
    public ClockInDTO save(ClockInDTO clockInDTO) {
        log.debug("Request to save ClockIn : {}", clockInDTO);
        ClockIn clockIn = clockInMapper.toEntity(clockInDTO);
        clockIn = clockInRepository.save(clockIn);
        return clockInMapper.toDto(clockIn);
    }

    /**
     * Get all the clockIns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClockInDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClockIns");
        return clockInRepository.findAll(pageable)
            .map(clockInMapper::toDto);
    }

    /**
     * Get one clockIn by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClockInDTO findOne(Long id) {
        log.debug("Request to get ClockIn : {}", id);
        ClockIn clockIn = clockInRepository.findOne(id);
        return clockInMapper.toDto(clockIn);
    }

    /**
     * Delete the clockIn by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClockIn : {}", id);
        clockInRepository.delete(id);
    }

	public List<ClockInDTO> findClockinsByActivityParticipationId(Long activityParticipationId) {
		
		return clockInRepository.findClockinsByActivityParticipationId(activityParticipationId).stream()
	            .map(clockInMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));
	}
	
	public List<String> getClockinsDateByWechatUserIdAndMonth(String wechatUserId,String yearMonth) {
		
		return clockInRepository.findClockinsDateByWechatUserIdAndMonth(wechatUserId,yearMonth);
	}

	public List<ClockInDTO> getClockinsByWechatUserIdAndDate(String wechatUserId, String yearMonthDate) {
		return clockInRepository.getClockinsByWechatUserIdAndDate(wechatUserId,yearMonthDate).stream()
	            .map(clockInMapper::toDto)
	            .collect(Collectors.toCollection(LinkedList::new));
	}
}
