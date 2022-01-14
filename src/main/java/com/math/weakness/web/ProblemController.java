package com.math.weakness.web;

import com.math.weakness.config.auth.dto.SessionUser;
import com.math.weakness.domain.Problem;
import com.math.weakness.dto.ProblemShow;
import com.math.weakness.dto.ProblemRequestDto;
import com.math.weakness.dto.ProblemResponseDto;
import com.math.weakness.dto.UserProblemDto;
import com.math.weakness.service.ProblemService;
import com.math.weakness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;
    private final UserService userService;

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public ProblemController(ProblemService problemService, UserService userService) {
        this.problemService = problemService;
        this.userService = userService;
    }

    @GetMapping
    public String problems(@RequestParam(required = false) Boolean status,
                           @RequestParam(required = false) Integer difficulty,
                           @PageableDefault Pageable pageable,
                           Model model) {
        Page<ProblemShow> problems = problemService.showAllProblemsByUser(pageable, difficulty, status);
        model.addAttribute("problems", problems);

        return "problems";
    }

    @GetMapping("/add")
    public String addProblem() {
        return "add-form";
    }

//    @GetMapping("/{problemId}")
//    public ResponseEntity<Resource> displayProblem(@PathVariable long problemId) {
//        ProblemResponseDto problemResponseDto = problemService.findById(problemId);
//        String problemFilePath = fileDir + problemResponseDto.getProblemImageName();
//        FileOutputStream fos = new FileOutputStream();
//        Resource resource = new FileSystemResource(problemFilePath);
//        HttpHeaders header = new HttpHeaders();
//        header.add("Content-Type", "image/png");
//        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
//    }

    @GetMapping("/{problemId}")
    public String showProblem(@PathVariable long problemId, Model model) {
    ProblemResponseDto problemResponseDto = problemService.findById(problemId);
    String problemFilePath = fileDir + problemResponseDto.getProblemImageName();
    model.addAttribute("problemFilePath",problemFilePath);
    model.addAttribute("problemId", problemId);
    return "problem";
    }

    @PostMapping("/{problemId}")
    public String solveProblem(@PathVariable Long problemId,
                               @RequestParam String answer,
                               RedirectAttributes redirectAttributes,
                               HttpSession httpSession) {
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