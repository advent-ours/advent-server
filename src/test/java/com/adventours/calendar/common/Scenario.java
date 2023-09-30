package com.adventours.calendar.common;

import com.adventours.calendar.api.CreateCalendarApi;
import com.adventours.calendar.gift.api.UpdateGiftApi;

public class Scenario {
    public static CreateCalendarApi createCalendar() {
        return new CreateCalendarApi();
    }

    public UpdateGiftApi updateGift() {
        return new UpdateGiftApi();
    }
}
