package com.math.weakness.service;

import com.math.weakness.domain.Problem;
import com.math.weakness.domain.UserProblem;
import com.math.weakness.dto.PageResponse;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.dto.UserProblemDto;
import com.math.weakness.repository.ProblemRepository;
import com.math.weakness.repository.UserProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final HttpSession httpSession;
    private final UserService userService;
    private final UserProblemRepository userProblemRepository;

    public void saveResult(UserProblemDto userProblemDto) {
        Problem problem = problemRepository.findById(userProblemDto.getProblemId())
                .orElseThrow();

        String answer = userProblemDto.getAnswer();
        String problemAnswer = problem.getAnswer();

        log.info("problemAnswer = {}", problemAnswer);
        log.info("answer = {}", answer);

        userProblemRepository.save(UserProblem.builder()
                .user(userService.findById(userProblemDto.getUserId()))
                .problem(problem)
                .status(problemAnswer.equals(answer))
                .build());
    }

    public Long addProblem(ProblemRequestDto params) {
        return problemRepository.save(params.toEntity())
                .getProblemId();
    }

    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    public ProblemResponseDto findById(Long id) {
        Problem foundProblem = problemRepository.findById(id)
                .orElseThrow();

        return ProblemResponseDto.from(foundProblem);
    }

    @Transactional(readOnly = true)
    public PageResponse showAllProblems(
            Pageable pageable,
            Integer difficulty,
            Boolean status
    ) {
        if (httpSession.getAttribute("user") != null) {

            Long id = 1L;
            return new PageResponse(problemRepository.findByDifficultyAndStatusAndId(id, pageable, difficulty, status));
        }
        return new PageResponse(problemRepository.findByDifficultyAndStatus(pageable, difficulty, status));
    }

    public Long problemUpdate(Long id, ProblemRequestDto params) {
        problemRepository.findById(id)
                .orElseThrow()
                .update(params.getTitle(), params.getAnswer(), params.getAuthor());

        return id;
    }

}
