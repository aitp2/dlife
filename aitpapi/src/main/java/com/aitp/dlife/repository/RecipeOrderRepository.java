package com.aitp.dlife.repository;

import com.aitp.dlife.domain.RecipeOrder;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RecipeOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeOrderRepository extends JpaRepository<RecipeOrder, Long> {

}
