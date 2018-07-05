package com.aitp.dlife.service;

import com.aitp.dlife.domain.PinfanPics;
import com.aitp.dlife.repository.PinfanPicsRepository;
import com.aitp.dlife.service.dto.PinfanPicsDTO;
import com.aitp.dlife.service.mapper.PinfanPicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing PinfanPics.
 */
@Service
@Transactional
public class PinfanPicsService {

    private final Logger log = LoggerFactory.getLogger(PinfanPicsService.class);

    private final PinfanPicsRepository pinfanPicsRepository;

    private final PinfanPicsMapper pinfanPicsMapper;

    public PinfanPicsService(PinfanPicsRepository pinfanPicsRepository, PinfanPicsMapper pinfanPicsMapper) {
        this.pinfanPicsRepository = pinfanPicsRepository;
        this.pinfanPicsMapper = pinfanPicsMapper;
    }

    /**
     * Save a pinfanPics.
     *
     * @param pinfanPicsDTO the entity to save
     * @return the persisted entity
     */
    public PinfanPicsDTO save(PinfanPicsDTO pinfanPicsDTO) {
        log.debug("Request to save PinfanPics : {}", pinfanPicsDTO);
        PinfanPics pinfanPics = pinfanPicsMapper.toEntity(pinfanPicsDTO);
        pinfanPics = pinfanPicsRepository.save(pinfanPics);
        return pinfanPicsMapper.toDto(pinfanPics);
    }

    /**
     * Get all the pinfanPics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PinfanPicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PinfanPics");
        return pinfanPicsRepository.findAll(pageable)
            .map(pinfanPicsMapper::toDto);
    }

    /**
     * Get one pinfanPics by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PinfanPicsDTO findOne(Long id) {
        log.debug("Request to get PinfanPics : {}", id);
        PinfanPics pinfanPics = pinfanPicsRepository.findOne(id);
        return pinfanPicsMapper.toDto(pinfanPics);
    }


    /**
     * Get  pinfanPics by activityId.
     *
     * @param activityId the id of the activityId
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<PinfanPicsDTO> findPicsByActivityId(Long activityId) {
        log.debug("Request to get PinfanPics : {}", activityId);
        List<PinfanPics> pinfanPics = pinfanPicsRepository.findByActivityId(activityId);
        final List<PinfanPicsDTO> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pinfanPics)){
            for (PinfanPics pic: pinfanPics) {
                result.add(pinfanPicsMapper.toDto(pic));
            }
        }
        return result;
    }

    /**
     * Delete the pinfanPics by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PinfanPics : {}", id);
        pinfanPicsRepository.delete(id);
    }
}
