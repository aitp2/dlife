package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Comment;
import com.aitp.dlife.domain.EventMessage;
import com.aitp.dlife.domain.enumeration.CommentChannel;
import com.aitp.dlife.domain.enumeration.EventChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventMessageRepository extends JpaRepository<EventMessage, Long> {

    @Query(value = "select em from EventMessage em "
        + "where em.channel=:channel",nativeQuery = false)
    Page<EventMessage> findAllForChannel(Pageable pageable, @Param("channel")EventChannel channel);

    @Query(value = "select em from EventMessage em "
        + "where em.objectId =:objectId",nativeQuery = false)
    Page<EventMessage> findAllForObjectId(Pageable pageable, @Param("objectId")Long objectId);

}
