package com.math.weakness.oauth.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.math.weakness.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    void generateJwt() {
        String jwt = jwtService.generateJwt("안진우", Role.STUDENT);
        System.out.println("jwt = " + jwt);
    }
    
    @Test
    void checkClaim() {
        Claims s = jwtService.parseJwt(jwtService.generateJwt("안진우", Role.STUDENT));
        System.out.println("s.get(\"name\") = " + s.get("name"));
    }
}