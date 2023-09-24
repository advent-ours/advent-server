package com.adventours.calendar.auth;

public record JwtTokenVo(String accessToken, String expiresIn) {
}
