package com.adventours.calendar;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.domain.CalendarTemplate;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.dailysentence.DailySentence;
import com.adventours.calendar.dailysentence.DailySentenceRepository;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class DummyGenerator implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final CalendarService calendarService;
    private final DailySentenceRepository dailySentenceRepository;

    @Value("${app.default-calendar-id}")
    private String defaultCalendarId;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }
        System.out.println("===== Init Data Generating =====");

        User user = userRepository.save(new User(
                1L,
                OAuthProvider.KAKAO,
                "",
                "team HBD",
                "image.png"
        ));

        Calendar calendar = calendarRepository.save(
                new Calendar(
                        UUID.fromString(defaultCalendarId),
                        user,
                        "HBD팀이 말아주는 크리스마스 캘린더",
                        CalendarTemplate.GREEN
                ));
        calendarService.init25Gifts(calendar);

        for(long i = 0; i < 25; i++) {
            dailySentenceRepository.save(new DailySentence(i, "메리크리스마스!"));
        }
        System.out.println("===== Init Data Generated !!! =====");
    }
}
