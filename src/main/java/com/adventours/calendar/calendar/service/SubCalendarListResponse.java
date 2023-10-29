package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.domain.CalendarTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SubCalendarListResponse(
        UUID id,
        Long userId,
        String nickname,
        String profileImgUrl,
        String title,
        Long notReadCount,
        CalendarTemplate template,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    static List<SubCalendarListResponse> toListForResponse(List<Calendar> calendars) {
        return calendars.stream()
                .map(calendar -> new SubCalendarListResponse(
                        calendar.getId(),
                        calendar.getUser().getId(),
                        calendar.getUser().getNickname(),
                        calendar.getUser().getProfileImgUrl(),
                        calendar.getTitle(),
                        0L,
                        calendar.getTemplate(),
                        calendar.getCreatedAt(),
                        calendar.getUpdatedAt()
                )).toList();
    }
}
