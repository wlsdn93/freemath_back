package com.math.weakness.web;

import com.math.weakness.dto.PageResponse;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.dto.UserProblemDto;
import com.math.weakness.service.ProblemService;
import com.math.weakness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/problems")
public class UserController {

    private final ProblemService problemService;
    private final UserService userService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping
    public PageResponse getProblems(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) String accessToken,
            @PageableDefault Pageable pageable
            ) {
        return problemService.showAllProblemsForUser(
                accessToken, pageable, difficulty, status
        );
    }

    @GetMapping("/{problemId}")
    public ResponseEntity showProblemDetail(@PathVariable long problemId,
                                    @RequestParam String accessToken) {
        ProblemResponseDto problemResponseDto = problemService.findById(problemId);
        String problemFilePath = fileDir + problemResponseDto.getProblemImageName();

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{problemId}")
    public ResponseEntity solveProblem(
            @PathVariable Long problemId,
            @RequestParam String answer,
            @RequestParam String accessToken
    ) {
        Long userId = 1L;

        UserProblemDto userProblemDto = UserProblemDto.builder()
                .userId(userId)
                .problemId(problemId)
                .answer(answer)
                .build();

        problemService.saveResult(userProblemDto);

        return new ResponseEntity(HttpStatus.OK);
    }
}
