package com.aitp.dlife.pinfan.repository;

import com.aitp.dlife.pinfan.domain.RatesPics;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RatesPics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatesPicsRepository extends JpaRepository<RatesPics, Long> {

}
