package com.adventours.calendar.letter;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.letter.domain.LetterRepository;
import com.adventours.calendar.user.domain.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class LetterControllerTest extends ApiTest {

    @Autowired
    LetterRepository letterRepository;

    @Test
    @DisplayName("편지 생성 성공")
    void createLetter() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request();

        SendLetterRequest request = new SendLetterRequest("content");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(request)
                .when()
                .post("/calendar/{calendarId}/letter", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);
    }
}