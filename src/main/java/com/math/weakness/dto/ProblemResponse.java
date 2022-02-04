package com.math.weakness.dto;

import com.math.weakness.domain.Problem;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProblemResponse {

    private final Long problemId;
    private final String title;
    private final String answer;
    private final String answerType;
    private final String problemImageName;
    private final String solutionImageName;
    private final String difficulty;

    private ProblemResponse(Problem entity) {
        this.problemId = entity.getProblemId();
        this.title = entity.getTitle();
        this.answer = entity.getAnswer();
        this.answerType = entity.getAnswerType();
        this.problemImageName = entity.getProblemImageName();
        this.solutionImageName = entity.getSolutionImageName();
        this.difficulty = entity.getDifficulty();
    }

    public static ProblemResponse from(Problem entity) {
        return new ProblemResponse(entity);
    }
}
