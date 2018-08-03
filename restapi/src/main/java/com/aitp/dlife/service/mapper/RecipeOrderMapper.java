package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.RecipeOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RecipeOrder and its DTO RecipeOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {RecipeMapper.class, InstantMapper.class})
public interface RecipeOrderMapper extends EntityMapper<RecipeOrderDTO, RecipeOrder> {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(target = "createTime",expression = "java(InstantMapper.toDateString(recipeOrder.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.toDateString(recipeOrder.getModifyTime()))")
    RecipeOrderDTO toDto(RecipeOrder recipeOrder);

    @Mapping(source = "recipeId", target = "recipe")
    @Mapping(target = "createTime",expression = "java(InstantMapper.fromString(recipeOrderDTO.getCreateTime()))")
    @Mapping(target = "modifyTime",expression = "java(InstantMapper.fromString(recipeOrderDTO.getModifyTime()))")
    RecipeOrder toEntity(RecipeOrderDTO recipeOrderDTO);

    default RecipeOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecipeOrder recipeOrder = new RecipeOrder();
        recipeOrder.setId(id);
        return recipeOrder;
    }
}
