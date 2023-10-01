package com.adventours.calendar.calendar.api;

import com.adventours.calendar.common.Scenario;
import io.restassured.RestAssured;

import java.util.UUID;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class SubscribeCalendarApi {

    private UUID calendarId;

    public SubscribeCalendarApi calendarId(UUID calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .post("/calendar/sub/{calendarId}", calendarId)
                .then()
                .log().all()
                .statusCode(200);
        return new Scenario();
    }
}
