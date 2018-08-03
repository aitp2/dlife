package com.aitp.dlife.repository;

import com.aitp.dlife.domain.QuestionPic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the QuestionPic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionPicRepository extends JpaRepository<QuestionPic, Long> {

}
