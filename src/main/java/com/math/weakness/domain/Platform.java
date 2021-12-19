package com.math.weakness.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Platform {

    NAVER("NAVER", "네이버"),
    KAKAO("KAKAO", "카카오"),
    FACEBOOK("FACEBOOK", "페이스북");

    private final String key;
    private final String title;
}
