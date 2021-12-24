package com.math.weakness.web;

import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @GetMapping
    public String problems(@PageableDefault Pageable pageable, Model model) {
        Page<ProblemResponseDto> problems = problemService.findAllProblem(pageable);
        model.addAttribute("problems", problems);
        return "problems";
    }

    @GetMapping("/add")
    public String addProblem() {
        return "add-form";
    }


    @GetMapping("/{problemId}")
    public String problem(@PathVariable long problemId, Model model) {
        ProblemResponseDto problemResponseDto = problemService.findById(problemId);
        String problemFilePath = fileDir + problemResponseDto.getProblemImageName();
        model.addAttribute("problemFilePath",problemFilePath);
        return "problem";
    }

    @PostMapping("/add")
    public String problems(@RequestParam MultipartFile problemImageFile,
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