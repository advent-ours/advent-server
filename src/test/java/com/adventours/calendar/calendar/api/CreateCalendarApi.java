package com.adventours.calendar.calendar.api;

import com.adventours.calendar.calendar.domain.CalendarTemplate;
import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import com.adventours.calendar.common.Scenario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static com.adventours.calendar.common.ApiTest.accessToken;

public class CreateCalendarApi {

    private String title = "제목";
    private CalendarTemplate template = CalendarTemplate.RED;

    public CreateCalendarApi title(final String title) {
        this.title = title;
        return this;
    }

    public CreateCalendarApi template(final CalendarTemplate template) {
        this.template = template;
        return this;
    }

    public Scenario request() {

        final CreateCalendarRequest request = new CreateCalendarRequest(title, template);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .header("Authorization", accessToken)
                .post("/calendar")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        return new Scenario();
    }
}
