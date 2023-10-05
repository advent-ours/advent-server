package com.adventours.calendar.calendar.api;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.common.DBTestUtil;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.user.domain.User;

import java.util.ArrayList;
import java.util.List;
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
        assert uuid != null;
        assert user != null;
        final Calendar calendar = DBTestUtil.calendarRepository.save(new Calendar(
                uuid,
                user,
                title));
        List<Gift> gifts = new ArrayList<>(24);
        for (int i = 1; i <= 24; i++) {
            gifts.add(Gift.initOf(calendar, i));
        }
        DBTestUtil.giftRepository.saveAll(gifts);
        return calendar;
    };
}
