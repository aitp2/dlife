package com.aitp.dlife.repository;

import com.aitp.dlife.domain.ActivityParticipation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActivityParticipation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityParticipationRepository extends JpaRepository<ActivityParticipation, Long> {

}
