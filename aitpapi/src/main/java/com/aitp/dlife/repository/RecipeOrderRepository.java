package com.aitp.dlife.repository;

import com.aitp.dlife.domain.RecipeOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the RecipeOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeOrderRepository extends JpaRepository<RecipeOrder, Long> {
	
	Page<RecipeOrder> findAllByWechatUserId(Pageable pageable, String wechatUserId);
	
}
