package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.dto.Form;
import com.math.weakness.dto.UserProblemDto;
import com.math.weakness.repository.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProblemServiceTest {

    @Autowired
    ProblemService problemService;

    @Autowired
    ProblemRepository problemRepository;


    @Test
    @DisplayName("문제추가")
    void addProblem() {
//        //Given
//        Form form = Form.builder()
//                .title("test")
//                .answerType("choice")
//                .answer("3")
//                .difficulty(4)
//                .subject("CommonMath1")
//                .build();
//
//        //When
//        Long problemId = problemService.addProblem(form);
//
//        //Then
//        Problem foundProblem = problemRepository.findById(problemId).get();
//        assertThat(form.getTitle()).isEqualTo(foundProblem.getTitle());
    }

    @Test
    @DisplayName("문제삭제")
    void deleteProblem() {
//        //Given
//        Form form = Form.builder()
//                .title("test")
//                .answerType("choice")
//                .answer("3")
//                .difficulty(4)
//                .subject("CommonMath1")
//                .problemImageName("test")
//                .solutionImageName("test")
//                .build();
//
//        //When
//        int beforeSize = problemRepository.findAll().size();
//        Long problemId1 = problemService.addProblem(form);
//        System.out.println("after add problem" + problemRepository.findAll().size());
//        problemService.deleteProblem(problemId1);
//        int afterSize = problemRepository.findAll().size();
//
//        //Then
//        assertThat(beforeSize).isEqualTo(afterSize);

    }
    @Test
    @Rollback(value = false)
    void testSampleAdd() {
//        int[] difficulties = {2, 3, 4};
//        String[] subjects = {"CommonMath1", "CommonMath2", "Calculus", "ProbabilityAndStatistic", "GeometryAndVector"};
//        for( long i = 1L ; i <= 500 ; i++) {
//            String subject = subjects[new Random().nextInt(subjects.length)];
//            //Given
//            Form form = Form.builder()
//                    .title("test")
//                    .answerType("choice")
//                    .answer("3")
//                    .difficulty(difficulties[new Random().nextInt(difficulties.length)])
//                    .subject(subject)
//                    .build();
//
//            //When
//            problemService.addProblem(form);
//        }
    }

    @Test
    @Rollback(value = false)
    void saveResult() {
//        Long userId = 1001L;
//        String[] answer = {"answer1", "answer2"};
//        for (long problemId = 21L; problemId <= 50 ; problemId++) {
//            problemService.saveResult(UserProblemDto.builder()
//                    .problemId(problemId)
//                    .userId(userId)
//                    .answer(answer[new Random().nextInt(answer.length)])
//                    .build());
//        }

    }
}












