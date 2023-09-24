package com.adventours.calendar.auth;

public record JwtTokenDto(String accessToken, String accessTokenExpiresIn) {
}
