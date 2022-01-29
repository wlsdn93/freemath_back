package com.math.weakness.oauth.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
public class OAuth2Properties {

    @Value("${oauth2.client.naver.client-id}")
    private String NAVER_CLIENT_ID;
    @Value("${oauth2.client.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${oauth2.client.naver.token-uri}")
    private String NAVER_TOKEN_API;
    @Value("${oauth2.client.naver.user-info-uri}")
    private String NAVER_USERINFO_API;

    @Value("${oauth2.client.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${oauth2.client.kakao.client-secret}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${oauth2.client.kakao.token-uri}")
    private String KAKAO_TOKEN_API;
    @Value("${oauth2.client.kakao.user-info-uri}")
    private String KAKAO_USERINFO_API;

    private String providerName;
    private String clientId;
    private String clientSecret;
    private String tokenRequestApi;
    private String userInfoRequestApi;

    @Builder
    public OAuth2Properties(String providerName, String clientId, String clientSecret, String tokenRequestApi,
            String userInfoRequestApi) {
        this.providerName = providerName;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenRequestApi = tokenRequestApi;
        this.userInfoRequestApi = userInfoRequestApi;
    }

    public OAuth2Properties of(String social) {
        if (social.equals("naver")) {
            return OAuth2Properties.builder()
                    .providerName("naver")
                    .clientId(NAVER_CLIENT_ID)
                    .clientSecret(NAVER_CLIENT_SECRET)
                    .tokenRequestApi(NAVER_TOKEN_API)
                    .userInfoRequestApi(NAVER_USERINFO_API)
                    .build();
        }
        return OAuth2Properties.builder()
                .providerName("kakao")
                .clientId(KAKAO_CLIENT_ID)
                .clientSecret(KAKAO_CLIENT_SECRET)
                .tokenRequestApi(KAKAO_TOKEN_API)
                .userInfoRequestApi(KAKAO_USERINFO_API)
                .build();
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getTokenRequestApi() {
        return tokenRequestApi;
    }

    public String getUserInfoRequestApi() {
        return userInfoRequestApi;
    }

    public String getProviderName() {
        return providerName;
    }
}
