package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.CalendarTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

public record CalendarDetailResponse(
        UUID id,
        Long userId,
        String nickname,
        String profileImgUrl,
        String title,
        CalendarTemplate template,
        boolean isMyCalendar,
        Long notOpenedCount,
        Long subscriberCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
