package com.daniellucas.certification_nlw.modules.questions.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "alternatives")
public class AlternativesEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column
  private String description;

  @Column(name = "is_correct")
  private boolean isCorrect;
  
  @Column(name = "question_id")
  private UUID questionId;

  @ManyToOne
  @JoinColumn(name = "question_id", insertable = false, updatable = false)
  private QuestionEntity questionEntity;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
