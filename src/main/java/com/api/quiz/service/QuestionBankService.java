package com.api.quiz.service;

import com.api.quiz.entity.QuestionBank;
import com.api.quiz.repository.QuestionBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBankService {
    @Autowired
    QuestionBankRepository repository;

    public QuestionBank saveQuestion(QuestionBank question) {
        return repository.save(question);
    }

    public List<QuestionBank> saveQuestions(List<QuestionBank> questions) {
        return repository.saveAll(questions);
    }

    public List<QuestionBank> getQuestions() {
        return repository.findAll();
    }

    public QuestionBank getQuestionById(int id) {
        return repository.findById(id);
    }

    /*public String deleteQuestion(int id) {
        repository.deleteById(id);
        return "question removed !! " + id;
    }*/

}
