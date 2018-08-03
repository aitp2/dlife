package com.aitp.dlife.service;

import com.aitp.dlife.domain.RecipeOrder;
import com.aitp.dlife.repository.RecipeOrderRepository;
import com.aitp.dlife.service.dto.RecipeOrderDTO;
import com.aitp.dlife.service.mapper.RecipeOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing RecipeOrder.
 */
@Service
@Transactional
public class RecipeOrderService {

    private final Logger log = LoggerFactory.getLogger(RecipeOrderService.class);

    private final RecipeOrderRepository recipeOrderRepository;

    private final RecipeOrderMapper recipeOrderMapper;

    public RecipeOrderService(RecipeOrderRepository recipeOrderRepository, RecipeOrderMapper recipeOrderMapper) {
        this.recipeOrderRepository = recipeOrderRepository;
        this.recipeOrderMapper = recipeOrderMapper;
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
    public Optional<RecipeOrderDTO> findOne(Long id) {
        log.debug("Request to get RecipeOrder : {}", id);
        return recipeOrderRepository.findById(id)
            .map(recipeOrderMapper::toDto);
    }

    /**
     * Delete the recipeOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipeOrder : {}", id);
        recipeOrderRepository.deleteById(id);
    }
}
