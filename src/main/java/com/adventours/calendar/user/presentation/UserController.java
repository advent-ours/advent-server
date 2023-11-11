package com.adventours.calendar.user.presentation;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.JwtTokenDto;
import com.adventours.calendar.auth.JwtTokenIssuer;
import com.adventours.calendar.auth.UserContext;
import com.adventours.calendar.global.CommonResponse;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.service.LoginRequest;
import com.adventours.calendar.user.service.LoginResponse;
import com.adventours.calendar.user.service.UpdateNicknameRequest;
import com.adventours.calendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenIssuer tokenIssuer;

    @PostMapping("/login/oauth/{provider}")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@PathVariable final String provider, @RequestBody final LoginRequest request) {
        System.out.println("request = " + request.token());
        final LoginResponse response = userService.login(OAuthProvider.valueOf(provider), request);
        final JwtTokenDto jwtTokenDto = tokenIssuer.issueToken(response.getUserId());
        response.issueToken(jwtTokenDto);
        return ResponseEntity.ok(new CommonResponse<>(response));
    }

    @Auth
    @PutMapping("/nickname")
    public ResponseEntity<CommonResponse<Void>> updateNickname(@RequestBody final UpdateNicknameRequest request) {
        final Long userId = UserContext.getContext();
        userService.updateNickname(userId, request);
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @Auth
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout() {
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @Auth
    @DeleteMapping("/withdraw")
    public ResponseEntity<CommonResponse<Void>> withdraw() {
        final Long userId = UserContext.getContext();
        userService.withdraw(userId);
        return ResponseEntity.ok(new CommonResponse<>());
    }
}
