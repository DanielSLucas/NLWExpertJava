package com.daniellucas.certification_nlw.modules.students.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyHasCertificationDTO {
  private String email;
  private String technology;
}
