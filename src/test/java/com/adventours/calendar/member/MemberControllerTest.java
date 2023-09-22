package com.adventours.calendar.member;

import com.adventours.calendar.member.domain.OAuthProvider;
import com.adventours.calendar.member.service.LoginRequest;
import com.adventours.calendar.member.service.LoginResponse;
import com.adventours.calendar.member.service.MemberService;
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
        final LoginResponse response = memberService.login(provider, request);
        assertThat(response.isNewMember()).isTrue();
    }

}
