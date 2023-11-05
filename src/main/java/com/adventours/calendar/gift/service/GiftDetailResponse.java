package com.adventours.calendar.gift.service;

import com.adventours.calendar.gift.domain.GiftType;

import java.time.LocalDateTime;

public record GiftDetailResponse(
        Long giftId,
        LocalDateTime openAt,
        GiftType giftType,
        String title,
        String textBody,
        String contentUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isMyGift,
        Boolean isReacted,
        Long reactCount
) {
}
