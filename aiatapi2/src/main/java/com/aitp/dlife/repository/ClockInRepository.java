package com.aitp.dlife.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aitp.dlife.domain.ClockIn;
import com.aitp.dlife.service.dto.ClockInDTO;


/**
 * Spring Data JPA repository for the ClockIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClockInRepository extends JpaRepository<ClockIn, Long> {

	@Query(value = "select clockIn from ClockIn clockIn "
			+ "where clockIn.activityParticipation.id =:activityParticipationId",nativeQuery = false)
	List<ClockIn> findClockinsByActivityParticipationId(@Param("activityParticipationId")Long activityParticipationId);

	@Query(value = "SELECT DISTINCT DATE_FORMAT(clock_in.punch_date_time,'%Y-%m-%d') FROM clock_in "
			+ " LEFT JOIN activity_participation "
			+ " on 	clock_in.activity_participation_id = activity_participation.id "
			+ " where "
			+ "  activity_participation.wechat_user_id =:wechatUserId "
			+ " and DATE_FORMAT(clock_in.punch_date_time,'%Y-%m') =:yearMonth"
			,nativeQuery = true)
	List<String> findClockinsDateByWechatUserIdAndMonth(@Param("wechatUserId")String wechatUserId,@Param("yearMonth")String yearMonth);


	
	@Query(value = "SELECT * FROM clock_in "
			+ " LEFT JOIN activity_participation "
			+ " on 	clock_in.activity_participation_id = activity_participation.id "
			+ " where "
			+ "  activity_participation.wechat_user_id =:wechatUserId "
			+ " and DATE_FORMAT(clock_in.punch_date_time,'%Y-%m-%d') =:yearMonthDate"
			,nativeQuery = true)
	List<ClockIn> getClockinsByWechatUserIdAndDate(@Param("wechatUserId")String wechatUserId,@Param("yearMonthDate")String yearMonthDate);

    @Query(value = "SELECT * FROM clock_in "
        + " LEFT JOIN activity_participation "
        + " on 	clock_in.activity_participation_id = activity_participation.id "
        + " where "
        + "  activity_participation.wechat_user_id =:wechatUserId "
        + " and activity_participation.id =:activityParticipationId"
        + " and DATE_FORMAT(clock_in.punch_date_time,'%Y-%m-%d') =:yearMonthDate"
        ,nativeQuery = true)
    List<ClockIn> getClockinsByWechatUserIdAndDateAndActivityId(@Param("wechatUserId")String wechatUserId,@Param("activityParticipationId")Long activityParticipationId,@Param("yearMonthDate")String yearMonthDate);

    @Query(value = "SELECT * FROM clock_in "
        + " LEFT JOIN activity_participation "
        + " on 	clock_in.activity_participation_id = activity_participation.id "
        + " LEFT JOIN fitness_activity "
        + " on 	activity_participation.activity_id = fitness_activity.id "
        + " where "
        + "  fitness_activity.id =:activityId "
        + " ORDER BY clock_in.punch_date_time DESC "
        ,nativeQuery = true)
    List<ClockIn> getClockinsByActivityId(@Param("activityId")String activityId);

}
