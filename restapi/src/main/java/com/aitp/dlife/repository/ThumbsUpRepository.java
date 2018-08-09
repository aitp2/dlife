package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.domain.ThumbsUp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ThumbsUp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThumbsUpRepository extends JpaSpecificationExecutor<ThumbsUp>,JpaRepository<ThumbsUp, Long> {

}
