package com.adventours.calendar.user;

import com.adventours.calendar.user.client.KakaoOAuthFeignClient;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.service.KakaoUserInformation;
import com.adventours.calendar.user.service.LoginRequest;
import com.adventours.calendar.user.service.LoginResponse;
import com.adventours.calendar.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;

    @MockBean
    KakaoOAuthFeignClient kakaoInformationFeignClient;

    @Test
    @DisplayName("카카오 로그인 성공")
    void login_kakao() {
        final OAuthProvider provider = OAuthProvider.KAKAO;
        final String token = "loginToken";
        final LoginRequest request = new LoginRequest(token);
        when(kakaoInformationFeignClient.call(any(), any())).thenReturn(new KakaoUserInformation(
                "providerId",
                "connectedAt",
                "nickname",
                "profileImage",
                "thumbnailImage",
                "kakaoAccount"
        ));

        final LoginResponse response = userService.login(provider, request);
        assertThat(response.isNewUser()).isTrue();
    }

}
