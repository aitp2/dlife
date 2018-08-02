package com.aitp.dlife.repository;

import com.aitp.dlife.domain.RecipeOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecipeOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeOrderRepository extends JpaRepository<RecipeOrder, Long> {

}
