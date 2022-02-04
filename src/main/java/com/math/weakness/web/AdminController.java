package com.math.weakness.web;

import com.math.weakness.dto.Form;
import com.math.weakness.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public String uploadProblem(@ModelAttribute Form formData) {
        problemService.addProblem(formData);

        return "/";
    }

    @DeleteMapping("/delete/{problemId}")
    public String deleteProblem(@PathVariable long problemId) {
        problemService.deleteProblem(problemId);
        return "/";
    }


}
