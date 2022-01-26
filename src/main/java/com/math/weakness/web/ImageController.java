package com.math.weakness.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.boot.web.servlet.server.Session.Cookie;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ImageController {

    @Value("${file.dir}")
    private String fileDir;



    @CrossOrigin
    @GetMapping("/kakaoBtnImage")
    public ResponseEntity<Resource> kakaoBtn() {
        String problemFilePath = fileDir + "kakao_btn.png";
        Resource resource = new FileSystemResource(problemFilePath);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/png");
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/naverBtnImage")
    public ResponseEntity<Resource> naverBtn() {
        String problemFilePath = fileDir + "naver_btn.png";
        Resource resource = new FileSystemResource(problemFilePath);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/png");
        return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
    }


}
