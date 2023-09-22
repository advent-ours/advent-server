package com.adventours.calendar.member.service;

public class KakaoOAuthRequestAdapter implements OAuthRequestPort {
    @Override
    public OAuthUserInformation requestUserInformation(final String token) {
        throw new UnsupportedOperationException("KakaoOAuthRequestAdapter#requestUserInformation not implemented yet.")
    }
}
