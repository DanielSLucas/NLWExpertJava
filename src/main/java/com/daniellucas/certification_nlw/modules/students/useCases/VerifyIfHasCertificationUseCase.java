package com.daniellucas.certification_nlw.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniellucas.certification_nlw.modules.students.dtos.VerifyHasCertificationDTO;
import com.daniellucas.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyIfHasCertificationUseCase {
  @Autowired
  private CertificationStudentRepository certificationStudentRepository;

  public boolean execute(VerifyHasCertificationDTO dto) {
    var result = this.certificationStudentRepository.findByStudentEmailAndTechnology(dto.getEmail(), dto.getTechnology());

    boolean hasCertification = !result.isEmpty();

    return hasCertification;
  }
}
