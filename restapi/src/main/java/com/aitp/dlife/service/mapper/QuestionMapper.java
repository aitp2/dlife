package com.aitp.dlife.service.mapper;

import com.aitp.dlife.domain.*;
import com.aitp.dlife.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {InstantMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    @Mapping(target = "createTime", expression = "java(InstantMapper.toDateString(question.getCreateTime()))")
    QuestionDTO toDto(Question question);

    @Mapping(target = "questionPics", ignore = true)
    @Mapping(target = "createTime", expression = "java(InstantMapper.fromString(questionDTO.getCreateTime()))")
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
