package com.api.quiz.repository;

import com.api.quiz.entity.QuestionPaperDetails;
import com.api.quiz.entity.QuestionPaperQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPaperQuestionsRepository extends JpaRepository<QuestionPaperQuestions,Integer> {
    QuestionPaperQuestions findById(int id);

}
