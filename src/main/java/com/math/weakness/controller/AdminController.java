package com.math.weakness.controller;

import com.math.weakness.dto.Form;
import com.math.weakness.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProblemService problemService;

    @PostMapping("/upload")
    public void uploadProblem(@ModelAttribute Form formData) {
        problemService.addProblem(formData);
    }

    @PostMapping("/update/{problemId}")
    public void updateProblem(@ModelAttribute Form formData, @PathVariable Long problemId) {
        problemService.updateProblem(formData, problemId);
    }

    @DeleteMapping("/delete/{problemId}")
    public void deleteProblem(@PathVariable long problemId) {
        problemService.deleteProblem(problemId);
    }


}
