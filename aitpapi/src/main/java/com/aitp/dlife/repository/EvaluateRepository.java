package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Evaluate;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Evaluate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Long> {
	
	@Query(value = "select evaluate from Evaluate evaluate "
			+ "where evaluate.recipeOrder.id =:recipeOrderId",nativeQuery = false)
	List<Evaluate> findAllByRecipeOrderId(@Param("recipeOrderId") Long recipeOrderId);

}
