package com.adventours.calendar.exception;

import com.adventours.calendar.HelloController;
import com.adventours.calendar.common.ApiTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

class ExceptionTest extends ApiTest {

    @Test
    void ok() {
        HelloController.ExceptionTestBody request = new HelloController.ExceptionTestBody(1);

        RestAssured.given().log().all()
                .when()
                .body(request)
                .contentType("application/json")
                .post("/exception/1")
                .then().log().all()
                .statusCode(200);
    }
}
