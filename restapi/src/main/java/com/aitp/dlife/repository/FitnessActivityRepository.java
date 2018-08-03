package com.aitp.dlife.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aitp.dlife.domain.FitnessActivity;
import com.aitp.dlife.service.dto.FitnessActivityDTO;


/**
 * Spring Data JPA repository for the FitnessActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FitnessActivityRepository extends JpaRepository<FitnessActivity, Long> {

	@Query(value = "select * from fitness_activity LEFT JOIN activity_participation ON fitness_activity.id = activity_participation.activity_id "
			+ " where activity_participation.wechat_user_id =:wechatUserId",nativeQuery = true)
	Set<FitnessActivity> findActivitiesByWechatUserId(@Param("wechatUserId")String wechatUserId);

	@Query(value = "select * from fitness_activity LEFT JOIN activity_participation ON fitness_activity.id = activity_participation.activity_id  where activity_start_time >=:nowData",nativeQuery = true)
	Set<FitnessActivity> findOpend(@Param("nowData")String nowData);
	
	@Query(value = "select * from fitness_activity LEFT JOIN activity_participation ON fitness_activity.id = activity_participation.activity_id  where activity_start_time <:nowData and activity_end_time >:nowData",nativeQuery = true)
	Set<FitnessActivity> findInProgress(@Param("nowData")String nowData);

	@Query(value = "select * from fitness_activity LEFT JOIN activity_participation ON fitness_activity.id = activity_participation.activity_id  where activity_end_time <:nowData",nativeQuery = true)
	List<FitnessActivity> findEnd(@Param("nowData")String nowData);
	
}
