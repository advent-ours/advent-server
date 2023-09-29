package com.adventours.calendar.api;

import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class CreateCalendarApi {

    private String title = "제목";

    public CreateCalendarApi title(final String title) {
        this.title = title;
        return this;
    }

    public void request() {

        final CreateCalendarRequest request = new CreateCalendarRequest(title);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .header("Authorization", accessToken)
                .post("/calendar")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
