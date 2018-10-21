package com.aitp.dlife.service;

import com.aitp.dlife.domain.Follow;
import com.aitp.dlife.domain.WechatUser;
import com.aitp.dlife.repository.FollowRepository;
import com.aitp.dlife.repository.WechatUserRepository;
import com.aitp.dlife.service.dto.UserPointDTO;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.service.mapper.WechatUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Service Implementation for managing WechatUser.
 */
@Service
@Transactional
public class WechatUserService {

    private final Logger log = LoggerFactory.getLogger(WechatUserService.class);

    private final WechatUserRepository wechatUserRepository;

    private final FollowRepository followRepository;

    private final WechatUserMapper wechatUserMapper;

    public WechatUserService(WechatUserRepository wechatUserRepository, FollowRepository followRepository, WechatUserMapper wechatUserMapper) {
        this.wechatUserRepository = wechatUserRepository;
        this.followRepository = followRepository;
        this.wechatUserMapper = wechatUserMapper;
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
        return wechatUserMapper.toDto(wechatUser);
    }

    /**
     * Get all the wechatUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WechatUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WechatUsers");
        return wechatUserRepository.findAll(pageable)
            .map(wechatUserMapper::toDto);
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
        WechatUser wechatUser = wechatUserRepository.findById(id).get();
        return wechatUserMapper.toDto(wechatUser);
    }

    /**
     * Get one wechatUser by id.
     *
     * @param currentId the id of the entity
     * @param targetId the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public WechatUserDTO findOneAndFollow(Long currentId,Long targetId) {
        log.debug("Request to get WechatUser : {}", targetId);
        WechatUser wechatUser = wechatUserRepository.findById(targetId).get();
        WechatUserDTO wechatUserDTO = wechatUserMapper.toDto(wechatUser);

        List<Follow> follws = followRepository.findByFollowUserIdAndFollowedUserId(currentId + "",targetId + "");
        if (!CollectionUtils.isEmpty(follws)){
            wechatUserDTO.setFollowed(Boolean.TRUE);
        }
        else{
            wechatUserDTO.setFollowed(Boolean.FALSE);
        }

        return wechatUserDTO;
    }

    /**˙
     * Delete the wechatUser by id.
     *
     * @param id the id of the entityh
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatUser : {}", id);
        wechatUserRepository.deleteById(id);
    }

    public WechatUserDTO findByOpenId(String openId) {
        log.debug("Request to findByOpenId : {}", openId);
        WechatUser wechatUser = wechatUserRepository.findByOpenId(openId);
        return wechatUserMapper.toDto(wechatUser);
    }

    public WechatUserDTO findByMobileNum(String mobileNum) {
        log.debug("Request to findByMobileNum : {}", mobileNum);
        WechatUser wechatUser = wechatUserRepository.findByMobileNum(mobileNum);
        return wechatUserMapper.toDto(wechatUser);
    }

	public UserPointDTO getWechatUserPointByID(Long id) {
		UserPointDTO dto = new UserPointDTO();
		WechatUser user = this.wechatUserRepository.getOne(id);
		dto.setId(id);
		dto.setTotalPoint(user.getTotalPoint());
		if(dto.getTotalPoint() == null) {
			dto.setTotalPoint(0);
			dto.setTodayPoint(0);
			dto.setTodayMaxPoint(0);
		} else {
			dto.setTodayPoint(this.wechatUserRepository.sumTodayPointsByUserid(String.valueOf(id)));
			dto.setTodayMaxPoint(this.wechatUserRepository.sumTodayMaxPoints());
		}
		
		return dto;
	}
}
