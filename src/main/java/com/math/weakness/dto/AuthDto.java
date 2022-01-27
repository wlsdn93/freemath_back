package com.math.weakness.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthDto {
    private final String accessToken;
    private final String refreshToken;
    private final String tokenType;
    private final String expiresIn;

    @Builder
    public AuthDto(String accessToken, String refreshToken, String tokenType,
            String expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

}
