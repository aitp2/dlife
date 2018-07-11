package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Pics;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Pics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PicsRepository extends JpaRepository<Pics, Long> {

    @Query(value = "select pics from Pics pics "
        + "where pics.clockIn.id =:clockInId",nativeQuery = false)
    List<Pics> findPicsByClockInId(@Param("clockInId") Long clockInId);

    @Query(value = "select pics from Pics pics "
        + "where pics.fitnessActivity.id =:activityId",nativeQuery = false)
    List<Pics> findByActivityId(@Param("activityId") Long activityId);
}
