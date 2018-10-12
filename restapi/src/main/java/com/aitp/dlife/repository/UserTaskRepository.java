package com.aitp.dlife.repository;

import com.aitp.dlife.domain.TaskDefine;
import com.aitp.dlife.domain.UserTask;
import com.aitp.dlife.service.dto.UserTaskDTO;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the UserTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

//	@Query(value="select * from user_task u where u.userid = ?1 and u.task_id = ?2 and u.create_time > ?3 and u.create_time < ?4", nativeQuery = true)
//	List<UserTask> findByUseridAndTaskAndPeriod(String userid, Long tdID, ZonedDateTime periodStart,
//			ZonedDateTime periodEnd);

	long deleteByValidateToLessThan(ZonedDateTime now);

	@Query(value="select * from user_task u where u.userid = ?1 and u.task_id = ?2 and u.validate_to > ?3", nativeQuery = true)
	List<UserTask> findByConditions(String userid, Long taskId, ZonedDateTime current);

	List<UserTask> findByUseridAndGroupidAndValidateToGreaterThan(String userid, String groupid,
			ZonedDateTime eventTime);

	List<UserTask> findByUseridAndValidateToGreaterThanAndGroupidIsNotNull(String userid, ZonedDateTime now);
}
