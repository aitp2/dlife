package com.aitp.dlife.repository;

import com.aitp.dlife.domain.PinfanPics;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the PinfanPics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinfanPicsRepository extends JpaRepository<PinfanPics, Long> {

    @Query(value = "select image from PinfanPics image "
        + "where image.pinFanActivity.id =:activityId",nativeQuery = false)
    List<PinfanPics> findByActivityId(@Param("activityId") Long activityId);
}
