package com.api.quiz.repository;

import com.api.quiz.entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBankRepository extends JpaRepository<QuestionBank,Integer> {
    QuestionBank findById(int id);
}
