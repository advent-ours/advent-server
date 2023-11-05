package com.adventours.calendar.gift.service;

import java.time.LocalDateTime;

public record GiftListResponse(
        Long giftId,
        LocalDateTime openAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isOpened
) {
}
