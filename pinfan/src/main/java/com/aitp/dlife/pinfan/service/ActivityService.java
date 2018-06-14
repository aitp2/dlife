package com.aitp.dlife.pinfan.service;

import com.aitp.dlife.pinfan.domain.Activity;
import com.aitp.dlife.pinfan.repository.ActivityRepository;
import com.aitp.dlife.pinfan.repository.search.ActivitySearchRepository;
import com.aitp.dlife.pinfan.service.dto.ActivityDTO;
import com.aitp.dlife.pinfan.service.mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Activity.
 */
@Service
@Transactional
public class ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    private final ActivitySearchRepository activitySearchRepository;

    public ActivityService(ActivityRepository activityRepository, ActivityMapper activityMapper, ActivitySearchRepository activitySearchRepository) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
        this.activitySearchRepository = activitySearchRepository;
    }

    /**
     * Save a activity.
     *
     * @param activityDTO the entity to save
     * @return the persisted entity
     */
    public ActivityDTO save(ActivityDTO activityDTO) {
        log.debug("Request to save Activity : {}", activityDTO);
        Activity activity = activityMapper.toEntity(activityDTO);
        activity = activityRepository.save(activity);
        ActivityDTO result = activityMapper.toDto(activity);
        activitySearchRepository.save(activity);
        return result;
    }

    /**
     * Get all the activities.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> findAll() {
        log.debug("Request to get all Activities");
        return activityRepository.findAll().stream()
            .map(activityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one activity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ActivityDTO findOne(Long id) {
        log.debug("Request to get Activity : {}", id);
        Activity activity = activityRepository.findOne(id);
        return activityMapper.toDto(activity);
    }

    /**
     * Delete the activity by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Activity : {}", id);
        activityRepository.delete(id);
        activitySearchRepository.delete(id);
    }

    /**
     * Search for the activity corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> search(String query) {
        log.debug("Request to search Activities for query {}", query);
        return StreamSupport
            .stream(activitySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(activityMapper::toDto)
            .collect(Collectors.toList());
    }
}
