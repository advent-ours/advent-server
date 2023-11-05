package com.adventours.calendar.gift.api;

import com.adventours.calendar.common.Scenario;
import io.restassured.RestAssured;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class ReactGiftApi {

    private int giftId = 1;

    public ReactGiftApi giftId(final int giftId) {
        this.giftId = giftId;
        return this;
    }

    public Scenario request() {
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .post("/calendar/{calendarId}/gift/{giftId}/react", "dummy", giftId)
                .then()
                .log().all()
                .statusCode(200);
        return new Scenario();
    }
}
