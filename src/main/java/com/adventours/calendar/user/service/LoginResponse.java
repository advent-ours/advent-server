package com.adventours.calendar.user.service;

import com.adventours.calendar.auth.JwtTokenDto;
import lombok.Getter;

@Getter
public final class LoginResponse {
    private final long userId;
    private final String nickname;
    private final boolean isNewUser;
    private String accessToken;
    private String accessTokenExpiresIn;

    public LoginResponse(long userId, String nickname, boolean isNewUser) {
        this.userId = userId;
        this.nickname = nickname;
        this.isNewUser = isNewUser;
    }

    public void issueToken(final JwtTokenDto jwtTokenDto) {
        accessToken = jwtTokenDto.accessToken();
        accessTokenExpiresIn = jwtTokenDto.accessTokenExpiresIn();
    }
}
