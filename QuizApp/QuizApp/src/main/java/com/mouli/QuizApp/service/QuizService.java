package com.mouli.QuizApp.service;

import com.mouli.QuizApp.dao.QuestionDao;
import com.mouli.QuizApp.dao.QuizDao;
import com.mouli.QuizApp.model.Question;
import com.mouli.QuizApp.model.QuestionWrapper;
import com.mouli.QuizApp.model.Quiz;
import com.mouli.QuizApp.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, String title, int numQ) {
        List<Question> questions = questionDao.findRandomQuestionByCategroy(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(Integer id) {
       Optional<Quiz> quiz =  quizDao.findById(id);
       List<Question> questionsFromDB = quiz.get().getQuestions();
       List<QuestionWrapper> questionForUser = new ArrayList<>();

       for(Question q : questionsFromDB){
           QuestionWrapper qw = new QuestionWrapper();
           qw.setId(q.getId());
           qw.setQuestionTitle(q.getQuestionTitle());
           qw.setOption1(q.getOption1());
           qw.setOption2(q.getOption2());
           qw.setOption3(q.getOption3());
           qw.setOption4(q.getOption4());
           questionForUser.add(qw);
       }

       return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right =0;
        int i=0;
        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;

        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
