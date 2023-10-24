package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.CalendarTemplate;

public record UpdateCalendarRequest(String title, CalendarTemplate template) {
}
