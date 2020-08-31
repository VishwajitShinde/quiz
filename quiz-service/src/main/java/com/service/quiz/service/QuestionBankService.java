package com.service.quiz.service;

import com.service.quiz.entity.QuestionBank;
import com.service.quiz.repository.QuestionBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<QuestionBank> getQuestions(int page, int size  ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("lastModifiedTime")));
        return repository.findAll(pageable);
    }

    public QuestionBank getQuestionById(int id) {
        return repository.findById(id);
    }

    /*public String deleteQuestion(int id) {
        repository.deleteById(id);
        return "question removed !! " + id;
    }*/

}
