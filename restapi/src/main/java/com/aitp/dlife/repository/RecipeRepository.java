package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Recipe;
import com.aitp.dlife.domain.RecipeOrder;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Recipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	Page<Recipe> findAllByWechatUserId(Pageable pageable, String wechatUserId);
	
}
