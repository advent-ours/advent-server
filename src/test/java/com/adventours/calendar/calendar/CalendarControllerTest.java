package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.persistence.SubscribeRepository;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import com.adventours.calendar.user.domain.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarControllerTest extends ApiTest {

    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    GiftRepository giftRepository;
    @Autowired
    GiftService giftService;
    @Autowired
    CalendarService calendarService;
    @Autowired
    SubscribeRepository subscribeRepository;

    @Test
    @DisplayName("캘린더 생성 성공")
    void createCalendar() {
        Scenario.createCalendar().request();
        Assertions.assertAll(
                () -> assertThat(calendarRepository.count()).isEqualTo(1),
                () -> assertThat(giftRepository.count()).isEqualTo(24)
        );
    }

    @Test
    @DisplayName("나의 캘린더 목록 조회 성공")
    void getMyCalendarList() {
        Scenario.createCalendar().title("캘린더1").request()
                .createCalendar().title("캘린더2").request()
                .createCalendar().title("캘린더3").request()
                .createCalendar().title("캘린더4").request()
                .createCalendar().title("캘린더5").request();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/my")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("캘린더 구독 성공")
    void subscribeCalendar() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .post("/calendar/sub/{calendarId}", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);

        assertThat(subscribeRepository.count()).isOne();
    }
}
