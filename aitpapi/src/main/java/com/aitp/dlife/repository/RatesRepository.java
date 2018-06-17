package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Rates;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {

}
