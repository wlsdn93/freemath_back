package com.math.weakness.web;

import com.math.weakness.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ProblemService problemService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/kakaoBtnImage")
    public ResponseEntity<Resource> kakaoBtn() {
        String problemFilePath = fileDir + "kakao_btn.png";
        Resource resource = new FileSystemResource(problemFilePath);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/png");
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    @GetMapping("/naverBtnImage")
    public ResponseEntity<Resource> naverBtn() {
        String problemFilePath = fileDir + "naver_btn.png";
        Resource resource = new FileSystemResource(problemFilePath);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/png");
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }


    @GetMapping("/problem-image/{problemId}")
    public ResponseEntity<Resource> showProblemImage(@PathVariable long problemId) {
        Resource resource = problemService.getProblemImage(problemId);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/jpeg");
        return new ResponseEntity<>(resource ,header ,HttpStatus.OK);
    }

    @GetMapping("/solution-image/{problemId}")
    public ResponseEntity<Resource> showSolutionImage(@PathVariable long problemId) {
        Resource resource = problemService.getSolutionImage(problemId);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/jpeg");
        return new ResponseEntity<>(resource ,header ,HttpStatus.OK);
    }

}
