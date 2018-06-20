package com.aitp.dlife.repository;

import com.aitp.dlife.domain.WechatUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WechatUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatUserRepository extends JpaRepository<WechatUser, Long> {
	
	WechatUser findByOpenId(String openId);

}
