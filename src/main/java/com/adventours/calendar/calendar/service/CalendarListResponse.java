package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.Calendar;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CalendarListResponse(
        UUID id,
        Long userId,
        String profileImgUrl,
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    static List<CalendarListResponse> toListForResponse(List<Calendar> calendars) {
        return calendars.stream()
                .map(calendar -> new CalendarListResponse(
                        calendar.getId(),
                        calendar.getUser().getId(),
                        calendar.getUser().getProfileImgUrl(),
                        calendar.getTitle(),
                        calendar.getCreatedAt(),
                        calendar.getUpdatedAt()
                )).toList();
    }
}
