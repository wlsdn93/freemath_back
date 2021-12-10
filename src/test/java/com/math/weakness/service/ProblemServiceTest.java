package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.repository.SpringDataProblemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProblemServiceTest {

    @Autowired
    ProblemService problemService;

    @Autowired
    SpringDataProblemRepository problemRepository;

    @Test
    @DisplayName("문제추가")
    void addProblem() {
        //Given
        Problem problem = Problem.builder()
                .title("title")
                .answer("answer")
                .difficulty(4)
                .build();
        //When
        problemRepository.save(problem);

        //Then
        Problem foundProblem = problemRepository.findById(problem.getProblemId()).get();
        Assertions.assertThat(problem.getTitle()).isEqualTo(foundProblem.getTitle());
    }

}