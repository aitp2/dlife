package com.aitp.dlife.repository;

import com.aitp.dlife.domain.PinfanPics;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PinfanPics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinfanPicsRepository extends JpaRepository<PinfanPics, Long> {

}
