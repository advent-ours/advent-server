package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.gift.persistence.GiftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarControllerTest extends ApiTest {

    @Autowired CalendarService calendarService;
    @Autowired CalendarRepository calendarRepository;
    @Autowired GiftRepository giftRepository;

    @Test
    void createCalendar() {
        final Long userId = 1L;
        final CreateCalendarRequest request = new CreateCalendarRequest("제목");
        calendarService.createCalendar(userId, request);

        Assertions.assertAll(
            () -> assertThat(calendarRepository.count()).isEqualTo(1),
            () -> assertThat(giftRepository.count()).isEqualTo(24)
        );
    }

}
