package com.adventours.calendar.gift.service;

import com.adventours.calendar.gift.domain.GiftType;

public record UpdateGiftRequest(
        Long id,
        GiftType giftType,
        String title,
        String textBody,
        String contentUrl) {
}
