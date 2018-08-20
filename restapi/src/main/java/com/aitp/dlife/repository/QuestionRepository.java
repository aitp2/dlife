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

    @Query(value = "select qu from Question as qu " + "where qu.wechatUserId =:wechatUserId",nativeQuery = false)
    Page<Question> findAllQuestionsByWechatUserId(Pageable pageable, @Param("wechatUserId")String wechatUserId);

    @Query(value = "select qu from Question as qu left join Comment as cm on qu.id = cm.objectId" + " where cm.wechatUserId =:wechatUserId",nativeQuery = false)
    Page<Question> findAllAnswersByWechatUserId(Pageable pageable, @Param("wechatUserId")String wechatUserId);
}
