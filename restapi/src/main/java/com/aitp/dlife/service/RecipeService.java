package com.aitp.dlife.service;

import java.util.List;

import com.aitp.dlife.domain.RecipeOrder;
import com.aitp.dlife.repository.RecipeOrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.Recipe;
import com.aitp.dlife.repository.RecipeRepository;
import com.aitp.dlife.service.dto.RecipeDTO;
import com.aitp.dlife.service.mapper.RecipeMapper;
import org.springframework.util.CollectionUtils;


/**
 * Service Implementation for managing Recipe.
 */
@Service
@Transactional
public class RecipeService {

    private final Logger log = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;


    private final RecipeOrderRepository recipeOrderRepository;

    public RecipeService(RecipeRepository recipeRepository, RecipeMapper recipeMapper,RecipeOrderRepository recipeOrderRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.recipeOrderRepository = recipeOrderRepository;
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

        Page<Recipe> page_recipe = recipeRepository.findAll(pageable);

        if(page_recipe!=null && page_recipe.getContent() != null){
            for(Recipe recipe:page_recipe.getContent()){
                if(!Hibernate.isInitialized(recipe.getImages())){
                    Hibernate.initialize(recipe.getImages());
                }
            }
        }

        Page<RecipeDTO> page_return = page_recipe.map(recipeMapper::toDto);

		return page_return;
    }

    /**
     * Get all the recipes.
     *
     * @param pageable the pagination information
     * @param currentUserId the current user id, will be used to check is the user could book the recipes
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RecipeDTO> findAll(Pageable pageable, String currentUserId) {
        log.debug("Request to get all Recipes");

        Page<Recipe> page_recipe = recipeRepository.findAll(pageable);

        if(page_recipe!=null && page_recipe.getContent() != null){
            for(Recipe recipe : page_recipe.getContent()){
                if(!Hibernate.isInitialized(recipe.getImages())){
                    Hibernate.initialize(recipe.getImages());
                }
            }
        }

        Page<RecipeDTO> page_return = page_recipe.map(recipeMapper::toDto);

        if (!CollectionUtils.isEmpty(page_return.getContent()))
        {
            for( RecipeDTO recipeDTO : page_return.getContent())
            {
                recipeDTO.setBookStatus(checkCouldBookRecipe(recipeDTO,currentUserId));
            }
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

        if(recipe!=null){
            if(!Hibernate.isInitialized(recipe.getImages())){
                Hibernate.initialize(recipe.getImages());
            }
        }

        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);
        return recipeDTO;
    }

    /**
     * Get one recipe by id.
     *
     * @param id the id of the entity
     * @param currentUserId the current user id, will be used to check is the user could book the recipes
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RecipeDTO findOne(Long id, String currentUserId) {
        log.debug("Request to get Recipe : {}", id);
        Recipe recipe = recipeRepository.findOne(id);

        if(recipe!=null){
            if(!Hibernate.isInitialized(recipe.getImages())){
                Hibernate.initialize(recipe.getImages());
            }
        }

        RecipeDTO recipeDTO = recipeMapper.toDto(recipe);

        recipeDTO.setBookStatus(checkCouldBookRecipe(recipeDTO,currentUserId));

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

        Page<Recipe> page_recipe = recipeRepository.findAllByWechatUserId(pageable, wechatUserId);

        if(page_recipe!=null && page_recipe.getContent() != null){
            for(Recipe recipe:page_recipe.getContent()){
                if(!Hibernate.isInitialized(recipe.getImages())){
                    Hibernate.initialize(recipe.getImages());
                }
            }
        }

        Page<RecipeDTO> page_return = page_recipe.map(recipeMapper::toDto);

        return page_return;
	}


    /**
     * check is the current user could or have been booked the recipe
     * @param recipe the recipe id
     * @param currentUserId the current user id
     * @return String OUT_STOCK: the recipe have been all sold out
     *                BOOKED: the current user have booked the recipe
     *                AVAILABLE: the current user could book the recipe
     */
	protected String checkCouldBookRecipe(RecipeDTO recipe, String currentUserId)
    {

        if (recipe == null || recipe.getId() == null || recipe.getPublishVersion() == null
            || StringUtils.isEmpty(currentUserId) || recipe.getNum() == null
            || recipe.getNum().intValue() < 1)
        {
            return "OUT_STOCK";
        }

        List<RecipeOrder> recipeOrders = recipeOrderRepository.findAllByRecipeIdAndVersion(recipe.getId(),recipe.getPublishVersion());

        if (CollectionUtils.isEmpty(recipeOrders))
        {
            return "AVAILABLE";
        }
        else
        {
            boolean booked = false;
            for(RecipeOrder recipeOrder : recipeOrders)
            {
                if (recipeOrder != null && currentUserId.equals(recipeOrder.getWechatUserId()))
                {
                    booked = true;
                    break;
                }
            }

            if (booked)
            {
                return "BOOKED";
            }
            else
            {
               if (recipe.getNum().intValue() > recipeOrders.size())
               {
                   return "AVAILABLE";
               }
               else
               {
                   return "OUT_STOCK";
               }
            }

        }
    }
}
