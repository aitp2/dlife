package com.aitp.dlife.repository;

import com.aitp.dlife.domain.EventMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventMessageRepository extends JpaRepository<EventMessage, Long> {

}
