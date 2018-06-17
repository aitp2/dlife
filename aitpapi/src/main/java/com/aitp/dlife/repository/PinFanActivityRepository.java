package com.aitp.dlife.repository;

import com.aitp.dlife.domain.PinFanActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PinFanActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinFanActivityRepository extends JpaRepository<PinFanActivity, Long> {

}
