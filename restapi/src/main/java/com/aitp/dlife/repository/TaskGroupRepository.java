package com.aitp.dlife.repository;

import com.aitp.dlife.domain.TaskGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaskGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

}
