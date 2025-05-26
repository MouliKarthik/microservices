package com.mouli.QuizService.controller;

import com.mouli.QuizService.model.QuestionWrapper;
import com.mouli.QuizService.model.QuizDto;
import com.mouli.QuizService.model.Response;
import com.mouli.QuizService.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        System.out.println(quizDto.getCategoryName() + quizDto.getNumQuestions() + quizDto.getTitle());
        return quizService.createQuiz(quizDto.getCategoryName() , quizDto.getNumQuestions() , quizDto.getTitle());
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz (@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}
