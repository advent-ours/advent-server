package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.gift.persistence.GiftRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarControllerTest extends ApiTest {

    @Autowired CalendarRepository calendarRepository;
    @Autowired GiftRepository giftRepository;

    @Test
    void createCalendar() {
        final CreateCalendarRequest request = new CreateCalendarRequest("제목");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .header("Authorization", accessToken)
                .post("/calendar")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        Assertions.assertAll(
            () -> assertThat(calendarRepository.count()).isEqualTo(1),
            () -> assertThat(giftRepository.count()).isEqualTo(24)
        );
    }

}
