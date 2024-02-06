package com.daniellucas.certification_nlw.modules.students.useCases;

import org.springframework.stereotype.Service;

import com.daniellucas.certification_nlw.modules.students.dtos.VerifyHasCertificationDTO;

@Service
public class VerifyIfHasCertificationUseCase {
  public boolean execute(VerifyHasCertificationDTO dto) {
    if(dto.getEmail().equals("daniel@email.com") && dto.getTechnology().equals("JAVA")) {
      return true;
    }

    return false;
  }
}
