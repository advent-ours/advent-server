package com.adventours.calendar.common;

import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.user.persistence.UserRepository;

public class DBTestUtil {
    public static UserRepository userRepository;
    public static CalendarRepository calendarRepository;

    public static void setUserRepository(UserRepository userRepository) {
        DBTestUtil.userRepository = userRepository;
    }

    public static void setCalendarRepository(CalendarRepository calendarRepository) {
        DBTestUtil.calendarRepository = calendarRepository;
    }
}
