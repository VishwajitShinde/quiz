package com.service.quiz.repository;

import com.service.quiz.entity.QuestionPaperDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionPaperDetailsRepository extends JpaRepository<QuestionPaperDetails,Integer> {

    QuestionPaperDetails findById(int id);
}
