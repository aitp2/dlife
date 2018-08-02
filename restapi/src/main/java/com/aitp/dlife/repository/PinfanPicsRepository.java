package com.aitp.dlife.repository;

import com.aitp.dlife.domain.PinfanPics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PinfanPics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinfanPicsRepository extends JpaRepository<PinfanPics, Long> {

}
