package com.adventours.calendar.common;

import com.adventours.calendar.auth.JwtTokenIssuer;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApiTest {
    @LocalServerPort
    int port;
    @Autowired
    DatabaseCleaner databaseCleaner;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenIssuer jwtTokenIssuer;
    protected static String accessToken;

    @BeforeEach
    void setUp() {
        if (RestAssured.UNDEFINED_PORT == RestAssured.port) {
            RestAssured.port = port;
            databaseCleaner.afterPropertiesSet();
        }
        databaseCleaner.execute();
        userRepository.save(new User(OAuthProvider.KAKAO, "providerId", "profileImage"));
        accessToken = jwtTokenIssuer.issueToken(1L).accessToken();
    }
}