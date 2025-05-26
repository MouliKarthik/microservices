package com.mouli.QuestionService.controller;

import com.mouli.QuestionService.model.Question;
import com.mouli.QuestionService.model.QuestionWrapper;
import com.mouli.QuestionService.model.Response;
import com.mouli.QuestionService.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions){
        System.out.println(categoryName + " " + numQuestions);
        ResponseEntity<List<Integer>> l = questionService.getQuestionForQuiz(categoryName,numQuestions);
        System.out.println(l);
        return l;
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsforId(@RequestBody List<Integer> questionsIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionById(questionsIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
