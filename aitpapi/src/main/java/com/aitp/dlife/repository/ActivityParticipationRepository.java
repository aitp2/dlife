package com.aitp.dlife.repository;

import com.aitp.dlife.domain.ActivityParticipation;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the ActivityParticipation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipation, Long> {

	@Query(value = "select activityParticipation from ActivityParticipation activityParticipation "
			+ "where activityParticipation.activity.id =:activityId",nativeQuery = false)
	List<ActivityParticipation> findByActivityId(@Param("activityId") Long activityId);

	@Query("select activityParticipation from ActivityParticipation activityParticipation "
			+ "where activityParticipation.wechatUserId =:wechatUserId")
	List<ActivityParticipation> findByWechatUserId(@Param("wechatUserId") String wechatUserId);
}
