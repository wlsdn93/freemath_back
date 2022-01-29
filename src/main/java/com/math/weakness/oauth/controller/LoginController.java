package com.math.weakness.oauth.controller;


import com.math.weakness.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class LoginController {

    private final OAuthService oAuthService;

    @GetMapping("/state")
    private String returnState() {
        return oAuthService.getState();
    }

    @GetMapping
    private ResponseEntity<String> oAuthLogin(@RequestParam String code,
            @RequestParam String state) {

            String redirectUri = oAuthService.oAuthLogin(code, state);
            return new ResponseEntity(redirectUri, new HttpHeaders(), HttpStatus.OK);
    }


}
