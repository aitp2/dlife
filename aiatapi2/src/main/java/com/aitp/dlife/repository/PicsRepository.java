package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Pics;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PicsRepository extends JpaRepository<Pics, Long> {

}
