package com.aitp.dlife.service;

import com.aitp.dlife.domain.Pics;
import com.aitp.dlife.repository.PicsRepository;
import com.aitp.dlife.service.dto.PicsDTO;
import com.aitp.dlife.service.mapper.PicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Service Implementation for managing Pics.
 */
@Service
@Transactional
public class PicsService {

    private final Logger log = LoggerFactory.getLogger(PicsService.class);

    private final PicsRepository picsRepository;

    private final PicsMapper picsMapper;

    public PicsService(PicsRepository picsRepository, PicsMapper picsMapper) {
        this.picsRepository = picsRepository;
        this.picsMapper = picsMapper;
    }

    /**
     * Save a pics.
     *
     * @param picsDTO the entity to save
     * @return the persisted entity
     */
    public PicsDTO save(PicsDTO picsDTO) {
        log.debug("Request to save Pics : {}", picsDTO);
        Pics pics = picsMapper.toEntity(picsDTO);
        pics = picsRepository.save(pics);
        return picsMapper.toDto(pics);
    }

    /**
     * Get all the pics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pics");
        return picsRepository.findAll(pageable)
            .map(picsMapper::toDto);
    }

    /**
     * Get one pics by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PicsDTO findOne(Long id) {
        log.debug("Request to get Pics : {}", id);
        Pics pics = picsRepository.findOne(id);
        return picsMapper.toDto(pics);
    }

    /**
     * Delete the pics by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pics : {}", id);
        picsRepository.delete(id);
    }

    /**
     * Delete all of the pics which is related to the ClockIn by the clockIn id.
     *
     * @param clockInId the id of the ClockIn id
     */
    public void deleteByClockInId(Long clockInId) {
        log.debug("Request to delete all of the pics which is related to the ClockIn by the clockIn id : {}", clockInId);
        picsRepository.findPicsByClockInId(clockInId).stream().forEach(entry -> delete(entry.getId()));
    }

    /**
     * Get  pinfanPics by activityId.
     *
     * @param activityId the id of the activityId
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<PicsDTO> findPicsByActivityId(Long activityId) {
        log.debug("Request to get PicsDTO : {}", activityId);
        List<Pics> Pics = picsRepository.findByActivityId(activityId);
        final List<PicsDTO> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(Pics)){
            for (Pics pic: Pics) {
                result.add(picsMapper.toDto(pic));
            }
        }
        return result;
    }
}
