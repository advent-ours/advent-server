package com.adventours.calendar.calendar;

import com.adventours.calendar.auth.JwtTokenIssuer;
import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.domain.CalendarTemplate;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.persistence.SubscribeRepository;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.calendar.service.UpdateCalendarRequest;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenIssuer jwtTokenIssuer;

    @Test
    @Disabled
    @DisplayName("Presigned URL 생성 성공")
    void generatePresignedUrl() {
        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/presigned-url?file-extension=jpg")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("캘린더 생성 성공")
    void createCalendar() {
        Scenario.createCalendar().request();
        Assertions.assertAll(
                () -> assertThat(calendarRepository.count()).isEqualTo(1),
                () -> assertThat(giftRepository.count()).isEqualTo(25)
        );
    }

    @Test
    @DisplayName("캘린더 삭제 성공")
    void deleteCalendar() {
        final User user = userRepository.getReferenceById(1L);
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .delete("/calendar/{calendarId}", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);

        assertThat(calendarRepository.count()).isZero();
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
    @DisplayName("나의 캘린더 목록 조회 성공 - 구독자 필드 확인")
    void getMyCalendarList_subscriber_count() {
        final User user = userRepository.getReferenceById(1L);
        final User user2 = Scenario.createUserDB().id(2L).create();
        String user2AccessToken = jwtTokenIssuer.issueToken(user2.getId()).accessToken();

        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).customAccessToken(user2AccessToken).request();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/my")
                .then()
                .log().all()
                .statusCode(200)
                .body("data[0].subscriberCount", org.hamcrest.Matchers.is(1));
    }

    @Test
    @DisplayName("캘린더 구독 성공")
    void subscribeCalendar() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request();
        assertThat(subscribeRepository.count()).isOne();
    }

    @Test
    @DisplayName("구독 캘린더 목록 조회 성공")
    void getSubscribeList() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar1 = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        final Calendar calendar2 = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        final Calendar calendar3 = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar1.getId()).request()
                .subscribeCalendar().calendarId(calendar2.getId()).request()
                .subscribeCalendar().calendarId(calendar3.getId()).request();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/sub")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("구독 캘린더 목록 조회 성공 - 읽지 않은 선물의 개수가 컬럼으로 확인된다")
    void getSubscribeList_not_read_gifts_count() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar1 = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar1.getId()).request()
                .openGift().calendarId(calendar1.getId().toString()).request();

        ValidatableResponse response = RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .get("/calendar/sub")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("캘린더 수정 성공")
    void updateCalendar() {
        User me = userRepository.findById(1L).get();
        Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(me).create();
        final UpdateCalendarRequest request = new UpdateCalendarRequest("수정된 제목", CalendarTemplate.GREEN);

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .body(request)
                .contentType("application/json")
                .when()
                .put("/calendar/{calendarId}", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);

        assertThat(calendarRepository.findById(calendar.getId()).get().getTitle()).isEqualTo("수정된 제목");
        assertThat(calendarRepository.findById(calendar.getId()).get().getTemplate()).isEqualTo(CalendarTemplate.GREEN);
    }

    @Test
    @DisplayName("캘린더 구독 취소 성공")
    void unsubscribeCalendar() {
        final User user = Scenario.createUserDB().id(2L).create();
        final Calendar calendar = Scenario.createCalendarDB().uuid(UUID.randomUUID()).user(user).create();
        Scenario.subscribeCalendar().calendarId(calendar.getId()).request();
        assertThat(subscribeRepository.count()).isOne();

        RestAssured.given().log().all()
                .header("Authorization", accessToken)
                .when()
                .delete("/calendar/{calendarId}/sub", calendar.getId())
                .then()
                .log().all()
                .statusCode(200);

        assertThat(subscribeRepository.count()).isZero();
    }
}
