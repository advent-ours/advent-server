package com.adventours.calendar.calendar.presentation;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.UserContext;
import com.adventours.calendar.calendar.service.CalendarListResponse;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import com.adventours.calendar.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Auth
    @GetMapping("/my")
    public ResponseEntity<List<CalendarListResponse>> getMyCalendarList() {
        final Long userId = UserContext.getContext();
        final List<CalendarListResponse> myCalendarList = calendarService.getMyCalendarList(userId);
        return ResponseEntity.ok(myCalendarList);
    }

    @Auth
    @PostMapping("/sub/{calendarId}")
    public ResponseEntity<CommonResponse<Void>> subscribeCalendar(@PathVariable final String calendarId) {
        final Long userId = UserContext.getContext();
        calendarService.subscribe(userId, calendarId);
        return ResponseEntity.ok(new CommonResponse<>());
    }
}
