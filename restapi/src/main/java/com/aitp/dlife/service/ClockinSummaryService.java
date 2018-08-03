package com.aitp.dlife.service;

import com.aitp.dlife.domain.ClockinSummary;
import com.aitp.dlife.repository.ClockinSummaryRepository;
import com.aitp.dlife.service.dto.ClockinSummaryDTO;
import com.aitp.dlife.service.mapper.ClockinSummaryMapper;import com.aitp.dlife.web.rest.util.DateUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClockinSummary.
 */
@Service
@Transactional
public class ClockinSummaryService {

    private final Logger log = LoggerFactory.getLogger(ClockinSummaryService.class);

    private final ClockinSummaryRepository clockinSummaryRepository;

    private final ClockinSummaryMapper clockinSummaryMapper;

    public ClockinSummaryService(ClockinSummaryRepository clockinSummaryRepository, ClockinSummaryMapper clockinSummaryMapper) {
        this.clockinSummaryRepository = clockinSummaryRepository;
        this.clockinSummaryMapper = clockinSummaryMapper;
    }

    /**
     * Save a clockinSummary.
     *
     * @param clockinSummaryDTO the entity to save
     * @return the persisted entity
     */
    public ClockinSummaryDTO save(ClockinSummaryDTO clockinSummaryDTO) {
        log.debug("Request to save ClockinSummary : {}", clockinSummaryDTO);
        ClockinSummary clockinSummary = clockinSummaryMapper.toEntity(clockinSummaryDTO);
        clockinSummary = clockinSummaryRepository.save(clockinSummary);
        return clockinSummaryMapper.toDto(clockinSummary);
    }

    /**
     * Get all the clockinSummaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClockinSummaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClockinSummaries");
        return clockinSummaryRepository.findAll(pageable)
            .map(clockinSummaryMapper::toDto);
    }

    /**
     * Get one clockinSummary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClockinSummaryDTO findOne(Long id) {
        log.debug("Request to get ClockinSummary : {}", id);
        ClockinSummary clockinSummary = clockinSummaryRepository.findOne(id);
        return clockinSummaryMapper.toDto(clockinSummary);
    }

    /**
     * Delete the clockinSummary by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClockinSummary : {}", id);
        clockinSummaryRepository.delete(id);
    }
    
    /**
     * Get one clockinSummary by wechatUserId.
     * 
     * @param wechatUserId
     * @return
     */
    public ClockinSummaryDTO findByWechatUserId(String wechatUserId) {
    	ClockinSummaryDTO result = clockinSummaryMapper.toDto(clockinSummaryRepository.findByWechatUserId(String.valueOf(wechatUserId))) ;
    	if(null != result && null != result.getId()){
    		result.setWeeklyCount(DateUtil.isThisWeek(result.getLastClockInTime())
					? result.getWeeklyCount() : 0);
    		result.setSerialCount(
					DateUtil.isYesterday(DateUtil.fromYDMStringDate(result.getLastClockInTime())) || DateUtil.isToday(DateUtil.fromYDMStringDate(result.getLastClockInTime()))
							? result.getSerialCount() : 0);
    	}
		return result;
	}
}
