package com.math.weakness.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROBLEM_ID")
    private Long problemId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ANSWER_TYPE")
    private String answerType;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "DIFFICULTY")
    private Integer difficulty;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "PROBLEM_IMAGE_NAME")
    private String problemImageName;

    @Column(name = "SOLUTION_IMAGE_NAME")
    private String solutionImageName;

    @Column(name = "CREATED_DATE")
    private final LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserProblem> userProblems = new ArrayList<>();

    @Builder
    public Problem(String title, String answerType, String answer, Integer difficulty,
            String subject, String problemImageName, String solutionImageName) {
        this.title = title;
        this.answerType = answerType;
        this.answer = answer;
        this.difficulty = difficulty;
        this.subject = subject;
        this.problemImageName = problemImageName;
        this.solutionImageName = solutionImageName;
    }

    public Problem update(String title, String answer, Integer difficulty, String subject) {
        this.title = title;
        this.answer = answer;
        this.difficulty = difficulty;
        this.subject = subject;
        this.modifiedDate = LocalDateTime.now();
        return this;
    }
}
