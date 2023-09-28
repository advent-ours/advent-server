package com.adventours.calendar.calendar.presentation;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.UserContext;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @Auth
    @PostMapping
    public ResponseEntity<Void> createCalendar(@RequestBody final CreateCalendarRequest request) {
        final Long userId = UserContext.getContext();
        calendarService.createCalendar(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
