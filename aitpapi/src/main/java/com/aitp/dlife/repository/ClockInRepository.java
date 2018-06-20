package com.aitp.dlife.repository;

import java.util.List;

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
public interface ClockInRepository extends JpaRepository<ClockIn, Long> {

	@Query(value = "select clockIn from ClockIn clockIn "
			+ "where clockIn.activityParticipation.id =:activityParticipationId",nativeQuery = false)
	List<ClockIn> findClockinsByActivityParticipationId(@Param("activityParticipationId")Long activityParticipationId);
	
}
