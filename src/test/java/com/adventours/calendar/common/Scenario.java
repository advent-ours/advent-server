package com.adventours.calendar.common;

import com.adventours.calendar.api.CreateCalendarApi;
import com.adventours.calendar.api.CreateCalendarDB;
import com.adventours.calendar.calendar.api.SubscribeCalendarApi;
import com.adventours.calendar.gift.api.OpenGiftApi;
import com.adventours.calendar.gift.api.UpdateGiftApi;
import com.adventours.calendar.user.api.CreateUserDB;
import com.adventours.calendar.user.api.UpdateNicknameApi;

public class Scenario {
    public static CreateUserDB createUserDB() {
        return new CreateUserDB();
    }
    public static CreateCalendarApi createCalendar() {
        return new CreateCalendarApi();
    }
    public static CreateCalendarDB createCalendarDB() {
        return new CreateCalendarDB();
    }

    public static UpdateNicknameApi updateNickname() {
        return new UpdateNicknameApi();
    }

    public static SubscribeCalendarApi subscribeCalendar() {
        return new SubscribeCalendarApi();
    }

    public static OpenGiftApi openGift() {
        return new OpenGiftApi();
    }

    public UpdateGiftApi updateGift() {
        return new UpdateGiftApi();
    }
}
