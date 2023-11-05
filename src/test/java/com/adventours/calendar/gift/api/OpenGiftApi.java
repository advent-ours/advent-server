package com.adventours.calendar.gift.api;

import com.adventours.calendar.common.Scenario;
import io.restassured.RestAssured;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class OpenGiftApi {
    private Long giftId = 1L;
    private String calendarId = null;

    public OpenGiftApi giftId(final Long giftId) {
        this.giftId = giftId;
        return this;
    }

    public OpenGiftApi calendarId(final String calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    public Scenario request() {
        assert calendarId != null;
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/{calendarId}/gift/{giftId}", calendarId, giftId)
                .then()
                .log().all()
                .statusCode(200);
        return new Scenario();
    }
}
