package com.math.weakness.dto;

import com.math.weakness.domain.Problem;
import lombok.Getter;

@Getter
public class ProblemDetail {

    private final Long problemId;
    private final String title;
    private final String answer;
    private final String answerType;
    private final String subject;
    private final String problemImageName;
    private final String solutionImageName;
    private final Integer difficulty;

    private ProblemDetail(Problem entity) {
        this.problemId = entity.getProblemId();
        this.title = entity.getTitle();
        this.answer = entity.getAnswer();
        this.subject = entity.getSubject();
        this.answerType = entity.getAnswerType();
        this.problemImageName = entity.getProblemImageName();
        this.solutionImageName = entity.getSolutionImageName();
        this.difficulty = entity.getDifficulty();
    }

    public static ProblemDetail from(Problem entity) {
        return new ProblemDetail(entity);
    }
}
