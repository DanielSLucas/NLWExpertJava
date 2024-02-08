package com.daniellucas.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniellucas.certification_nlw.modules.questions.entities.QuestionEntity;
import com.daniellucas.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.daniellucas.certification_nlw.modules.students.dtos.StudentCertificationAnswerDTO;
import com.daniellucas.certification_nlw.modules.students.dtos.VerifyHasCertificationDTO;
import com.daniellucas.certification_nlw.modules.students.entities.AnswerCertificationsEntity;
import com.daniellucas.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.daniellucas.certification_nlw.modules.students.entities.StudentEntity;
import com.daniellucas.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.daniellucas.certification_nlw.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private  CertificationStudentRepository certificationStudentRepository;

  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

  public CertificationStudentEntity execute(StudentCertificationAnswerDTO data) throws Exception {
    var hasCertification = verifyIfHasCertificationUseCase.execute(
      VerifyHasCertificationDTO
        .builder()
        .email(data.getEmail())
        .technology(data.getTechnology())
        .build()
    );

    if (hasCertification) {
      throw new Exception("Você já tirou sua certificação");
    }
    
    List<QuestionEntity> questions = questionRepository.findByTechnology(data.getTechnology());
    List<AnswerCertificationsEntity> answerCertifications = new ArrayList<>();

    AtomicInteger correctAnswers = new AtomicInteger(0);

    data.getQuestionAnswers()
      .stream()
      .forEach(questionAnswer -> {
        var originalQuestion = questions
          .stream()
          .filter(question -> question.getId().equals(questionAnswer.getQuestionId()))
          .findFirst()
          .get();

        var rightAlternative = originalQuestion
          .getAlternatives()
          .stream()
          .filter(altenative -> altenative.isCorrect())
          .findFirst()
          .get();

        var isCorrect = rightAlternative.getId().equals(questionAnswer.getAlternativeId());

        if (isCorrect) {
          correctAnswers.incrementAndGet();
        }

        questionAnswer.setCorrect(isCorrect);

        var certificationAnswer = AnswerCertificationsEntity
          .builder()
          .answerId(questionAnswer.getAlternativeId())
          .questionId(questionAnswer.getQuestionId())
          .isCorrect(questionAnswer.isCorrect())
          .build();
          

        answerCertifications.add(certificationAnswer);
      });

    var student = studentRepository.findByEmail(data.getEmail());
    UUID studentId;

    if(student.isEmpty()) {
      var newStudent = StudentEntity
        .builder()
        .email(data.getEmail())
        .build();

      newStudent = studentRepository.save(newStudent);

      studentId = newStudent.getId();
    } else {
      studentId = student.get().getId();
    }
  
    CertificationStudentEntity certificationStudentEntity = CertificationStudentEntity
      .builder()
      .studentId(studentId)
      .technology(data.getTechnology())
      .grade(correctAnswers.get())
      .build();

    var studentCertification = certificationStudentRepository.save(certificationStudentEntity);

    answerCertifications.stream().forEach(answerCertification -> {
      answerCertification.setCertificationId(studentCertification.getId());
      answerCertification.setCertificationStudentEntity(certificationStudentEntity);
    });

    studentCertification.setAnswerCertificationsEntity(answerCertifications);

    certificationStudentRepository.save(certificationStudentEntity);

    return studentCertification;
  }
}
