package com.adventours.calendar.calendar.api;

import com.adventours.calendar.common.Scenario;
import io.restassured.RestAssured;

import java.util.UUID;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class SubscribeCalendarApi {

    private String customAccessToken = accessToken;
    private UUID calendarId;

    public SubscribeCalendarApi customAccessToken(String customAccessToken) {
        this.customAccessToken = customAccessToken;
        return this;
    }

    public SubscribeCalendarApi calendarId(UUID calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public Scenario request() {
        assert calendarId != null;
        RestAssured.given().log().all()
                .header("Authorization", customAccessToken)
                .when()
                .post("/calendar/sub/{calendarId}", calendarId)
                .then()
                .log().all()
                .statusCode(200);
        return new Scenario();
    }
}
