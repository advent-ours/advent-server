package com.adventours.calendar.common;

import com.adventours.calendar.api.CreateCalendarApi;
import com.adventours.calendar.gift.api.UpdateGiftApi;
import com.adventours.calendar.user.api.UpdateNicknameApi;

public class Scenario {
    public static CreateCalendarApi createCalendar() {
        return new CreateCalendarApi();
    }

    public static UpdateNicknameApi updateNickname() {
        return new UpdateNicknameApi();
    }

    public UpdateGiftApi updateGift() {
        return new UpdateGiftApi();
    }
}
