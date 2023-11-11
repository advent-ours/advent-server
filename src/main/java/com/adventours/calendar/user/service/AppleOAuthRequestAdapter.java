package com.adventours.calendar.user.service;

import com.adventours.calendar.exception.InvalidTokenException;
import com.adventours.calendar.user.client.AppleAuthKeyFeignClient;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppleOAuthRequestAdapter implements OAuthRequestPort {

    private static final OAuthProvider OAUTH_PROVIDER = OAuthProvider.APPLE;
    private final AppleAuthKeyFeignClient appleAuthKeyFeignClient;

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAUTH_PROVIDER;
    }

    @Override
    public OAuthUserInformation requestUserInformation(String token) {
        // jwt 복호화용 공개키 목록 가져오기
        String publicKeys = appleAuthKeyFeignClient.call();

        ObjectMapper objectMapper = new ObjectMapper();
        Keys keys;
        try {
            keys = objectMapper.readValue(publicKeys, Keys.class);

            SignedJWT signedJWT = SignedJWT.parse(token);

            boolean isVerified = isVerifiedToken(keys, signedJWT);

            if (isVerified) {
                JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

                return AppleUserInformation.builder()
                        .providerId(jwtClaimsSet.getSubject())
                        .build();
            } else {
                log.warn("AppleOAuthServiceImpl|requestUserInformation 서명 검증 실패 : {}", token);
                throw new InvalidTokenException();
            }

        } catch (JsonProcessingException | ParseException | JOSEException e) {
            log.error("AppleOAuthServiceImpl|requestUserInformation Exception: {}", token, e);
            throw new RuntimeException(e);
        }
    }

    private static boolean isVerifiedToken(Keys keys, SignedJWT signedJWT) throws ParseException, JsonProcessingException, JOSEException {
        boolean isVerified = false;
        ObjectMapper objectMapper = new ObjectMapper();

        for (Keys.Key key : keys.getKeyList()) {
            RSAKey rsaKey = (RSAKey) JWK.parse(objectMapper.writeValueAsString(key));
            RSAPublicKey publicKey = rsaKey.toRSAPublicKey();
            RSASSAVerifier verifier = new RSASSAVerifier(publicKey);
            if (signedJWT.verify(verifier)) {
                isVerified = true;
                break;
            }
        }
        return isVerified;
    }
}
