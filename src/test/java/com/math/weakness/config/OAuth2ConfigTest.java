package com.math.weakness.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class OAuth2ConfigTest {

    @Autowired
    private Environment environment;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Test
    public void test() {


//        String naverClientId = environment.getProperty("spring.security.oauth2.client.registration.naver.client-id");
        System.out.println("naverClientId = " + naverClientId);
    }

}