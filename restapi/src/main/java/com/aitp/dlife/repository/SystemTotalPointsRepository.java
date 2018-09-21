package com.aitp.dlife.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.aitp.dlife.domain.SystemTotalPoints;


/**
 * Spring Data  repository for the SystemTotalPoints entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemTotalPointsRepository extends JpaRepository<SystemTotalPoints, Long> {

	SystemTotalPoints findBySystemId(String systemPointName);

}
