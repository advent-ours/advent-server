package com.adventours.calendar.user.presentation;

import com.adventours.calendar.auth.JwtTokenDto;
import com.adventours.calendar.auth.JwtTokenIssuer;
import com.adventours.calendar.global.CommonResponse;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.service.LoginRequest;
import com.adventours.calendar.user.service.LoginResponse;
import com.adventours.calendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenIssuer tokenIssuer;

    @PostMapping("/login/oauth/{provider}")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@PathVariable final String provider, final LoginRequest request) {
        final LoginResponse response = userService.login(OAuthProvider.valueOf(provider), request);
        final JwtTokenDto jwtTokenDto = tokenIssuer.issueToken(response.getUserId());
        response.issueToken(jwtTokenDto);
        return ResponseEntity.ok(new CommonResponse<>(response));
    }
}
