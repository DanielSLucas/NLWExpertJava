package com.daniellucas.certification_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniellucas.certification_nlw.modules.questions.dtos.AlternativesResultDTO;
import com.daniellucas.certification_nlw.modules.questions.dtos.QuestionResultDTO;
import com.daniellucas.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.daniellucas.certification_nlw.modules.questions.entities.QuestionEntity;
import com.daniellucas.certification_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
  
  @Autowired
  private QuestionRepository questionRepository;

  @GetMapping("/technology/{technology}")
  public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
    var result = this.questionRepository.findByTechnology(technology);

    var toMap = result.stream().map(question -> mapQuestionToDTO(question))
      .collect(Collectors.toList());

    return toMap;
  }

  static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
    var questionResultDTO = QuestionResultDTO
      .builder()
      .id(question.getId())
      .technology(question.getTechnology())
      .description(question.getDescription())
      .build();

    List<AlternativesResultDTO> alternativesResultDTOs = question
      .getAlternatives()
      .stream()
      .map(alternative -> mapAlternativeDTO(alternative))
      .collect(Collectors.toList());

    questionResultDTO.setAlternatives(alternativesResultDTOs);

    return questionResultDTO;
  }

  static AlternativesResultDTO mapAlternativeDTO(AlternativesEntity alternatives) {
    return AlternativesResultDTO
      .builder()
      .id(alternatives.getId())
      .description(alternatives.getDescription())
      .build();
  }
}
