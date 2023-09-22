package com.adventours.calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberControllerTest {

    @Test
    @DisplayName("카카오 로그인 성공")
    void login_kakao() {
        userService.login(provider, request);
    }
}
