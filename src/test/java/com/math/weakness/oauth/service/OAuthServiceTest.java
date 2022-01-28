package com.math.weakness.oauth.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OAuthServiceTest {

    @Value("${oauth2.client.naver.client-id}")
    private String naverClientId;
    @Value("${oauth2.client.naver.client-secret}")
    private String naverClientSecret;

    @Test
    void propertyTest() {
        System.out.println(naverClientId);
        System.out.println(naverClientSecret);
    }
}