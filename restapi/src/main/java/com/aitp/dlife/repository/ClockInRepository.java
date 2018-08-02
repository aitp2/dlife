package com.aitp.dlife.repository;

import com.aitp.dlife.domain.ClockIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ClockIn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClockInRepository extends JpaRepository<ClockIn, Long> {

}
