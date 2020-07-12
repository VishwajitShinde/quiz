package com.api.quiz.service;

import com.api.quiz.entity.Question;
import com.api.quiz.repository.QuestionBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBankService {
    @Autowired
    QuestionBankRepository repository;

    public Question saveQuestion(Question question) {
        return repository.save(question);
    }

    public List<Question> saveQuestions(List<Question> questions) {
        return repository.saveAll(questions);
    }

    public List<Question> getQuestions() {
        return repository.findAll();
    }

    public Question getQuestionById(int id) {
        return repository.findById(id);
    }

    /*public String deleteQuestion(int id) {
        repository.deleteById(id);
        return "question removed !! " + id;
    }*/

}
