package com.aitp.dlife.repository;

import com.aitp.dlife.domain.PinFanActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PinFanActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinFanActivityRepository extends JpaRepository<PinFanActivity, Long> {

}
