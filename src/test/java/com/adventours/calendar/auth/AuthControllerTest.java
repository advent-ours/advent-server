package com.adventours.calendar.auth;

import com.adventours.calendar.common.ApiTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class AuthControllerTest extends ApiTest {

    @Test
    void version() {
        String os = "IOS";
        String version = "1.0.0";
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .queryParam("os", os)
                .queryParam("appVersion", version)
                .get("/version")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.updateRequired", Matchers.is(UpdateRequiredState.LATEST.toString()));
    }
}