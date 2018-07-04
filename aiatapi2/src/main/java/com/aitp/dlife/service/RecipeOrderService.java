package com.aitp.dlife.service;

import java.util.ArrayList;
import java.util.List;

import com.aitp.dlife.service.mapper.InstantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aitp.dlife.domain.Image;
import com.aitp.dlife.domain.Recipe;
import com.aitp.dlife.domain.RecipeOrder;
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

    public RecipeOrderService(RecipeOrderRepository recipeOrderRepository, RecipeOrderMapper recipeOrderMapper,RecipeRepository recipeRepository) {
        this.recipeOrderRepository = recipeOrderRepository;
        this.recipeOrderMapper = recipeOrderMapper;
        this.recipeRepository = recipeRepository;
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
//    		List<Image> listimage = imageRepository.findByRecipeId(recipeOrderDTO.getRecipeId());
//    		if(listimage.size() > 0) {
//    			recipeOrderDTO.setImageURL(listimage.get(0).getOssPath());
//    		}
    		recipeOrderDTO.setRecipeTile(recipe.getTitle());
    		recipeOrderDTO.setRecipeStartTime(InstantMapper.toDateString(recipe.getStartTime()));
    	}
		return page_return;
	}

    public List<RecipeOrderDTO> findAllByRecipeId(Long recipeId){
    	List<RecipeOrderDTO> list = new ArrayList<RecipeOrderDTO>();
    	List<RecipeOrder> list_RecipeOrder = recipeOrderRepository.findAllByRecipeId( recipeId);
    	for(RecipeOrder recipeOrder:list_RecipeOrder) {
    		list.add(recipeOrderMapper.toDto(recipeOrder));
    	}
    	return list;
    }

    /**
     * find all the recipe orders by id and version
     * @param recipeId
     * @param version
     * @return the recipe orders
     */
    public List<RecipeOrder> findAllByRecipedIdAndVersion(Long recipeId, Integer version)
    {
        List<RecipeOrder> list_RecipeOrder = recipeOrderRepository.findAllByRecipeIdAndVersion(recipeId,version);
        return list_RecipeOrder;
    }
}
