package com.aitp.dlife.repository;

import com.aitp.dlife.domain.ThumbsUp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ThumbsUp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThumbsUpRepository extends JpaRepository<ThumbsUp, Long> {

}
