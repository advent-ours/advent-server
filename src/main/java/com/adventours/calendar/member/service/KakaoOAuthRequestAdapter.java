package com.adventours.calendar.member.service;

import com.adventours.calendar.member.domain.OAuthProvider;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthRequestAdapter implements OAuthRequestPort {
    private final OAuthProvider PROVIDER = OAuthProvider.KAKAO;
    @Override
    public OAuthUserInformation requestUserInformation(final String token) {
        throw new UnsupportedOperationException("KakaoOAuthRequestAdapter#requestUserInformation not implemented yet.");
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return PROVIDER;
    }
}
