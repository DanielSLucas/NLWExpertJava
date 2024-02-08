package com.daniellucas.certification_nlw.modules.students.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDTO {
  private UUID questionId;
  private UUID alternativeId;
  private boolean isCorrect;
}
