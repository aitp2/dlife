package com.aitp.dlife.repository;

import com.aitp.dlife.domain.PinFanActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;


/**
 * Spring Data JPA repository for the PinFanActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinFanActivityRepository extends JpaRepository<PinFanActivity, Long> {

    Page<PinFanActivity> findAllByWechatUserId(Pageable pageable,@Param("wechatUserId")String wechatUserId);

    List<PinFanActivity> findAllByIdIn(Set<Long> ids);

}
