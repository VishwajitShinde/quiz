package com.api.quiz.controller;

import com.api.quiz.entity.QuestionBank;
import com.api.quiz.service.ActiveUserDetailRetrivalService;
import com.api.quiz.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = { RequestMethod.POST, RequestMethod.GET ,
                RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.HEAD,
                RequestMethod.TRACE, RequestMethod.OPTIONS,  }, maxAge = 3600 )
@RestController
public class QuestionBankController {

    @Autowired
    private QuestionBankService service;

    @Autowired
    ActiveUserDetailRetrivalService activeUserDetailRetrivalService;

    @PostMapping("/addQuestion")
    @PreAuthorize("hasRole('TEACHER')")
    public QuestionBank addQuestion(@RequestBody QuestionBank question) {
        question.setExamSetterId(activeUserDetailRetrivalService.getId());
        return service.saveQuestion(question);
    }

    @PostMapping("/addQuestions")
    @PreAuthorize("hasRole('TEACHER')")
    public List<QuestionBank> addQuestions(@RequestBody List<QuestionBank> questions) {
        // set examination setter id here
        return service.saveQuestions(questions);
    }

    @GetMapping("/questions")
    public List<QuestionBank> findAllQuestions() {
        return service.getQuestions();
    }

    @GetMapping("/questionById/{id}")
    public QuestionBank findQuestionById(@PathVariable int id) {
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
