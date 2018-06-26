package com.aitp.dlife.service;

import com.aitp.dlife.domain.Evaluate;
import com.aitp.dlife.domain.Image;
import com.aitp.dlife.domain.Recipe;
import com.aitp.dlife.domain.RecipeOrder;
import com.aitp.dlife.repository.EvaluateRepository;
import com.aitp.dlife.repository.ImageRepository;
import com.aitp.dlife.service.dto.EvaluateDTO;
import com.aitp.dlife.service.dto.RecipeDTO;
import com.aitp.dlife.service.dto.RecipeOrderDTO;
import com.aitp.dlife.service.mapper.EvaluateMapper;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Evaluate.
 */
@Service
@Transactional
public class EvaluateService {

    private final Logger log = LoggerFactory.getLogger(EvaluateService.class);

    private final EvaluateRepository evaluateRepository;

    private final EvaluateMapper evaluateMapper;

    private final ImageRepository imageRepository;

    public EvaluateService(EvaluateRepository evaluateRepository, EvaluateMapper evaluateMapper,ImageRepository imageRepository) {
        this.evaluateRepository = evaluateRepository;
        this.evaluateMapper = evaluateMapper;
        this.imageRepository = imageRepository;
    }

    /**
     * Save a evaluate.
     *
     * @param evaluateDTO the entity to save
     * @return the persisted entity
     */
    public EvaluateDTO save(EvaluateDTO evaluateDTO) {
        log.debug("Request to save Evaluate : {}", evaluateDTO);
        Evaluate evaluate = evaluateMapper.toEntity(evaluateDTO);
        evaluate = evaluateRepository.save(evaluate);
        return evaluateMapper.toDto(evaluate);
    }

    /**
     * Get all the evaluates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EvaluateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluates");

        Page<Evaluate> page_evaluate = evaluateRepository.findAll(pageable);

        if(page_evaluate!=null && page_evaluate.getContent() != null){
            for(Evaluate evaluate:page_evaluate.getContent()){
                if(!Hibernate.isInitialized(evaluate.getImages())){
                    Hibernate.initialize(evaluate.getImages());
                }
            }
        }

        Page<EvaluateDTO> page_return = page_evaluate.map(evaluateMapper::toDto);

        return page_return;
    }

    /**
     * Get one evaluate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EvaluateDTO findOne(Long id) {
        log.debug("Request to get Evaluate : {}", id);
        Evaluate evaluate = evaluateRepository.findOne(id);

        if(evaluate!=null){
            if(!Hibernate.isInitialized(evaluate.getImages())){
                Hibernate.initialize(evaluate.getImages());
            }
        }

        return evaluateMapper.toDto(evaluate);
    }

    /**
     * Delete the evaluate by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Evaluate : {}", id);
        evaluateRepository.delete(id);
    }

    public List<EvaluateDTO> findAllByRecipeOrderId(Long recipeOrderId){
    	List<EvaluateDTO> list = new ArrayList<EvaluateDTO>();
    	for(Evaluate evaluate:evaluateRepository.findAllByRecipeOrderId(recipeOrderId)) {

            if(evaluate!=null){
                if(!Hibernate.isInitialized(evaluate.getImages())){
                    Hibernate.initialize(evaluate.getImages());
                }
            }

    		EvaluateDTO evaluateDTO = evaluateMapper.toDto(evaluate);
    		list.add(evaluateDTO);
    	}
    	return list;
    }
}
