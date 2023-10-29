package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.CalendarTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

public record MyCalendarListResponse(
        UUID id,
        Long userId,
        String nickname,
        String profileImgUrl,
        String title,
        Long subscriberCount,
        CalendarTemplate template,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
