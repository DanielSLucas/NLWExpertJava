package com.daniellucas.certification_nlw.modules.questions.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativesResultDTO {
  private UUID id;
  private String description;
}
