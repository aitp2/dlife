package com.aitp.dlife.repository;

import com.aitp.dlife.domain.ClockIn;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClockIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClockInRepository extends JpaRepository<ClockIn, Long> {

}
