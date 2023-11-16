package com.adventours.calendar.auth;

import com.adventours.calendar.common.ApiTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class AuthControllerTest extends ApiTest {

//    client-version:
//        ios:
//            required: 0.5.0
//            latest: 1.0.0
//        aos:
//            required: 0.5.0
//            latest: 1.0.0

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

    @Test
    void version_force() {
        String os = "IOS";
        String version = "0.4.0";
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .queryParam("os", os)
                .queryParam("appVersion", version)
                .get("/version")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.updateRequired", Matchers.is(UpdateRequiredState.FORCE.toString()));
    }

    @Test
    void version_recommended() {
        String os = "IOS";
        String version = "0.9.0";
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .queryParam("os", os)
                .queryParam("appVersion", version)
                .get("/version")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.updateRequired", Matchers.is(UpdateRequiredState.RECOMMENDED.toString()));
    }
}