package com.aitp.dlife.repository;

import com.aitp.dlife.domain.UserEvent;
import com.aitp.dlife.domain.enumeration.PointEventType;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

	List<UserEvent> findByUseridAndUuid(String userid, String uuid);

	List<UserEvent> findByStatusOrderByCreateTime(boolean status);
	
	long countByUseridAndStatusAndEventTimeGreaterThanAndEventTimeLessThanAndTargetSystemAndEventType(String userid,
			boolean status, ZonedDateTime periodStart, ZonedDateTime periodEnd, String targetSystem, PointEventType eventType);

	long deleteByValidateToLessThan(ZonedDateTime now);

	@Query(value="select count(*) from user_event where userid = ?1 and status = ?2 and event_time > ?3 and event_time < ?4 and event_type = ?5 and apply_task_id = ?6", nativeQuery = true)
	long countByConditions(String userid, boolean status, ZonedDateTime periodStart, ZonedDateTime periodEnd, String eventType, Long taskId);

}
