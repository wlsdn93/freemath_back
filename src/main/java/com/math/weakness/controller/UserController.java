package com.math.weakness.controller;

import com.math.weakness.dto.Form;
import com.math.weakness.dto.PageResponse;
import com.math.weakness.service.ProblemService;
import com.math.weakness.service.UserService;
import java.util.List;
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
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Boolean status,
            @RequestParam String accessToken,
            @PageableDefault Pageable pageable) {
        return problemService.showAllProblemsForUser(
                accessToken, pageable, difficulty, subject, status
        );
    }

    @GetMapping("/list")
    public List<Long> getProblemIdList(
            @RequestParam(required = false) Integer difficulty,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Boolean status,
            @RequestParam String accessToken) {
        return problemService.getProblemIdList(accessToken, difficulty, subject, status);
    }



    @GetMapping("/{problemId}")
    public Form getProblemDetail(@PathVariable Long problemId) {
        return problemService.findById(problemId);
    }

    @PostMapping("/{problemId}")
    public ResponseEntity solveProblem(
            @PathVariable Long problemId,
            @RequestParam String answer,
            @RequestParam String accessToken) {
          boolean result = problemService.saveOrUpdateResult(problemId, answer, accessToken);
          return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
