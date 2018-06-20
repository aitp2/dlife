package com.aitp.dlife.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.Image;
import com.aitp.dlife.domain.Recipe;
import com.aitp.dlife.domain.RecipeOrder;
import com.aitp.dlife.repository.ImageRepository;
import com.aitp.dlife.repository.RecipeOrderRepository;
import com.aitp.dlife.repository.RecipeRepository;
import com.aitp.dlife.service.dto.RecipeOrderDTO;
import com.aitp.dlife.service.mapper.RecipeOrderMapper;


/**
 * Service Implementation for managing RecipeOrder.
 */
@Service
@Transactional
public class RecipeOrderService {

    private final Logger log = LoggerFactory.getLogger(RecipeOrderService.class);

    private final RecipeOrderRepository recipeOrderRepository;

    private final RecipeOrderMapper recipeOrderMapper;
    
    private final RecipeRepository recipeRepository;
    
    private final ImageRepository imageRepository;

    public RecipeOrderService(RecipeOrderRepository recipeOrderRepository, RecipeOrderMapper recipeOrderMapper,RecipeRepository recipeRepository,ImageRepository imageRepository) {
        this.recipeOrderRepository = recipeOrderRepository;
        this.recipeOrderMapper = recipeOrderMapper;
        this.recipeRepository = recipeRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * Save a recipeOrder.
     *
     * @param recipeOrderDTO the entity to save
     * @return the persisted entity
     */
    public RecipeOrderDTO save(RecipeOrderDTO recipeOrderDTO) {
        log.debug("Request to save RecipeOrder : {}", recipeOrderDTO);
        RecipeOrder recipeOrder = recipeOrderMapper.toEntity(recipeOrderDTO);
        recipeOrder = recipeOrderRepository.save(recipeOrder);
        return recipeOrderMapper.toDto(recipeOrder);
    }

    /**
     * Get all the recipeOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RecipeOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecipeOrders");
        return recipeOrderRepository.findAll(pageable)
            .map(recipeOrderMapper::toDto);
    }

    /**
     * Get one recipeOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RecipeOrderDTO findOne(Long id) {
        log.debug("Request to get RecipeOrder : {}", id);
        RecipeOrder recipeOrder = recipeOrderRepository.findOne(id);
        return recipeOrderMapper.toDto(recipeOrder);
    }

    /**
     * Delete the recipeOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipeOrder : {}", id);
        recipeOrderRepository.delete(id);
    }
    
    /**
     * find recipe order list By WechatUserId
     * @param pageable
     * @param wechatUserId
     * @return
     */
    public Page<RecipeOrderDTO> findAllByWechatUserId(Pageable pageable,String wechatUserId) {
    	Page<RecipeOrderDTO> page_return = recipeOrderRepository.findAllByWechatUserId(pageable,wechatUserId).map(recipeOrderMapper::toDto);
    	for(RecipeOrderDTO recipeOrderDTO:page_return.getContent()) {
    		Recipe recipe = recipeRepository.findOne(recipeOrderDTO.getRecipeId());
    		List<Image> listimage = imageRepository.findByRecipeId(recipeOrderDTO.getRecipeId());
    		if(listimage.size() > 0) {
    			recipeOrderDTO.setImageURL(listimage.get(0).getOssPath());
    		}
    		recipeOrderDTO.setRecipeTile(recipe.getTitle());
    		recipeOrderDTO.setRecipeStartTime(recipe.getStartTime());
    	}
		return page_return;
	}
}
