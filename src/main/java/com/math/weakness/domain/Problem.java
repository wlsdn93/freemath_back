package com.math.weakness.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Problem {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROBLEM_ID")
    private Long problemId;

    @OneToMany(mappedBy = "problem")
    private List<UserProblem> userProblems = new ArrayList<>();

    @Column(name = "IMAGE")
    private Byte[] image;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DIFFICULTY")
    private Integer difficulty;
}
