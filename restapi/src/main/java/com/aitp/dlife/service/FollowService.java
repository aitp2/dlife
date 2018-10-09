package com.aitp.dlife.service;

import com.aitp.dlife.domain.WechatUser;
import com.aitp.dlife.repository.WechatUserRepository;
import com.aitp.dlife.service.dto.WechatUserDTO;
import com.aitp.dlife.web.rest.errors.BadRequestAlertException;
import com.aitp.dlife.web.rest.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.Follow;
import com.aitp.dlife.repository.FollowRepository;
import com.aitp.dlife.service.dto.FollowDTO;
import com.aitp.dlife.service.mapper.FollowMapper;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing Follow.
 */
@Service
@Transactional
public class FollowService {

    private final Logger log = LoggerFactory.getLogger(FollowService.class);

    private static final String ENTITY_NAME = "follow";

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    private final WechatUserRepository wechatUserRepository;

    public FollowService(FollowRepository followRepository, FollowMapper followMapper, WechatUserRepository wechatUserRepository) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
        this.wechatUserRepository = wechatUserRepository;
    }

    /**
     * Save a follow.
     *
     * @param followDTO the entity to save
     * @return the persisted entity
     */
    public FollowDTO save(FollowDTO followDTO) {
        log.debug("Request to save Follow : {}", followDTO);
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        return followMapper.toDto(follow);
    }

    /**
     * Save a follow.
     *
     * @param originalFollowDTO the entity to save
     * @return the persisted entity
     */
    public FollowDTO createFollow(FollowDTO originalFollowDTO){

        List<Follow> follows = followRepository.findByFollowUserIdAndFollowedUserId(originalFollowDTO.getFollowUserId(), originalFollowDTO.getFollowedUserId());
        FollowDTO followDTO;

        // if there is already have the follow data, will not create the new one
        if (CollectionUtils.isEmpty(follows)){
            followDTO = createNewFollow(originalFollowDTO);
        }
        else{
            followDTO = followMapper.toDto(follows.get(0));
        }

        List<Follow> followeds  = followRepository.findByFollowUserIdAndFollowedUserId(originalFollowDTO.getFollowedUserId(), originalFollowDTO.getFollowUserId());
        if (!CollectionUtils.isEmpty(followeds)){
            followDTO.setMutual(Boolean.TRUE);

            Follow follow = followeds.get(0);
            follow.setMutual(Boolean.TRUE);
            follow.setModifyTime((new Date()).toInstant());
            followRepository.save(follow);
        }

        addFollowCount(originalFollowDTO);

        followDTO.setModifyTime(DateUtil.getYMDDateString(new Date()));
        return save(followDTO);
    }

    /**
     * add the follow count of the customer
     *
     * @param originalFollowDTO the entity to save
     * @return the persisted entity
     */
    protected void addFollowCount(FollowDTO originalFollowDTO){

        //add follow count
        Optional<WechatUser> followUserOptional = wechatUserRepository.findById(Long.valueOf(originalFollowDTO.getFollowUserId()));
        if (followUserOptional.isPresent()){
            WechatUser followUser = followUserOptional.get();
            int followCount = (followUser.getFollowCount() == null ? 1 : followUser.getFollowCount().intValue() + 1);
            followUser.setFollowCount(Integer.valueOf(followCount));
            wechatUserRepository.save(followUser);
        }

        //add followed count
        Optional<WechatUser> followedUserOptional = wechatUserRepository.findById(Long.valueOf(originalFollowDTO.getFollowedUserId()));
        if (followedUserOptional.isPresent()){
            WechatUser followedUser = followedUserOptional.get();
            int followedCount = (followedUser.getFollowedCount() == null ? 1 : followedUser.getFollowedCount().intValue() + 1);
            followedUser.setFollowedCount(Integer.valueOf(followedCount));
            wechatUserRepository.save(followedUser);
        }
    }

    /**
     *
     * create the new follow
     *
     * @param followDTO
     * @return FollowDTO
     */
    protected FollowDTO createNewFollow(FollowDTO followDTO){
        Optional<WechatUser> followUserDTO = wechatUserRepository.findById(Long.valueOf(followDTO.getFollowUserId()));
        if (!followUserDTO.isPresent())
        {
            throw new BadRequestAlertException("Can not get the wehcatUser by user id:" + followDTO.getFollowUserId(), ENTITY_NAME, "noFollowUser");
        }
        else
        {
            followDTO.setFollowUseravatar(followUserDTO.get().getAvatar());
            followDTO.setFollowUserNickname(followUserDTO.get().getNickName());
        }

        Optional<WechatUser> followedUserDTO = wechatUserRepository.findById(Long.valueOf(followDTO.getFollowedUserId()));
        if (followedUserDTO == null)
        {
            throw new BadRequestAlertException("Can not get the wehcatUser by user id:" + followDTO.getFollowUserId(), ENTITY_NAME, "noFollowUser");
        }
        else
        {
            followDTO.setFollowedUseravatar(followedUserDTO.get().getAvatar());
            followDTO.setFollowedUserNickname(followedUserDTO.get().getNickName());
        }

        //set default messages
        followDTO.setCreateTime(DateUtil.getYMDDateString(new Date()));

        return followDTO;
    }

    /**
     * Get all the follows.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FollowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Follows");
        return followRepository.findAll(pageable)
            .map(followMapper::toDto);
    }

    /**
     * Get one follow by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FollowDTO findOne(Long id) {
        log.debug("Request to get Follow : {}", id);
        Follow follow = followRepository.findById(id).get();
        return followMapper.toDto(follow);
    }

    /**
     * Delete the follow by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Follow : {}", id);
        FollowDTO followDTO = findOne(id);
        if (followDTO != null){
            List<Follow> followeds  = followRepository.findByFollowUserIdAndFollowedUserId(followDTO.getFollowedUserId(), followDTO.getFollowUserId());

            if (!CollectionUtils.isEmpty(followeds)){
                Follow follow = followeds.get(0);

                follow.setMutual(Boolean.FALSE);
                follow.setModifyTime((new Date()).toInstant());
                followRepository.save(follow);
            }

            minusFollowCount(followDTO);
        }

        followRepository.deleteById(id);
    }

    /**
     * minus the follow count of the customer
     *
     * @param originalFollowDTO
     */
    protected void minusFollowCount(FollowDTO originalFollowDTO){

        //minus follow count
        Optional<WechatUser> followUserOptional = wechatUserRepository.findById(Long.valueOf(originalFollowDTO.getFollowUserId()));
        if (followUserOptional.isPresent()){
            WechatUser followUser = followUserOptional.get();
            int followCount = (followUser.getFollowCount() == null ? 0 : followUser.getFollowCount().intValue() - 1);
            followUser.setFollowCount(Integer.valueOf(followCount));
            wechatUserRepository.save(followUser);
        }

        //minus followed count
        Optional<WechatUser> followedUserOptional = wechatUserRepository.findById(Long.valueOf(originalFollowDTO.getFollowedUserId()));
        if (followedUserOptional.isPresent()){
            WechatUser followedUser = followedUserOptional.get();
            int followedCount = (followedUser.getFollowedCount() == null ? 0 : followedUser.getFollowedCount().intValue() - 1);
            followedUser.setFollowedCount(Integer.valueOf(followedCount));
            wechatUserRepository.save(followedUser);
        }
    }

    /**
     * findAllByFollowUserId
     * @param pageable
     * @param wechatUserId
     * @return
     */
    public Page<FollowDTO> findAllByFollowUserId(Pageable pageable, String wechatUserId) {
    	return followRepository.findAllByFollowUserId(pageable,wechatUserId).map(followMapper::toDto);
	}

    /**
     * findAllByFollowedUserId
     * @param pageable
     * @param wechatUserId
     * @return
     */
    public Page<FollowDTO> findAllByFollowedUserId(Pageable pageable, String wechatUserId) {
    	return followRepository.findAllByFollowedUserId(pageable,wechatUserId).map(followMapper::toDto);
	}
}
