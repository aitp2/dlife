package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.RecipeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recipe and its DTO RecipeDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class})
public interface RecipeMapper extends EntityMapper<RecipeDTO, Recipe> {

    @Mapping(target = "createTime",expression = "java(InstantMapper.toDateString(recipe.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.toDateString(recipe.getModifyTime()))")
    @Mapping(target = "startTime",expression = "java(InstantMapper.toDateString(recipe.getStartTime()))")
    @Mapping(target = "endTime",expression = "java(InstantMapper.toDateString(recipe.getEndTime()))")
    RecipeDTO toDto(Recipe recipe);

    @Mapping(target = "recipeOrders", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "createTime",expression = "java(InstantMapper.fromString(recipeDTO.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.fromString(recipeDTO.getModifyTime()))")
    @Mapping(target = "startTime",expression = "java(InstantMapper.fromString(recipeDTO.getStartTime()))")
    @Mapping(target = "endTime",expression = "java(InstantMapper.fromString(recipeDTO.getEndTime()))")
    Recipe toEntity(RecipeDTO recipeDTO);

    default Recipe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recipe recipe = new Recipe();
        recipe.setId(id);
        return recipe;
    }
}
