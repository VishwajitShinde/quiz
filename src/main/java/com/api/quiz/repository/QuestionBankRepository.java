package com.api.quiz.repository;

import com.api.quiz.entity.Question;
import com.api.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionBankRepository extends JpaRepository<Question,Integer> {
    Question findById(int id);
}
