package com.mouli.QuestionService.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String difficultylevel;
    private String category;
    private String rightAnswer;

}
