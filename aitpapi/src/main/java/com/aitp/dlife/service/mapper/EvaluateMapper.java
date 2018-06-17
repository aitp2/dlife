package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.EvaluateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Evaluate and its DTO EvaluateDTO.
 */
@Mapper(componentModel = "spring", uses = {RecipeOrderMapper.class})
public interface EvaluateMapper extends EntityMapper<EvaluateDTO, Evaluate> {

    @Mapping(source = "recipeOrder.id", target = "recipeOrderId")
    EvaluateDTO toDto(Evaluate evaluate);

    @Mapping(target = "images", ignore = true)
    @Mapping(source = "recipeOrderId", target = "recipeOrder")
    Evaluate toEntity(EvaluateDTO evaluateDTO);

    default Evaluate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Evaluate evaluate = new Evaluate();
        evaluate.setId(id);
        return evaluate;
    }
}
