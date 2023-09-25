package com.adventours.calendar.user.service;

import com.adventours.calendar.auth.JwtTokenDto;
import lombok.Getter;

@Getter
public final class LoginResponse {
    private final long userId;
    private final boolean isNewUser;
    private String accessToken;
    private String accessTokenExpiresIn;

    public LoginResponse(long userId, boolean isNewUser) {
        this.userId = userId;
        this.isNewUser = isNewUser;
    }

    public void issueToken(final JwtTokenDto jwtTokenDto) {
        accessToken = jwtTokenDto.accessToken();
        accessTokenExpiresIn = jwtTokenDto.accessTokenExpiresIn();
    }
}
