package com.math.weakness.oauth.service;

import com.math.weakness.domain.AuthenticationState;
import com.math.weakness.oauth.repository.AuthenticationStateRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
class OAuthServiceTest {

//    @Autowired
//    AuthenticationStateRepository repository;
//
//    @Value("${oauth2.client.naver.client-id}")
//    private String naverClientId;
//    @Value("${oauth2.client.naver.client-secret}")
//    private String naverClientSecret;
//
//    @Test
//    void propertyTest() {
//        System.out.println(naverClientId);
//        System.out.println(naverClientSecret);
//    }
//
//    @Test
//    @Rollback(value = false)
//    void saveTest() {
//        repository.save(new AuthenticationState("123123"));
//        AuthenticationState foundState = repository.findByState("123123");
//        AuthenticationState notExistState = repository.findByState("123143");
//        System.out.println(foundState.getState());
//        System.out.println(notExistState);
//    }
//
//    @Test
//    @Rollback(value = false)
//    void deleteTest() {
//        repository.deleteByValidTimeLessThan(LocalDateTime.now());
//        System.out.println(repository.findAll().size());
//    }
}