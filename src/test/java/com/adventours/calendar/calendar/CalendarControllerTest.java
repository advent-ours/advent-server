package com.adventours.calendar.calendar;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.domain.GiftType;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import com.adventours.calendar.gift.service.UpdateGiftRequest;
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

    @Test
    @DisplayName("캘린더 생성/수정 성공")
    void updateCalendar() {
        Scenario.createCalendar().request();
        final long userId = 1L;
        final long giftId = 1L;
        final UpdateGiftRequest request = new UpdateGiftRequest(
                giftId,
                GiftType.TEXT,
                "제목",
                "내용",
                null
        );

        giftService.updateGift(userId, request);

        final Gift gift = giftRepository.findById(1L).get();
        Assertions.assertAll(
                () -> assertThat(gift.getTitle()).isEqualTo(request.title()),
                () -> assertThat(gift.getTextBody()).isEqualTo(request.textBody()),
                () -> assertThat(gift.getGiftType()).isEqualTo(request.giftType())
        );
    }
}
