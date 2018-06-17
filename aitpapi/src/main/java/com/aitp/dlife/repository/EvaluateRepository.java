package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Evaluate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Evaluate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Long> {

}
