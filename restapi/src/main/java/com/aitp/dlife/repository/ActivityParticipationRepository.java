package com.aitp.dlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aitp.dlife.domain.ActivityParticipation;


/**
 * Spring Data JPA repository for the ActivityParticipation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipation, Long> {

	@Query(value = "select activityParticipation from ActivityParticipation activityParticipation "
			+ "where activityParticipation.activity.id =:activityId",nativeQuery = false)
	List<ActivityParticipation> findByActivityId(@Param("activityId") Long activityId);

    @Query(value = "select activityParticipation from ActivityParticipation activityParticipation "
        + "where activityParticipation.activity.id =:activityId and activityParticipation.wechatUserId =:wechatUserId",nativeQuery = false)
    ActivityParticipation findByUidAndActivityId(@Param("activityId") Long activityId,@Param("wechatUserId") String wechatUserId);
    
    @Query(value = "select activityParticipation from ActivityParticipation activityParticipation "
            + "where activityParticipation.id in (:participationIds) and not exists(select clockIn.activityParticipation.id from ClockIn clockIn where  activityParticipation.id = clockIn.activityParticipation.id and punch_date_time>:startDate and punch_date_time<:endDate) ",nativeQuery = false)
        List<ActivityParticipation> findNonClockParticipation(@Param("participationIds") List<Long> participationIds,@Param("startDate") String startDate,@Param("endDate") String endDate);
    
    @Query(value = "select activityParticipation from ActivityParticipation activityParticipation "
            + "where activityParticipation.id in (:participationIds) and  exists(select clockIn.activityParticipation.id from ClockIn clockIn where  activityParticipation.id = clockIn.activityParticipation.id and punch_date_time>:startDate and punch_date_time<:endDate)",nativeQuery = false)
        List<ActivityParticipation> findClockParticipation(@Param("participationIds") List<Long> participationIds,@Param("startDate") String startDate,@Param("endDate") String endDate);
    
}
