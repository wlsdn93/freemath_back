package com.math.weakness.oauth.service;

import com.math.weakness.domain.AuthenticationState;
import com.math.weakness.domain.Platform;
import com.math.weakness.domain.Role;
import com.math.weakness.domain.User;
import com.math.weakness.oauth.repository.AuthenticationStateRepository;
import com.math.weakness.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OAuthService {

    private final WebClient webClient;
    private final JwtService jwtService;
    private final AuthenticationStateRepository stateRepository;
    private final UserRepository userRepository;
    private final EmailEncryptionService emailEncryptionService;

    private JSONObject tokenResponse;
    private final String LOGIN_SUCCESS_REDIRECT_URL = "http://freemath.online/problems";
    private String ERROR_CODE;
    @Value("${oauth2.client.naver.client-id}")
    private String NAVER_CLIENT_ID;
    @Value("${oauth2.client.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${oauth2.client.naver.token-uri}")
    private String NAVER_TOKEN_API;
    @Value("${oauth2.client.naver.user-info-uri}")
    private String NAVER_USERINFO_API;

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
        log.info("getState method has called");
        stateRepository.save(new AuthenticationState(alphanumericState));
        return alphanumericState;
    }

    public Map<String, String> oAuthLogin(String code, String state) throws Exception {

        stateRepository.deleteByValidTimeLessThan(LocalDateTime.now());
        log.info("delete expired authenticationState has called");
        AuthenticationState authenticationState = stateRepository.findByState(state);
        log.info("state valid process has called");
        if (authenticationState == null) {
            Map<String, String> response = new HashMap<>();
            response.put("redirectUri", LOGIN_SUCCESS_REDIRECT_URL);
            response.put("errorCode", ERROR_CODE);
        }
        Map<String, String> userInfo = getUserInfo(code);
        String jwt = getJwt(userInfo);
        Map<String, String> response = new HashMap<>();
        response.put("jwt", jwt);
        response.put("redirectUri", LOGIN_SUCCESS_REDIRECT_URL);

        // return JWT
        log.info("{}", userInfo);
        return response;
    }

    private String getJwt(Map<String, String> userInfo) throws Exception {
        User user = this.saveOrUpdate(userInfo);
        return jwtService.generateJwt(user.getName(), user.getEmail(), user.getRole());
    }

    private User saveOrUpdate(Map<String, String> userInfo) throws Exception {
        String email = userInfo.get("email");
        String encryptedEmail = emailEncryptionService.encryptSHA256(email);
        try {
            return userRepository.findByEmail(encryptedEmail)
                    .orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            return userRepository.save(User.builder()
                    .name(userInfo.get("name"))
                    .email(encryptedEmail)
                    .role(Role.STUDENT)
                    .platform(Platform.NAVER)
                    .build());
        }
    }

    private Map<String, String> getUserInfo(String code) {
        Map<String, String> tokenInfo = this.getTokenInfo(code);
        JSONObject userInfoResponse = webClient.mutate()
                .baseUrl(NAVER_USERINFO_API)
                .defaultHeader("Authorization", "Bearer " + tokenInfo.get("access_token"))
                .build()
                .get()
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        Map<String, String> profile = (Map)userInfoResponse.get("response");
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", profile.get("email").toString());
        userInfo.put("name", profile.get("name").toString());
        return userInfo;
    }

    private Map<String, String> getTokenInfo(String code){

        tokenResponse = webClient.mutate()
                .baseUrl(NAVER_TOKEN_API)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", NAVER_CLIENT_ID)
                        .queryParam("client_secret", NAVER_CLIENT_SECRET)
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
