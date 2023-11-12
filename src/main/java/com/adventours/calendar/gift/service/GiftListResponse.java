package com.adventours.calendar.gift.service;

import com.adventours.calendar.gift.domain.GiftType;

import java.time.LocalDateTime;

public record GiftListResponse(
        Long giftId,
        GiftType giftType,
        boolean isOpened,
        LocalDateTime openAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
