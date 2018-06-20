package com.aitp.dlife.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.Image;
import com.aitp.dlife.domain.Recipe;
import com.aitp.dlife.repository.ImageRepository;
import com.aitp.dlife.repository.RecipeRepository;
import com.aitp.dlife.service.dto.RecipeDTO;
import com.aitp.dlife.service.mapper.RecipeMapper;


/**
 * Service Implementation for managing Recipe.
 */
@Service
@Transactional
public class RecipeService {

    private final Logger log = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;
    
    private final ImageRepository imageRepository;

    public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper,ImageRepository imageRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.imageRepository = imageRepository;
    }

    /**
     * Save a recipe.
     *
     * @param recipeDTO the entity to save
     * @return the persisted entity
     */
    public RecipeDTO save(RecipeDTO recipeDTO) {
        log.debug("Request to save Recipe : {}", recipeDTO);
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(recipe);
    }

    /**
     * Get all the recipes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RecipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recipes");
        Page<RecipeDTO> page_return = recipeRepository.findAll(pageable)
                .map(recipeMapper::toDto);
    	for(RecipeDTO recipeDTO:page_return.getContent()) {
    		List<Image> list = imageRepository.findByRecipeId(recipeDTO.getId());
    		List<String> iamgePathList = new ArrayList<String>();
    		for(Image image:list) {
    			iamgePathList.add(image.getOssPath());
    		}
    		recipeDTO.setListImageURL(iamgePathList);
    	}
		return page_return;
    }

    /**
     * Get one recipe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RecipeDTO findOne(Long id) {
        log.debug("Request to get Recipe : {}", id);
        Recipe recipe = recipeRepository.findOne(id);
        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);
        List<Image> list = imageRepository.findByRecipeId(recipeDTO.getId());
        List<String> iamgePathList = new ArrayList<String>();
		for(Image image:list) {
			iamgePathList.add(image.getOssPath());
		}
		recipeDTO.setListImageURL(iamgePathList);
        return recipeDTO;
    }

    /**
     * Delete the recipe by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Recipe : {}", id);
        recipeRepository.delete(id);
    }
    
    /**
     * find recipe list By WechatUserId
     * @param pageable
     * @param wechatUserId
     * @return
     */
    public Page<RecipeDTO> findAllByWechatUserId(Pageable pageable,String wechatUserId) {
    	Page<RecipeDTO> page_return = recipeRepository.findAllByWechatUserId(pageable, wechatUserId).map(recipeMapper::toDto);
    	for(RecipeDTO recipeDTO:page_return.getContent()) {
    		List<Image> list = imageRepository.findByRecipeId(recipeDTO.getId());
    		List<String> iamgePathList = new ArrayList<String>();
    		for(Image image:list) {
    			iamgePathList.add(image.getOssPath());
    		}
    		recipeDTO.setListImageURL(iamgePathList);
    	}
		return page_return;
	}
}
