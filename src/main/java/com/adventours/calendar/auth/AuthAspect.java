package com.adventours.calendar.auth;

import com.adventours.calendar.exception.TokenNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class AuthAspect {
    private final JwtTokenIssuer jwtTokenIssuer;
    private final HttpServletRequest httpServletRequest;
    public static final String AUTHORIZATION_HEADER = "Authorization";


    @Around("@annotation(com.adventours.calendar.auth.Auth)")
    public Object validateAccessToken(final ProceedingJoinPoint joinPoint) throws Throwable {
        String accessToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        if (!StringUtils.hasText(accessToken)) {
            throw new TokenNotFoundException();
        }

        accessToken = JwtTokenIssuer.parseOnlyTokenFromRequest(accessToken);
        Long userId = jwtTokenIssuer.validateJwt(accessToken);

        UserContext.CONTEXT.set(userId);

        return joinPoint.proceed();
    }

}
