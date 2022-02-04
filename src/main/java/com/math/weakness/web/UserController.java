package com.math.weakness.web;

import com.math.weakness.dto.PageResponse;
import com.math.weakness.dto.ProblemDetail;
import com.math.weakness.service.ProblemService;
import com.math.weakness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/problems")
public class UserController {

    private final ProblemService problemService;
    private final UserService userService;

    @GetMapping
    public PageResponse getProblems(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) String accessToken,
            @PageableDefault Pageable pageable) {
        return problemService.showAllProblemsForUser(
                accessToken, pageable, difficulty, status
        );
    }

    @GetMapping("/{problemId}")
    public ProblemDetail getProblemDetail(@PathVariable long problemId) {
        return problemService.findById(problemId);
    }

    @PostMapping("/{problemId}")
    public ResponseEntity solveProblem(
            @PathVariable Long problemId,
            @RequestParam String answer,
            @RequestParam String accessToken) {
          boolean result = problemService.saveResult(problemId, answer, accessToken);
          return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
