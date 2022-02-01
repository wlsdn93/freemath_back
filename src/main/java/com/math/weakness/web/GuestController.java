package com.math.weakness.web;

import com.math.weakness.dto.PageResponse;
import com.math.weakness.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {

    private final ProblemService problemService;

    @GetMapping("/problems")
    public PageResponse problems(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer difficulty,
            @PageableDefault Pageable pageable
    ) {
        return problemService.showAllProblemsForGuest(
                pageable, difficulty, status
        );
    }
}
