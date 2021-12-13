package com.math.weakness.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Problem {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROBLEM_ID")
    private Long problemId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "PROBLEM_IMAGE_NAME")
    private String problemImageName;

    @Column(name = "SOLUTION_IMAGE_NAME")
    private String solutionImageName;

    @Column(name = "DIFFICULTY")
    private Integer difficulty;

    @Column(name = "AUTHOR", nullable = true)
    private String author;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserProblem> userProblems = new ArrayList<>();

    @OneToMany(mappedBy = "problem",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ProblemTag> problemTags = new ArrayList<>();

    @Builder
    public Problem(String title, String answer, String problemImageName,
                   String solutionImageName, Integer difficulty, String author) {
        this.title = title;
        this.answer = answer;
        this.problemImageName = problemImageName;
        this.solutionImageName = solutionImageName;
        this.difficulty = difficulty;
        this.author = author;
    }

    public void update(String title, String answer, String author) {
        this.title = title;
        this.answer = answer;
        this.author = author;
        this.modifiedDate = LocalDateTime.now();
    }
}
