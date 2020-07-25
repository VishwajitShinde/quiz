package com.api.quiz.controller;

import com.api.quiz.service.ParticipantService;
import com.api.quiz.util.QuestionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.OPTIONS,})
public class QuestionAnswerController {
    public static final String QUESTIONS_ANSWER_JSON_FILE_PATH = "G:\\TECH_LEARNING\\spring-boot\\quiz\\src\\main\\resources\\questions.json";
    @Autowired
    private ParticipantService service;

    private final Logger logger = LoggerFactory.getLogger(QuestionAnswerController.class);

    private ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(value = "/questions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getQuestions() {
        try {
            File file = new File(QUESTIONS_ANSWER_JSON_FILE_PATH) ;
            List<Map<String, Object>> questions = mapper.readValue(file, List.class);
            Set<Integer> questionNumbers = QuestionUtil.generateQuestions(questions.size(), 10);

            List<Map<String, Object>> selectedQuestions = IntStream
                    .range(0, questions.size())
                    .filter(questionInd -> questionNumbers.contains(questionInd))
                    .mapToObj(questionInd -> questions.get(questionInd))
                    .collect(Collectors.toList());

            // remove Answer
            selectedQuestions.forEach(question -> {
                question.remove("Answer");
            });

            logger.info(" controller : /api/questions, Get , SelectedQuestions :  {} ", mapper.writeValueAsString(selectedQuestions));
            return selectedQuestions;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/answers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getAnswers(@RequestBody Set<Integer> questionIds) {
        try {
            logger.info(" controller :/api/Answers, questionIds Request Body : {} ", mapper.writeValueAsString(questionIds));
            File file = new File(QUESTIONS_ANSWER_JSON_FILE_PATH) ;
            List<Map<String, Object>> questions = mapper.readValue(file, List.class);
            List<Map<String, Object>> questionAnswers = IntStream
                    .range(0, questions.size())
                    .filter(questionInd -> questionIds.contains(questionInd))
                    .mapToObj(questionInd -> questions.get(questionInd))
                    .collect(Collectors.toList());
            Map<Integer, Integer> answers = questionAnswers.stream()
                    .collect(Collectors.toMap(x -> (int) x.get("QnID"), x -> (int) x.get("Answer")));
            return answers;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
