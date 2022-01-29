package com.math.weakness.oauth.service;

import com.math.weakness.domain.AuthenticationState;
import com.math.weakness.oauth.dto.OAuth2Properties;
import com.math.weakness.oauth.repository.AuthenticationStateRepository;
import com.math.weakness.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    private final AuthenticationStateRepository stateRepository;
    private final UserRepository userRepository;
    private OAuth2Properties oAuth2Properties;
    private JSONObject tokenResponse;

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

    public String oAuthLogin(String code, String state, String social) {

        stateRepository.deleteByValidTimeLessThan(LocalDateTime.now());
        log.info("delete expired authenticationState has called");
        AuthenticationState authenticationState = stateRepository.findByState(state);
        log.info("state valid process has called");
        if (authenticationState == null) {
            return "http://localhost:8081/login?error=request_not_found";
        }

        OAuth2Properties oAuth2properties = oAuth2Properties.of(social);

        Map<String, String> userInfo = getUserInfo(code, oAuth2properties);
        String email = userInfo.get("email");

        log.info("{}", userInfo);
        return "http://localhost:8081";
    }

    public Map getUserInfo(String code, OAuth2Properties oAuth2Properties) {
        Map<String, String> tokenInfo = getTokenInfo(code, oAuth2Properties);
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
    public Map<String, String> getTokenInfo(String code, OAuth2Properties oAuth2Properties){

        if (oAuth2Properties.getProviderName().equals("naver")) {
            tokenResponse = webClient.mutate()
                    .baseUrl(oAuth2Properties.getTokenRequestApi())
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("/")
                            .queryParam("grant_type", "authorization_code")
                            .queryParam("client_id", oAuth2Properties.getClientId())
                            .queryParam("client_secret", oAuth2Properties.getClientSecret())
                            .queryParam("code", code)
                            .build())
                    .retrieve()
                    .bodyToMono(JSONObject.class)
                    .block();
        }
        tokenResponse = webClient.mutate()
                .baseUrl(oAuth2Properties.getTokenRequestApi())
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder.path("/")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", oAuth2Properties.getClientId())
                        .queryParam("client_secret", oAuth2Properties.getClientSecret())
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
