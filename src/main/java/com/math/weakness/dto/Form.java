package com.math.weakness.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Form {
    private String problemImage;
    private String solutionImage;
    private String title;
    private String answerType;
    private String answer;
    private String difficulty;
    private String subject;

    public Form(String problemImage,
            String solutionImage, String title, String answerType, String answer,
            String difficulty, String subject) {
        this.problemImage = problemImage;
        this.solutionImage = solutionImage;
        this.title = title;
        this.answerType = answerType;
        this.answer = answer;
        this.difficulty = difficulty;
        this.subject = subject;
    }
}
