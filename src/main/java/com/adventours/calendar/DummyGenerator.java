package com.adventours.calendar;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.domain.CalendarTemplate;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Profile("dev")
@Component
@RequiredArgsConstructor
public class DummyGenerator implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CalendarService calendarService;
    private final CalendarRepository calendarRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("===== Dummy Data Generating =====");
        userRepository.save(new User(
                1L,
                OAuthProvider.KAKAO,
                "providerId1",
                "nickname1",
                "image.png"
        ));
        userRepository.save(new User(
                2L,
                OAuthProvider.KAKAO,
                "providerId2",
                "nickname2",
                "image.png"
                ));

        calendarService.createCalendar(1L, new CreateCalendarRequest("title", CalendarTemplate.GREEN));
        calendarService.createCalendar(1L, new CreateCalendarRequest("title", CalendarTemplate.RED));

        List<Calendar> calendarList = calendarRepository.findAll();
        calendarService.subscribe(2L, String.valueOf(calendarList.get(0).getId()));
        calendarService.subscribe(2L, String.valueOf(calendarList.get(1).getId()));

        System.out.println("===== Dummy Data Generated =====");
    }
}
