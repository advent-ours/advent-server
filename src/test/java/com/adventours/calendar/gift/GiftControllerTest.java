package com.adventours.calendar.gift;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.domain.GiftPersonalState;
import com.adventours.calendar.gift.domain.GiftType;
import com.adventours.calendar.gift.persistence.GiftPersonalStateRepository;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GiftControllerTest extends ApiTest {

    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    GiftRepository giftRepository;
    @Autowired
    GiftPersonalStateRepository giftPersonalStateRepository;
    @Autowired
    GiftService giftService;
    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("선물 생성/수정 성공")
    void updateGift() {
        Scenario.createCalendar().request()
                .updateGift().request();

        final Gift gift = giftRepository.findById(1L).get();
        Assertions.assertAll(
                () -> assertThat(gift.getTitle()).isEqualTo("제목"),
                () -> assertThat(gift.getTextBody()).isEqualTo("내용"),
                () -> assertThat(gift.getGiftType()).isEqualTo(GiftType.TEXT)
        );
    }

    @Test
    @DisplayName("선물 리스트 조회 성공")
    void getGiftList() {
        Scenario.createCalendar().request();
        final Calendar calendar = calendarRepository.findAll().get(0);

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/{calendarId}/gift", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void openGift() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request().
                openGift().calendarId(calendar.getTitle()).request();

        final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findAll().get(0);

        assertThat(giftPersonalState.isOpened()).isTrue();
    }
}