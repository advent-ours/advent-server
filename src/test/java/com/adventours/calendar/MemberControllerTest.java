package com.adventours.calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberControllerTest {

    private MemberService memberService;

    @Test
    @DisplayName("카카오 로그인 성공")
    void login_kakao() {
        final OAuthProvider provider = OAuthProvider.KAKAO;
        final String token = "loginToken";
        final LoginRequest request = new LoginRequest(token);
        boolean isNewMember = memberService.login(provider, request);
        assertThat(isNewMember).isTrue();
    }

    private class MemberService {
        public boolean login(final OAuthProvider provider, final LoginRequest request) {
            throw new UnsupportedOperationException("MemberService#login not implemented yet.");
        }
    }

    private enum OAuthProvider {
        KAKAO
    }

    private record LoginRequest(String token) {
    }
}
