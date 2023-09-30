package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.persistence.GiftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.adventours.calendar.gift.domain.GiftType.INIT;
import static org.assertj.core.api.Assertions.assertThat;

class CalendarControllerTest extends ApiTest {

    @Autowired CalendarRepository calendarRepository;
    @Autowired GiftRepository giftRepository;

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
    @DisplayName("캘린더 생성/수정 성공")
    void updateCalendar() {
        final long userId = 1L;
        final UpdateCalendarRequest request;

        giftService.updateGift(userId, request);

        final Gift gift = giftRepository.findById(1L).get();
        Assertions.assertAll(
                () -> assertThat(gift.getBody()).isEqualTo(""),
                () -> assertThat(gift.getGiftType()).isEqualTo(INIT)
        );
    }
}
