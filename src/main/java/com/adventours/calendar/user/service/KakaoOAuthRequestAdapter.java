package com.adventours.calendar.user.service;

import com.adventours.calendar.user.client.KakaoOAuthFeignClient;
import com.adventours.calendar.user.domain.OAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoOAuthRequestAdapter implements OAuthRequestPort {
    private final KakaoOAuthFeignClient kakaoOAuthFeignClient;
    private static final OAuthProvider PROVIDER = OAuthProvider.KAKAO;
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public OAuthUserInformation requestUserInformation(final String token) {
        return kakaoOAuthFeignClient.call(
                "application/x-www-form-urlencoded;charset=utf-8",
                TOKEN_PREFIX + token
        );
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return PROVIDER;
    }
}
