package com.daniellucas.certification_nlw.modules.students.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniellucas.certification_nlw.modules.students.dtos.VerifyHasCertificationDTO;
import com.daniellucas.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/students")
public class StudentController {
  
  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;
  
  @PostMapping("/verifyIfHasCertification")
  public String verify(@RequestBody VerifyHasCertificationDTO data) {
    var result = this.verifyIfHasCertificationUseCase.execute(data);
      
    return result ? "True" : "false";
  }
  
}
