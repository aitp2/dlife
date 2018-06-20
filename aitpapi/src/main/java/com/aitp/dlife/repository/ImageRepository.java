package com.aitp.dlife.repository;

import com.aitp.dlife.domain.Image;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	
	@Query(value = "select image from Image image "
			+ "where image.recipe.id =:recipeId",nativeQuery = false)
	List<Image> findByRecipeId(@Param("recipeId") Long recipeId);


}
