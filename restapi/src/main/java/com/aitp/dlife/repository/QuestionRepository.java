package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAllByWechatUserId(Pageable pageable, @Param("wechatUserId")String wechatUserId);
}
