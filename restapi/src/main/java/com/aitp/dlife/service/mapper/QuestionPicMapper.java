package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.QuestionPicDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuestionPic and its DTO QuestionPicDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface QuestionPicMapper extends EntityMapper<QuestionPicDTO, QuestionPic> {

    @Mapping(source = "question.id", target = "questionId")
    QuestionPicDTO toDto(QuestionPic questionPic);

    @Mapping(source = "questionId", target = "question")
    QuestionPic toEntity(QuestionPicDTO questionPicDTO);

    default QuestionPic fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionPic questionPic = new QuestionPic();
        questionPic.setId(id);
        return questionPic;
    }
}
