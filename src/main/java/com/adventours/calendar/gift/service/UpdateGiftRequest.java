package com.adventours.calendar.gift.service;

import com.adventours.calendar.gift.domain.GiftType;

public record UpdateGiftRequest(
        GiftType giftType,
        String title,
        String textBody,
        String contentUrl) {
}
