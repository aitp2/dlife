package com.aitp.dlife.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aitp.dlife.domain.ClockIn;

/**
 * Spring Data JPA repository for the ClockIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClockInNotesRepository extends JpaRepository<ClockIn, Long> {

	@Query(value = "SELECT DISTINCT DATE_FORMAT(clock_in.punch_date_time,'%Y-%m-%d') FROM clock_in "
			+ " LEFT JOIN activity_participation "
			+ " ON clock_in.activity_participation_id = activity_participation.id " + " where "
			+ " activity_participation.wechat_user_id =?1 " + " and activity_participation.fitness_activity_id =?2 "
			+ " and DATE_FORMAT(clock_in.punch_date_time,'%Y-%m') =?3", nativeQuery = true)
	List<String> getColckInCalendarByWechatUserIdAndAcitivityIdAndYearMonth(String wechatUserId, Long activityId,
			String ym);

	@Query(value = "SELECT clockIn FROM ClockIn clockIn where clockIn.activityParticipation.wechatUserId =:wechatUserId and clockIn.activityParticipation.fitnessActivity.id=:activityId")
	Page<ClockIn> getColckInNotesByWechatUserIdAndAcitivityId(Pageable pageable,
			@Param("wechatUserId") String wechatUserId, @Param("activityId") Long activityId);

	@Query(value = "SELECT clockIn FROM ClockIn clockIn where clockIn.activityParticipation.wechatUserId =:wechatUserId")
	Page<ClockIn> getColckInNotesByWechatUserId(Pageable pageable, @Param("wechatUserId") String wechatUserId);

	@Query(value = "SELECT" + " clock_in.id as id," + " clock_in.punch_date_time as punch_date_time,"
			+ " clock_in.sign_note as sign_note," + " clock_in.title as title,"
			+ " clock_in.activity_id as activity_id,"
			+ " clock_in.activity_participation_id as activity_participation_id" + " FROM clock_in"
			+ " LEFT JOIN activity_participation"
			+ " on clock_in.activity_participation_id = activity_participation.id " + " where "
			+ " activity_participation.wechat_user_id =:wechatUserId "
			+ " and activity_participation.fitness_activity_id =:activityId"
			+ " and DATE_FORMAT(clock_in.punch_date_time,'%Y-%m-%d') =:ymd", nativeQuery = true)
	List<ClockIn> getColckInNotesByWechatUserIdAndAcitivityIdAndYearMonthDay(@Param("wechatUserId") String wechatUserId,
			@Param("activityId") Long activityId, @Param("ymd") String ymd);

}
