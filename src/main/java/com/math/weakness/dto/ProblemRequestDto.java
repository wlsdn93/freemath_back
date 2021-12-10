package com.math.weakness.dto;

import com.math.weakness.domain.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemRequestDto {

    private String title;
    private String answer;
    private Byte[] image;
    private Integer difficulty;
    private String author;

    public Problem toEntity() {
        return Problem.builder()
                .title(title)
                .answer(answer)
                .image(image)
                .difficulty(difficulty)
                .author(author)
                .build();
    }

}
