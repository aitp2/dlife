package com.aitp.dlife.repository;

import com.aitp.dlife.domain.WechatUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WechatUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WechatUserRepository extends JpaRepository<WechatUser, Long> {

}
