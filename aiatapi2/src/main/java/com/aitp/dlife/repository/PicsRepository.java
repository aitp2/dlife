package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Pics;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PicsRepository extends JpaRepository<Pics, Long> {

}
