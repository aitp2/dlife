package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Message;
import com.aitp.dlife.domain.enumeration.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM Message AS m left JOIN Event_Message AS em"
        + " on m.event_message_id = em.id where "
        + "  m.wechat_user_id=:wechatUserId"
        + " and m.jhi_read=:read and em.jhi_type in (:eventTypes)"
        + "  order by em.create_time desc"
        ,nativeQuery = true)
    List<Message> findMessageByUser( @Param("wechatUserId")String wechatUserId, @Param("read")boolean read , @Param("eventTypes")List<String> eventTypes);

}
