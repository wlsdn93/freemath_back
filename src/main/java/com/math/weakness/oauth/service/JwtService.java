package com.math.weakness.oauth.service;

import com.math.weakness.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwt(String name, String email, Role role) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(90).toMillis()))
                .claim("name", name)
                .claim("email", email)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

}
