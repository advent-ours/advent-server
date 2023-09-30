package com.adventours.calendar.user;

import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.user.client.KakaoOAuthFeignClient;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.persistence.UserRepository;
import com.adventours.calendar.user.service.KakaoUserInformation;
import com.adventours.calendar.user.service.LoginRequest;
import com.adventours.calendar.user.service.UpdateNicknameRequest;
import com.adventours.calendar.user.service.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest extends ApiTest {

    @MockBean
    KakaoOAuthFeignClient kakaoInformationFeignClient;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    //TODO: MockBean 초기화 현상 해결 필요.
    @Test
    @Disabled
    @DisplayName("카카오 회원가입(로그인) 성공")
    void login_kakao() {
        //given
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

        //when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/user/login/oauth/{provider}", provider.name())
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        //then
        assertThat(userRepository.findByProviderAndProviderId(provider, "providerId")).isPresent();
    }

    @Test
    @DisplayName("토큰 테스트")
    void test() {
        //when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .header("Authorization", accessToken)
                .get("/")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("닉네임 변경 성공")
    void updateNickname() {
        final Long userId = 1L;
        final UpdateNicknameRequest request;
        userService.updateNickname(userId, request);

        assertThat(userRepository.findById(userId).get().getNickname()).isEqualTo("업데이트닉네임");
    }
}
