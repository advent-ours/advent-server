package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.CalendarTemplate;

public record CreateCalendarRequest(String title, CalendarTemplate template) {
}
