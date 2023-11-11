package com.adventours.calendar.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "AppleAuthKeyFeignClient", url = "${oauth.apple.auth-uri}")
public interface AppleAuthKeyFeignClient {

    @GetMapping
    String call();
}
