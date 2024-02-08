package com.daniellucas.certification_nlw.modules.students.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniellucas.certification_nlw.modules.students.dtos.StudentCertificationAnswerDTO;
import com.daniellucas.certification_nlw.modules.students.dtos.VerifyHasCertificationDTO;
import com.daniellucas.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.daniellucas.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/students")
public class StudentController {
  
  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
  
  @Autowired StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

  @PostMapping("/verifyIfHasCertification")
  public String verify(@RequestBody VerifyHasCertificationDTO data) {
    var result = this.verifyIfHasCertificationUseCase.execute(data);
      
    return result ? "Já fez a prova" : "Pode fazer a prova";
  }
  
  @PostMapping("/certification/answer")
  public ResponseEntity<Object> certificationAnswer(
    @RequestBody StudentCertificationAnswerDTO data
  ) {
    try {
      var result = this.studentCertificationAnswersUseCase.execute(data);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
