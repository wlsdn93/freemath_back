package com.math.weakness.dto;

import com.math.weakness.domain.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class ProblemResponseDto {

    private Long problemId;
    private String title;
    private String answer;
    private String problemImageName;
    private String solutionImageName;
    private Integer difficulty;
    private String author;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ProblemResponseDto(Problem entity) {
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
}
