package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.ImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Image and its DTO ImageDTO.
 */
@Mapper(componentModel = "spring", uses = {RecipeMapper.class, EvaluateMapper.class, InstantMapper.class})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "evaluat.id", target = "evaluatId")
    @Mapping(target = "createTime",expression = "java(InstantMapper.toDateString(image.getCreateTime()))")
    ImageDTO toDto(Image image);

    @Mapping(source = "recipeId", target = "recipe")
    @Mapping(source = "evaluatId", target = "evaluat")
    @Mapping(target = "createTime",expression = "java(InstantMapper.fromString(imageDTO.getCreateTime()))")
    Image toEntity(ImageDTO imageDTO);

    default Image fromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
