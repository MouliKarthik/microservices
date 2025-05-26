package com.mouli.QuizService.service;


import com.mouli.QuizService.dao.QuizDao;
import com.mouli.QuizService.feign.QuizInterface;
import com.mouli.QuizService.model.QuestionWrapper;
import com.mouli.QuizService.model.Quiz;
import com.mouli.QuizService.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;



    public ResponseEntity<String> createQuiz(String category,  Integer numQ, String title) {
        //call the generate url - Rest Template http://localhost:8080/question/generate
        List<Integer> questions = quizInterface.getQuestionForQuiz(category,numQ).getBody();
        System.out.println(questions);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        System.out.println(quiz);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(Integer id) {
        Quiz quiz =  quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();

        List<QuestionWrapper> questions = quizInterface.getQuestionsforId(questionIds).getBody();

       return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
