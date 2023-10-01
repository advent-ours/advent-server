package com.adventours.calendar.api;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.common.DBTestUtil;
import com.adventours.calendar.user.domain.User;

import java.util.UUID;

public class CreateCalendarDB {

    private UUID uuid;
    private User user;
    private String title = "title";

    public CreateCalendarDB uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public CreateCalendarDB user(User user) {
        this.user = user;
        return this;
    }

    public CreateCalendarDB title(String title) {
        this.title = title;
        return this;
    }

    public Calendar create() {
        return DBTestUtil.calendarRepository.save(new Calendar(
                uuid,
                user,
                title));
    };
}
