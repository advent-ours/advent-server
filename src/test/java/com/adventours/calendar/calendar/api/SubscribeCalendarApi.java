package com.adventours.calendar.calendar.api;

import io.restassured.RestAssured;

import java.util.UUID;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class SubscribeCalendarApi {

    private UUID calendarId;

    public SubscribeCalendarApi calendarId(UUID calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public void request() {
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .post("/calendar/sub/{calendarId}", calendarId)
                .then()
                .log().all()
                .statusCode(200);
    }
}
