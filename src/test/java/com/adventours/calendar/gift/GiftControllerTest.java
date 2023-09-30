package com.adventours.calendar.gift;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.common.ApiTest;
import com.adventours.calendar.common.Scenario;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.domain.GiftType;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.gift.service.GiftService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GiftControllerTest extends ApiTest {

    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    GiftRepository giftRepository;
    @Autowired
    GiftService giftService;

    @Test
    @DisplayName("선물 생성/수정 성공")
    void updateGift() {
        Scenario.createCalendar().request()
                .updateGift().request();

        final Gift gift = giftRepository.findById(1L).get();
        Assertions.assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(gift.getTitle()).isEqualTo("제목"),
                () -> org.assertj.core.api.Assertions.assertThat(gift.getTextBody()).isEqualTo("내용"),
                () -> org.assertj.core.api.Assertions.assertThat(gift.getGiftType()).isEqualTo(GiftType.TEXT)
        );
    }
}