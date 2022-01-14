package com.math.weakness.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProblemDto {
    private long userId;
    private long problemId;
    private String answer;

    @Builder
    public UserProblemDto(long userId, long problemId, String answer) {
        this.userId = userId;
        this.problemId = problemId;
        this.answer = answer;
    }
}
