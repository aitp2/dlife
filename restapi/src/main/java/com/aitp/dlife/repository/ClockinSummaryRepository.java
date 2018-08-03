package com.aitp.dlife.repository;

import com.aitp.dlife.domain.ClockinSummary;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the ClockinSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClockinSummaryRepository extends JpaRepository<ClockinSummary, Long> {

	@Query("select clockinSummary from ClockinSummary clockinSummary "
			+ "where clockinSummary.wechatUserId =:wechatUserId")
	ClockinSummary findByWechatUserId(@Param("wechatUserId") String wechatUserId);

}
