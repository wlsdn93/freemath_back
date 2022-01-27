package com.math.weakness.oauth.controller;


import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
public class LoginController {

    @Value("${oauth2.client.naver.client-id}")
    private String naverClientId;
    @Value("${oauth2.client.naver.client-secret}")
    private String naverClientSecret;

    @Autowired
    private WebClient webClient;

    @GetMapping("/oauth")
    private ResponseEntity<String> oAuthLogin(@RequestParam String code, @RequestParam String state) {

        Map userInfo = getUserInfo(code, state);

        log.info("{}, {}", userInfo.get("email"), userInfo.get("name"));

        String redirectUri = "http://localhost:8081";
        return new ResponseEntity(redirectUri ,new HttpHeaders(), HttpStatus.OK);
    }

    private Map getUserInfo(String code, String state) {
        Map<String, String> tokenInfo = getTokenInfo(code, state);
        JSONObject response = webClient.mutate()
                .baseUrl("https://openapi.naver.com/v1/nid/me")
                .defaultHeader("Authorization", "Bearer " + tokenInfo.get("access_token"))
                .build()
                .get()
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        Map profile = (Map)response.get("response");
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", profile.get("email").toString());
        userInfo.put("name", profile.get("name").toString());
        return userInfo;
    }

    private Map<String, String> getTokenInfo(String code, String state) {
        JSONObject response = webClient.mutate()
                .baseUrl("https://nid.naver.com")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/oauth2.0/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", naverClientId)
                        .queryParam("client_secret", naverClientSecret)
                        .queryParam("code", code)
                        .queryParam("state", state)
                        .build())
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        Map<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("access_token", response.get("access_token").toString());
        tokenInfo.put("refresh_token", response.get("refresh_token").toString());
        tokenInfo.put("token_type", response.get("token_type").toString());
        tokenInfo.put("expires_in", response.get("expires_in").toString());

        return tokenInfo;
    }


}
