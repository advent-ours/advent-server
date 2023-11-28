package com.adventours.calendar.gift;

import com.adventours.calendar.auth.JwtTokenIssuer;
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
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
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
    @Autowired
    JwtTokenIssuer jwtTokenIssuer;
    @SpyBean
    Clock clock;

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
    @DisplayName("내가 구독한 캘린더의 선물 summary")
    void getSubGiftListSummaryList() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/{calendarId}/gift/sub/summary", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);
    }


    @Test
    @DisplayName("내 캘린더 선물 모아보기")
    void getSummaryMyCalendarList() {
        final User user = Scenario.createUserDB().id(1L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.updateGift().giftId(1L).request()
                .updateGift().giftId(2L).request()
                .updateGift().giftId(3L).request();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/{calendarId}/gift/my/summary", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("구독 캘린더 선물 모아보기 - 열람 가능한 선물 모아보기")
    void getSummarySubCalendarList() {
        final User user2 = Scenario.createUserDB().id(2L).create();
        final String user2AccessToken = jwtTokenIssuer.issueToken(user2.getId()).accessToken();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user2).create();
        Scenario.updateGift().accessToken(user2AccessToken).giftId(1L).request()
                .updateGift().accessToken(user2AccessToken).giftId(2L).request()
                .updateGift().accessToken(user2AccessToken).giftId(3L).request();


        Instant instant = Instant.now().atZone(ZoneOffset.UTC).plusHours(9)
                .withMonth(12).withDayOfMonth(13).toInstant();
        BDDMockito.given(clock.instant())
                .willReturn(instant);
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/{calendarId}/gift/sub/summary", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("내 선물 열기 성공")
    void openGift_my() {
        final User user = userRepository.getReferenceById(1L);
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request().
                openGift().calendarId(calendar.getTitle()).request();
    }

    @Test
    @DisplayName("선물에 반응 보내기 성공")
    void react_gift() {
        final User user = userRepository.getReferenceById(1L);
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request().
                openGift().calendarId(calendar.getTitle()).request().
                reactGift().request();

        final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findAll().get(0);

        assertThat(giftPersonalState.isReacted()).isTrue();
    }

    @Test
    @DisplayName("선물에 반응 취소하기 성공")
    void react_gift_cancel() {
        final User user = userRepository.getReferenceById(1L);
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request().
                openGift().calendarId(calendar.getTitle()).request().
                reactGift().request()
                .reactGift().request();

        final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findAll().get(0);

        assertThat(giftPersonalState.isReacted()).isFalse();
    }
}
