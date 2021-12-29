package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.repository.ProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProblemServiceTest {

    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemRepository problemRepository;

    @BeforeEach
    public void BeforeEach() {
        problemRepository.deleteAll();
    }

    @Test
    @DisplayName("문제추가")
    void addProblem() {
        //Given
        ProblemRequestDto problemRequestDto = ProblemRequestDto.builder()
                .title("title")
                .answer("answer")
                .author("author")
                .difficulty(4)
                .build();

        //When
        Long problemId = problemService.addProblem(problemRequestDto);

        //Then
        Problem foundProblem = problemRepository.findById(problemId).get();
        assertThat(problemRequestDto.getTitle()).isEqualTo(foundProblem.getTitle());
    }

    @Test
    @DisplayName("문제삭제")
    @Rollback(value = false)
    void deleteProblem() {
        //Given
        ProblemRequestDto problemRequestDto1 = ProblemRequestDto.builder()
                .title("title1")
                .answer("answer1")
                .author("author1")
                .difficulty(4)
                .build();
        ProblemRequestDto problemRequestDto2 = ProblemRequestDto.builder()
                .title("title2")
                .answer("answer2")
                .author("author2")
                .difficulty(3)
                .build();

        //When
        Long problemId1 = problemService.addProblem(problemRequestDto1);
        Long problemId2 = problemService.addProblem(problemRequestDto2);
        problemService.deleteProblemById(problemId1);

        //Then
        assertThat(problemRepository.findAll().size()).isEqualTo(1);

    }
    @Test
    @Rollback(value = false)
    void testSampleAdd() {
        for( int i = 0 ; i < 1000 ; i++) {
            //Given
            ProblemRequestDto problemRequestDto1 = ProblemRequestDto.builder()
                    .title("title1")
                    .answer("answer1")
                    .author("author1")
                    .difficulty(4)
                    .build();

            //When
            Long problemId1 = problemService.addProblem(problemRequestDto1);

        }
    }

}