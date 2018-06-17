package com.aitp.dlife.repository;

import com.aitp.dlife.domain.ClockinSummary;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClockinSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClockinSummaryRepository extends JpaRepository<ClockinSummary, Long> {

}
