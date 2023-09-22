package com.adventours.calendar.user;

import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.user.client.KakaoOAuthFeignClient;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.persistence.UserRepository;
import com.adventours.calendar.user.service.KakaoUserInformation;
import com.adventours.calendar.user.service.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class UserControllerTest extends ApiTest {

    @MockBean
    KakaoOAuthFeignClient kakaoInformationFeignClient;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("카카오 로그인 성공")
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

}
