package com.math.weakness.oauth.service;


import com.math.weakness.domain.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    void generateJwt() {
        String jwt = jwtService.generateJwt("안진우", "silrarion@naver.com",Role.STUDENT);
        System.out.println("jwt = " + jwt);
    }
    
    @Test
    void checkClaim() {
        Claims s = jwtService.parseJwt(jwtService.generateJwt("안진우","silrarion@naver.com", Role.STUDENT));
        System.out.println("s.get(\"name\") = " + s.get("name"));
    }

    @Test
    void tester() {
        Claims claims = jwtService.parseJwt(
                jwtService.generateJwt("안진우", "silrarion@naver.com", Role.ADMIN));
        Object role = claims.get("role").toString();
        System.out.println("role = " + role);
        System.out.println("role = " + Role.ADMIN);
        System.out.println(role.equals(Role.ADMIN));
        System.out.println(role.equals(Role.ADMIN.toString()));
    }

}