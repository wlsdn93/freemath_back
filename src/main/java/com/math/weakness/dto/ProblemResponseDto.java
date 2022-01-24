package com.math.weakness.dto;

import com.math.weakness.domain.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class ProblemResponseDto {

    private final Long problemId;
    private final String title;
    private final String answer;
    private final String problemImageName;
    private final String solutionImageName;
    private final Integer difficulty;
    private final String author;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    private ProblemResponseDto(Problem entity) {
        this.problemId = entity.getProblemId();
        this.title = entity.getTitle();
        this.answer = entity.getAnswer();
        this.problemImageName = entity.getProblemImageName();
        this.solutionImageName = entity.getSolutionImageName();
        this.difficulty = entity.getDifficulty();
        this.author = entity.getAuthor();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }

    public static ProblemResponseDto from(Problem entity) {
        return new ProblemResponseDto(entity);
    }
}
