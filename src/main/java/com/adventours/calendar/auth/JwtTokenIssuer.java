package com.adventours.calendar.auth;

import com.adventours.calendar.exception.ExpiredTokenException;
import com.adventours.calendar.exception.InvalidTokenException;
import com.adventours.calendar.exception.NotFoundUserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Transactional
@Service
public class JwtTokenIssuer {

    @Value("${jwt.access-token.secret}")
    private String secretKey;
    @Value("${jwt.access-token.expire-length}")
    private long accessTokenExpireLength;
    public static final String TOKEN_TYPE = "Bearer ";

    public JwtTokenDto issueToken(Long userId) {
        if (Objects.isNull(userId)) {
            throw new NotFoundUserException();
        }
        JwtTokenVo accessToken = generateToken(userId, accessTokenExpireLength);

        return new JwtTokenDto(
                TOKEN_TYPE + accessToken.accessToken(),
                accessToken.expiresIn());
    }

    public Long validateJwt(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Long.valueOf((String) claims.get("userId"));
        } catch (ExpiredJwtException e) {
            log.info("JwtTokenServiceImpl|Token expired: {}", token, e);
            throw new ExpiredTokenException();
        } catch (Exception e) {
            log.warn("JwtTokenServiceImpl|Token Exception: {}", token, e);
            throw new InvalidTokenException();
        }
    }

    public JwtTokenVo generateToken(Long userId, Long expireLength) {
        // header 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS512");

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // payload 설정
        Map<String, String> payloads = new HashMap<>();
        payloads.put("userId", String.valueOf(userId));

        Date ext = new Date();
        ext.setTime(ext.getTime() + expireLength);

        String jwt = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setExpiration(ext)
                .signWith(key)
                .compact();

        return new JwtTokenVo(jwt, String.valueOf(expireLength));
    }

    public static String parseOnlyTokenFromRequest(String token) {
        if (!token.startsWith(TOKEN_TYPE)) {
            throw new InvalidTokenException();
        }
        return token.substring(TOKEN_TYPE.length()).trim();
    }

    private static String createTokenStringForResponse(JwtTokenVo accessToken) {
        return TOKEN_TYPE + accessToken.accessToken();
    }

}