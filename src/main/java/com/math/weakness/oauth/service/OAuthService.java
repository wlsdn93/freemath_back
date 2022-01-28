package com.math.weakness.oauth.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class OAuthService {

    @Autowired
    private WebClient webClient;

    @Value("${oauth2.client.naver.client-id}")
    private String naverClientId;
    @Value("${oauth2.client.naver.client-secret}")
    private String naverClientSecret;
    @Value("${oauth2.client.kakao.client-id}")
    private String kakaoClientId;
    @Value("${oauth2.client.kakao.client-secret}")
    private String kakaoClientSecret;


    public String getState() {

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String alphanumericState  = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return alphanumericState;
    }

    public String oAuthLogin(String code, String state, String social) {

        if (state.isEmpty()) {
            return "http://localhost:8081/login?error=request_not_found";
        }
        String clientId, clientSecret;
        // state check, social check here
        if (social.equals("naver")) {
            clientId = naverClientId;
            clientSecret = naverClientSecret;
        }
        else {
            clientId = kakaoClientId;
            clientSecret = kakaoClientSecret;
        }

        Map userInfo = getUserInfo(code, clientId, clientSecret);
        log.info("{}", userInfo.toString());
        return "http://localhost:8081";
    }

    public Map getUserInfo(String code, String clientId, String clientSecret) {
        Map<String, String> tokenInfo = getTokenInfo(code, clientId, clientSecret);
        JSONObject userInfoResponse = webClient.mutate()
                .baseUrl("https://openapi.naver.com/v1/nid/me")
                .defaultHeader("Authorization", "Bearer " + tokenInfo.get("access_token"))
                .build()
                .get()
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        Map profile = (Map)userInfoResponse.get("response");
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", profile.get("email").toString());
        userInfo.put("name", profile.get("name").toString());
        return userInfo;
    }

    public Map<String, String> getTokenInfo(String code, String clientId, String clientSecret){
        JSONObject tokenResponse = webClient.mutate()
                .baseUrl("https://nid.naver.com")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/oauth2.0/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        Map<String, String> tokenInfo = new HashMap<>();
        tokenInfo.put("access_token", tokenResponse.get("access_token").toString());
        tokenInfo.put("refresh_token", tokenResponse.get("refresh_token").toString());
        tokenInfo.put("token_type", tokenResponse.get("token_type").toString());
        tokenInfo.put("expires_in", tokenResponse.get("expires_in").toString());

        return tokenInfo;
    }


}
