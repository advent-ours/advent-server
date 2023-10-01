package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.persistence.SubscribeRepository;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        Scenario.createCalendar().request();
        final String calendarId = calendarRepository.findAll().get(0).getId().toString();

        final Long userId = 1L;
        calendarService.subscribe(userId, calendarId);
        assertThat(subscribeRepository.count()).isOne();
    }
}
