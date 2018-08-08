package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select cm from Comment cm "
        + "where cm.objectId =:objectId and cm.channel=:channel",nativeQuery = false)
    Page<Comment> findAllForOneObject(Pageable pageable, @Param("channel")CommentChannel channel, @Param("objectId")Long objectId);
}
