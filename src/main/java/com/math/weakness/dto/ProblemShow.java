package com.math.weakness.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProblemShow {

    private Long problemId;
    private String title;
    private Integer difficulty;
    private Boolean status;

    @Builder
    private ProblemShow(Long problemId, String title, Integer difficulty, Boolean status) {
        this.problemId = problemId;
        this.title = title;
        this.difficulty = difficulty;
        this.status = status;
    }
}
