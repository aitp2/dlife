package com.aitp.dlife.pinfan.repository;

import com.aitp.dlife.pinfan.domain.Attendee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Attendee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {

}
