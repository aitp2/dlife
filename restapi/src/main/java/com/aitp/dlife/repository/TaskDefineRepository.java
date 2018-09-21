package com.aitp.dlife.repository;

import com.aitp.dlife.domain.TaskDefine;
import com.aitp.dlife.domain.enumeration.PointEventType;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaskDefine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskDefineRepository extends JpaRepository<TaskDefine, Long> {

	List<TaskDefine> findByStatusAndEventTypeAndTargetSystemsLikeOrderByPriorityDesc(boolean staus, PointEventType eventType, String targetSystem);

	List<TaskDefine> findByStatusOrderByPriorityDesc(boolean status);

}
