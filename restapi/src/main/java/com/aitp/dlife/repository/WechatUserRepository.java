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


	@Query(value="select ifnull(sum(change_point),0) from user_point_details where to_days(apply_time) = to_days(now()) and userid = ?1 and event_type not in ('SYSRECHARGE','REDEEM', 'RECHARGE') ", nativeQuery = true)
	Integer sumTodayPointsByUserid(String userid);


    WechatUser findByMobileNum(String mobileNum);

}
