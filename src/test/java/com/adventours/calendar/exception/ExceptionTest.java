package com.adventours.calendar.exception;

import com.adventours.calendar.HelloController;
import com.adventours.calendar.common.ApiTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

class ExceptionTest extends ApiTest {

    @Test
    void ok_get() {
        RestAssured.given().log().all()
                .when()
                .contentType("application/json")
                .param("query", "query")
                .get("/exception")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void ok_post() {
        HelloController.ExceptionTestBody request = new HelloController.ExceptionTestBody(1);

        RestAssured.given().log().all()
                .when()
                .body(request)
                .contentType("application/json")
                .post("/exception/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void path_param() {
        HelloController.ExceptionTestBody request = new HelloController.ExceptionTestBody(1);

        RestAssured.given().log().all()
                .when()
                .body(request)
                .contentType("application/json")
                .post("/exception/error")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void query_param() {
        RestAssured.given().log().all()
                .when()
                .contentType("application/json")
                .get("/exception")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void not_allowd_method() {
        RestAssured.given().log().all()
                .when()
                .contentType("application/json")
                .delete("/exception")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void body_validate_noBody() {
        RestAssured.given().log().all()
                .when()
                .contentType("application/json")
                .post("/exception/1")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void body_validate_not_valid_body() {
        HelloController.ExceptionTestBody request = new HelloController.ExceptionTestBody(null);

        RestAssured.given().log().all()
                .when()
                .contentType("application/json")
                .body(request)
                .post("/exception/1")
                .then().log().all()
                .statusCode(400);
    }
}
