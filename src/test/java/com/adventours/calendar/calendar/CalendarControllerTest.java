package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.gift.persistence.GiftRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarControllerTest extends ApiTest {

    private CalendarService calendarService;
    private CalendarRepository calendarRepository;
    private GiftRepository giftRepository;

    @Test
    void createCalendar() {
        final Long userId = 1L;
        final CreateCalendarRequest request;
        calendarService.createCalendar(userId, request);

        Assertions.assertAll(
            () -> assertThat(calendarRepository.count()).isEqualTo(1),
            () -> assertThat(giftRepository.count()).isEqualTo(24)
        );
    }

    private class CalendarService {
    }
}
