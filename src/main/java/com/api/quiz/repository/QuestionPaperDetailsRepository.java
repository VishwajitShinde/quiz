package com.api.quiz.repository;

import com.api.quiz.entity.QuestionBank;
import com.api.quiz.entity.QuestionPaperDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPaperDetailsRepository extends JpaRepository<QuestionPaperDetails,Integer> {

    QuestionPaperDetails findById(int id);
}
