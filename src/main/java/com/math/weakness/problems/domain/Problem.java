package com.math.weakness.problems.domain;

public record Problem(
        String title,
        String imagePath,
        String solutionPath,
        String answer,
        Integer level
) {
    public Problem {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        if (imagePath == null || imagePath.isBlank()) {
            throw new IllegalArgumentException("imagePath must not be blank");
        }
        if (solutionPath == null || solutionPath.isBlank()) {
            throw new IllegalArgumentException("solutionPath must not be blank");
        }
    }

    public Boolean isCorrect(String answer) {
        return this.answer.equals(answer);
    }
}
