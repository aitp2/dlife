package com.aitp.dlife.repository;

import com.aitp.dlife.domain.FitnessActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FitnessActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FitnessActivityRepository extends JpaRepository<FitnessActivity, Long> {

}
