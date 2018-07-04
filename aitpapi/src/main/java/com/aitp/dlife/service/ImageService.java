package com.aitp.dlife.service;

import com.aitp.dlife.domain.Image;
import com.aitp.dlife.repository.ImageRepository;
import com.aitp.dlife.service.dto.ImageDTO;
import com.aitp.dlife.service.mapper.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    /**
     * Get all the images.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imageRepository.findAll(pageable)
            .map(imageMapper::toDto);
    }

    /**
     * Get one image by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ImageDTO findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        Image image = imageRepository.findOne(id);
        return imageMapper.toDto(image);
    }

    /**
     * Get one image by id.
     *
     * @param recipeId the id of the recipeId
     * @return the entities
     */
    @Transactional(readOnly = true)
    public List<ImageDTO> findImagesByRecipeId(Long recipeId) {
        log.debug("Request to get Image : {}", recipeId);
        List<Image> images = imageRepository.findByRecipeId(recipeId);

        final List<ImageDTO> result = new ArrayList<>();

        if (!CollectionUtils.isEmpty(images))
        {
            for(Image image : images)
            {
                result.add(imageMapper.toDto(image));
            }
        }

        return result;
    }

    /**
     * Get one image by id.
     *
     * @param evaluatId the id of the recipeId
     * @return the entities
     */
    @Transactional(readOnly = true)
    public List<ImageDTO> findImagesByEvaluatId(Long evaluatId) {
        log.debug("Request to get Image : {}", evaluatId);
        List<Image> images = imageRepository.findByEvaluatId(evaluatId);

        final List<ImageDTO> result = new ArrayList<>();

        if (!CollectionUtils.isEmpty(images))
        {
            for(Image image : images)
            {
                result.add(imageMapper.toDto(image));
            }
        }

        return result;
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.delete(id);
    }
}
