package com.adventours.calendar.common;

import com.adventours.calendar.api.CreateCalendarApi;

public class Scenario {
    public static CreateCalendarApi createCalendar() {
        return new CreateCalendarApi();
    }
}
