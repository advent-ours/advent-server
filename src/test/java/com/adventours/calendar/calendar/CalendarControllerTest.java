package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarControllerTest extends ApiTest {

    @Autowired CalendarRepository calendarRepository;
    @Autowired GiftRepository giftRepository;
    @Autowired GiftService giftService;

    @Test
    @DisplayName("캘린더 생성 성공")
    void createCalendar() {
        Scenario.createCalendar().request();
        Assertions.assertAll(
            () -> assertThat(calendarRepository.count()).isEqualTo(1),
            () -> assertThat(giftRepository.count()).isEqualTo(24)
        );
    }

}
