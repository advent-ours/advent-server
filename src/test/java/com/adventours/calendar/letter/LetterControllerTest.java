package com.adventours.calendar.letter;

import com.adventours.calendar.auth.JwtTokenIssuer;
import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.letter.domain.LetterRepository;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.UUID;

class LetterControllerTest extends ApiTest {

    @Autowired
    LetterRepository letterRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenIssuer jwtTokenIssuer;

    @Test
    @DisplayName("편지 생성 성공")
    void createLetter() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request()
                .sendLetter().calendarId(calendar.getId()).request();
    }

    @Test
    @DisplayName("편지 조회 성공")
    void getLetter() {
        final User me = userRepository.getReferenceById(1L);
        final User user2 = Scenario.createUserDB().id(2L).create();
        String user2AccessToken = jwtTokenIssuer.issueToken(user2.getId()).accessToken();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(me).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request()
                .sendLetter().calendarId(calendar.getId()).accessToken(user2AccessToken).content("content1").request()
                .sendLetter().calendarId(calendar.getId()).accessToken(user2AccessToken).content("content2").request()
                .sendLetter().calendarId(calendar.getId()).accessToken(user2AccessToken).content("content3").request()
                .sendLetter().calendarId(calendar.getId()).accessToken(user2AccessToken).content("content4").request()
                .sendLetter().calendarId(calendar.getId()).accessToken(user2AccessToken).content("content5").request();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/{calendarId}/letter", calendar.getId())
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());

    }
}