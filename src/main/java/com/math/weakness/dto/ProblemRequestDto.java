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
    private Byte[] image;
    private Integer difficulty;
    private String author;

    @Builder
    public ProblemRequestDto(String title, String answer, Byte[] image, Integer difficulty, String author) {
        this.title = title;
        this.answer = answer;
        this.image = image;
        this.difficulty = difficulty;
        this.author = author;
    }

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
