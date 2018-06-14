package com.aitp.dlife.uaa.service;

import com.aitp.dlife.uaa.domain.WechatUser;
import com.aitp.dlife.uaa.repository.WechatUserRepository;
import com.aitp.dlife.uaa.repository.search.WechatUserSearchRepository;
import com.aitp.dlife.uaa.service.dto.WechatUserDTO;
import com.aitp.dlife.uaa.service.mapper.WechatUserMapper;
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
 * Service Implementation for managing WechatUser.
 */
@Service
@Transactional
public class WechatUserService {

    private final Logger log = LoggerFactory.getLogger(WechatUserService.class);

    private final WechatUserRepository wechatUserRepository;

    private final WechatUserMapper wechatUserMapper;

    private final WechatUserSearchRepository wechatUserSearchRepository;

    public WechatUserService(WechatUserRepository wechatUserRepository, WechatUserMapper wechatUserMapper, WechatUserSearchRepository wechatUserSearchRepository) {
        this.wechatUserRepository = wechatUserRepository;
        this.wechatUserMapper = wechatUserMapper;
        this.wechatUserSearchRepository = wechatUserSearchRepository;
    }

    /**
     * Save a wechatUser.
     *
     * @param wechatUserDTO the entity to save
     * @return the persisted entity
     */
    public WechatUserDTO save(WechatUserDTO wechatUserDTO) {
        log.debug("Request to save WechatUser : {}", wechatUserDTO);
        WechatUser wechatUser = wechatUserMapper.toEntity(wechatUserDTO);
        wechatUser = wechatUserRepository.save(wechatUser);
        WechatUserDTO result = wechatUserMapper.toDto(wechatUser);
        wechatUserSearchRepository.save(wechatUser);
        return result;
    }

    /**
     * Get all the wechatUsers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WechatUserDTO> findAll() {
        log.debug("Request to get all WechatUsers");
        return wechatUserRepository.findAll().stream()
            .map(wechatUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one wechatUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatUserDTO findOne(Long id) {
        log.debug("Request to get WechatUser : {}", id);
        WechatUser wechatUser = wechatUserRepository.findOne(id);
        return wechatUserMapper.toDto(wechatUser);
    }

    /**
     * Delete the wechatUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatUser : {}", id);
        wechatUserRepository.delete(id);
        wechatUserSearchRepository.delete(id);
    }

    /**
     * Search for the wechatUser corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<WechatUserDTO> search(String query) {
        log.debug("Request to search WechatUsers for query {}", query);
        return StreamSupport
            .stream(wechatUserSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(wechatUserMapper::toDto)
            .collect(Collectors.toList());
    }
}
