package com.math.weakness.dto;

import com.math.weakness.domain.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemRequestDto {

    private String title;
    private String answer;
    private String problemImageAddress;
    private String problemImageName;
    private String solutionImageAddress;
    private String solutionImageName;
    private Integer difficulty;
    private String author;

    @Builder
    private ProblemRequestDto(String title, String answer, String problemImageAddress, String problemImageName,
                              String solutionImageAddress, String solutionImageName, Integer difficulty, String author) {
        this.title = title;
        this.answer = answer;
        this.problemImageAddress = problemImageAddress;
        this.problemImageName = problemImageName;
        this.solutionImageAddress = solutionImageAddress;
        this.solutionImageName = solutionImageName;
        this.difficulty = difficulty;
        this.author = author;
    }

    public Problem toEntity() {
        return Problem.builder()
                .title(title)
                .answer(answer)
                .problemImageName(problemImageName)
                .solutionImageName(solutionImageName)
                .difficulty(difficulty)
                .author(author)
                .build();
    }

}
