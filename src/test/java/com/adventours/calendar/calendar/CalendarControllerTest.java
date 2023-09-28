package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.persistence.GiftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarControllerTest extends ApiTest {

    @Autowired CalendarRepository calendarRepository;
    @Autowired GiftRepository giftRepository;

    @Test
    void request() {
        Scenario.createCalendar().request();
        Assertions.assertAll(
            () -> assertThat(calendarRepository.count()).isEqualTo(1),
            () -> assertThat(giftRepository.count()).isEqualTo(24)
        );
    }

}
