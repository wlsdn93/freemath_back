package com.math.weakness.web;

import com.math.weakness.security.dto.SessionUser;
import com.math.weakness.dto.PageResponse;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.dto.UserProblemDto;
import com.math.weakness.service.ProblemService;
import com.math.weakness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;
    private final UserService userService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping
    public PageResponse problems(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer difficulty,
            @PageableDefault Pageable pageable
            ) {
        return problemService.showAllProblems(
                pageable, difficulty, status
        );
    }

    @GetMapping("/add")
    public String addProblem() {
        return "add-form";
    }


    @GetMapping("/{problemId}")
    public String showProblem(@PathVariable long problemId, Model model) {
        ProblemResponseDto problemResponseDto = problemService.findById(problemId);
        String problemFilePath = fileDir + problemResponseDto.getProblemImageName();
        model.addAttribute("problemFilePath", problemFilePath);
        model.addAttribute("problemId", problemId);
        return "problem";
    }

    @PostMapping("/{problemId}")
    public String solveProblem(
            @PathVariable Long problemId,
            @RequestParam String answer,
            RedirectAttributes redirectAttributes,
            HttpSession httpSession
    ) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        Long userId = userService.findByEmail(user.getEmail());

        UserProblemDto userProblemDto = UserProblemDto.builder()
                .userId(userId)
                .problemId(problemId)
                .answer(answer)
                .build();

        problemService.saveResult(userProblemDto);
        redirectAttributes.addAttribute("problemId", problemId);

        return "redirect:/problems/{problemId}";
    }

    @PostMapping("/add")
    public String problems(
            @RequestParam MultipartFile problemImageFile,
            @RequestParam MultipartFile solutionImageFile,
            @RequestParam String title,
            @RequestParam String answer,
            @RequestParam String difficulty
    ) throws IOException {

        String problemOriginalName = problemImageFile.getOriginalFilename();
        String solutionOriginalNane = solutionImageFile.getOriginalFilename();
        String problemFilePath = fileDir + problemOriginalName;
        String solutionFilePath = fileDir + solutionOriginalNane;

        ProblemRequestDto requestDto = ProblemRequestDto.builder()
                .title(title)
                .difficulty(Integer.parseInt(difficulty))
                .answer(answer)
                .problemImageName(problemOriginalName)
                .solutionImageName(solutionOriginalNane)
                .build();

        problemService.addProblem(requestDto);

        problemImageFile.transferTo(new File(problemFilePath));
        solutionImageFile.transferTo(new File(solutionFilePath));

        return "redirect:/problems";
    }
}
