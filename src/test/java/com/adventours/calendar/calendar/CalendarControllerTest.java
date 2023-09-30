package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.service.CalendarListResponse;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @DisplayName("나의 캘린더 목록 조회")
    void getMyCalendarList() {
        Scenario.createCalendar().title("캘린더1").request()
                .createCalendar().title("캘린더2").request()
                .createCalendar().title("캘린더3").request()
                .createCalendar().title("캘린더4").request()
                .createCalendar().title("캘린더5").request();

        final Long userId = 1L;
        final List<CalendarListResponse> myCalendarList = calendarService.getMyCalendarList(userId);

        Assertions.assertAll(
                () -> assertThat(myCalendarList.size()).isEqualTo(5),
                () -> assertThat(myCalendarList.get(0).title()).isEqualTo("캘린더1"),
                () -> assertThat(myCalendarList.get(4).title()).isEqualTo("캘린더5")
        );
    }
}
