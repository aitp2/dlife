package com.aitp.dlife.repository;

import com.aitp.dlife.domain.FitnessActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FitnessActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FitnessActivityRepository extends JpaRepository<FitnessActivity, Long> {

}
