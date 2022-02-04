package com.math.weakness.dto;

import com.math.weakness.domain.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Form {
    private String title;
    private String answerType;
    private String answer;
    private Integer difficulty;
    private String subject;
    private String problemImage;
    private String problemImageName;
    private String solutionImage;
    private String solutionImageName;

    @Builder
    public Form(String title, String answerType, String answer, Integer difficulty,
            String subject, String problemImageName,
            String solutionImageName) {
        this.title = title;
        this.answerType = answerType;
        this.answer = answer;
        this.difficulty = difficulty;
        this.subject = subject;
        this.problemImageName = problemImageName;
        this.solutionImageName = solutionImageName;
    }

    public Problem toEntity() {
        return Problem.builder()
                .title(title)
                .answerType(answerType)
                .answer(answer)
                .difficulty(difficulty)
                .subject(subject)
                .problemImageName(problemImageName)
                .problemImageName(solutionImageName)
                .build();
    }
}
