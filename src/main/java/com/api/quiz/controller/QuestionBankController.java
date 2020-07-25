package com.api.quiz.controller;

import com.api.quiz.entity.Question;
import com.api.quiz.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api")
//@CrossOrigin(origins = "*", allowedHeaders = "*",
//        methods = { RequestMethod.POST, RequestMethod.GET , RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.OPTIONS,  } )
@RestController
public class QuestionBankController {

    @Autowired
    private QuestionBankService service;

    @PostMapping("/addQuestion")
    public Question addQuestion(@RequestBody Question question) {
        question.setExaSetterId(1);
        return service.saveQuestion(question);
    }

    @PostMapping("/addQuestions")
    public List<Question> addQuestions(@RequestBody List<Question> Questions) {
        // set examination setter id here
        return service.saveQuestions(Questions);
    }

    @GetMapping("/questions")
    public List<Question> findAllQuestions() {
        return service.getQuestions();
    }

    @GetMapping("/questionById/{id}")
    public Question findQuestionById(@PathVariable int id) {
        return service.getQuestionById(id);
    }

    /*
    @GetMapping("/searchQuestionByText/{text}")
    public Question findQuestionByText(@PathVariable int id) {
        return service.getQuestionByText(id);
    }
    */

    /*
    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable int id) {
        return service.deleteQuestion(id);
    }
    */

}
