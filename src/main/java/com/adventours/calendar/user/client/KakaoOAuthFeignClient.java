package com.adventours.calendar.user.client;

import com.adventours.calendar.user.service.KakaoUserInformation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoInformationFeignClient", url = "${oauth.kakao.auth_url}")
public interface KakaoOAuthFeignClient {

    @GetMapping
    KakaoUserInformation call(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Authorization") String accessToken
    );
}
