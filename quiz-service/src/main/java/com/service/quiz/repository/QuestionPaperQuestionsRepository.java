package com.service.quiz.repository;

import com.service.quiz.entity.QuestionPaperQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPaperQuestionsRepository extends JpaRepository<QuestionPaperQuestions,Integer> {
    QuestionPaperQuestions findById(int id);

}
