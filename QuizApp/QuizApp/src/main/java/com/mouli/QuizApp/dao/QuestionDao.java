package com.mouli.QuizApp.dao;

import com.mouli.QuizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM questions q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionByCategroy(String category, int numQ);
}
