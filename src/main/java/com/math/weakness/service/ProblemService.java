package com.math.weakness.service;

import com.math.weakness.config.auth.dto.SessionUser;
import com.math.weakness.domain.Problem;
import com.math.weakness.domain.User;
import com.math.weakness.domain.UserProblem;
import com.math.weakness.dto.PageResponse;
import com.math.weakness.dto.ProblemShow;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.dto.UserProblemDto;
import com.math.weakness.repository.ProblemRepository;
import com.math.weakness.repository.UserProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final HttpSession httpSession;
    private final UserService userService;
    private final UserProblemRepository userProblemRepository;

    public void saveResult(UserProblemDto userProblemDto) {
        long userId = userProblemDto.getUserId();
        User user = userService.findById(userId);
        long problemId = userProblemDto.getProblemId();
        Problem problem = problemRepository.findById(problemId).get();
        String answer = userProblemDto.getAnswer();
        String problemAnswer = problem.getAnswer();
        log.info("problemAnswer = {}", problemAnswer);
        log.info("answer = {}", answer);
        boolean status;
        if (problemAnswer.equals(answer)) {
            status = true;
        } else {
            status = false;
        }
        userProblemRepository.save(UserProblem.builder()
                .user(user)
                .problem(problem)
                .status(status)
                .build());
    }


    /**
     * 추가
     */
    public Long addProblem(ProblemRequestDto params) {
        Problem entity = problemRepository.save(params.toEntity());
        return entity.getProblemId();
    }

    /**
     * 삭제
     */
    public void deleteProblemById(Long id) {
        problemRepository.deleteById(id);
    }

    /**
     * 문제 조회
     */
    public ProblemResponseDto findById(Long id) {
        Problem foundProblem = problemRepository.findById(id).get();
        ProblemResponseDto problemResponseDto = new ProblemResponseDto(foundProblem);
        return problemResponseDto;
    }

    /**
     * 페이징 처리
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PageResponse showAllProblems(Pageable pageable, Integer difficulty,
            Boolean status) {
        if (httpSession.getAttribute("user") != null) {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            Long id = userService.findByEmail(user.getEmail());
            Page<ProblemShow> problemList = problemRepository.findByDifficultyAndStatusAndId(id,
                    pageable, difficulty, status);
            PageResponse pageResponse = new PageResponse(problemList);
            return pageResponse;
        }
        Page<ProblemShow> problemList = problemRepository.findByDifficultyAndStatus(pageable, difficulty, status);
        PageResponse pageResponse = new PageResponse(problemList);

        return pageResponse;
    }


    /**
     * 수정
     */
    public Long problemUpdate(Long id, ProblemRequestDto params) {
        Problem entity = problemRepository.findById(id).get();
        entity.update(params.getTitle(), params.getAnswer(), params.getAuthor());
        return id;
    }


}
