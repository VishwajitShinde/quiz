package com.service.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.quiz.entity.QuestionBank;
import com.service.quiz.service.PricingPlanService;
import com.service.quiz.service.QuestionBankService;
import com.service.quiz.util.QuestionUtil;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/question-answers/")
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.OPTIONS,})
public class SampleQuestionAnswerController {
    public static final String QUESTIONS_ANSWER_JSON_FILE_PATH = "./data/questions.json";

    private final Logger logger = LoggerFactory.getLogger(SampleQuestionAnswerController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private QuestionBankService service;

    @Autowired
    private PricingPlanService pricingPlanService;

    @Autowired
    private ResourceLoader resourceLoader;


    @RequestMapping(value = "/questions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQuestions(
            @RequestParam(value = "X-api-key", defaultValue = "FX000-FREE-PLAN", required = false)
            @RequestHeader(value = "X-api-key", defaultValue = "FX000-FREE-PLAN", required = false)
                    String apiKey ) {
        try {

            //logger.info(" FileContent : {}", QuestionUtil.getFileContent(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader));
            String content = QuestionUtil.getFileContent(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader);
            //File file = QuestionUtil.getFileFromClassPath(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader);
            List<Map<String, Object>> questions = mapper.readValue(content, List.class);
            //List<Map<String, Object>> questions = mapper.readValue(file, List.class);
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

            //logger.info(" controller : /api/questions, Get , SelectedQuestions :  {} ", mapper.writeValueAsString(selectedQuestions));
           List<String> qids = selectedQuestions.stream().map(qn -> {return qn.get("QnID") + "";}).collect(Collectors.toList());
            logger.info(" controller : /api/questions, Get , SelectedQuestions :  {} ", qids );

            Bucket bucket = pricingPlanService.resolveBucket(apiKey);
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
            if (probe.isConsumed()) {
                return ResponseEntity.ok()
                        .header("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()))
                        .body(selectedQuestions);
            }

            Map<String, String> errorResponse = new LinkedHashMap<>();

            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;

            errorResponse.put("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill))
                    .body(errorResponse);
                   // .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/answers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getAnswers(@RequestBody Set<Integer> questionIds) {
        try {
            logger.info(" controller :/api/Answers, questionIds Request Body : {} ", mapper.writeValueAsString(questionIds));
            //logger.info(" FileContent : {}", QuestionUtil.getFileContent(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader));
            String content = QuestionUtil.getFileContent(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader);
            //File file = QuestionUtil.getFileFromClassPath(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader);
            List<Map<String, Object>> questions = mapper.readValue(content, List.class);
            //List<Map<String, Object>> questions = mapper.readValue(file, List.class);
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

    @RequestMapping(value = "/populate/db", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuestionBank> populateQuestionsInDB() {
        try {
            //logger.info(" FileContent : {}", QuestionUtil.getFileContent(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader));
            String content = QuestionUtil.getFileContent(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader);
            //File file = QuestionUtil.getFileFromClassPath(QUESTIONS_ANSWER_JSON_FILE_PATH, resourceLoader);
            List<Map<String, Object>> questions = mapper.readValue(content, List.class);
            //List<Map<String, Object>> questions = mapper.readValue(file, List.class);
            logger.info(" controller : /populate/db, Get , Questions Size :  {} ", questions.size());
            return service.saveQuestions(getQuestionBank(questions));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    List<QuestionBank> getQuestionBank(List<Map<String, Object>> listMap) {
        List<QuestionBank> questionBanks = new ArrayList<>();
        for (Map<String, Object> map : listMap) {
            /*
                  "Qn": "Following picture represnts.",
                  "ImageName": "q17.png",
                  "Option1": "Full Outer Join",
                  "Option2": "Left Join",
                  "Option3": "Right Join",
                  "Option4": "Inner Join",
                  "Answer": 3
             */
            QuestionBank question =
                    new QuestionBank(map.get("Qn") + "", (String) map.get("Option1"), (String) map.get("Option2"), (String) map.get("Option3"), (String) map.get("Option4"), map.get("Answer") + "");
            question.setExamSetterId(1L);
            questionBanks.add(question);
        }
        return questionBanks;
    }

}
