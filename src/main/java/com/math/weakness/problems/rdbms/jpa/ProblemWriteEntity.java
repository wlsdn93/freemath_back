package com.math.weakness.problems.rdbms.jpa;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "PROBLEM")
@NoArgsConstructor(access = PROTECTED)
public class ProblemWriteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}