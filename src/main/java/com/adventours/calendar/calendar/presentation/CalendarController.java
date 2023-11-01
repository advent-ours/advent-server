package com.adventours.calendar.calendar.presentation;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.UserContext;
import com.adventours.calendar.calendar.service.AwsS3Service;
import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.calendar.service.CreateCalendarRequest;
import com.adventours.calendar.calendar.service.MyCalendarListResponse;
import com.adventours.calendar.calendar.service.SubCalendarListResponse;
import com.adventours.calendar.calendar.service.UpdateCalendarRequest;
import com.adventours.calendar.calendar.service.UploadKeyResponse;
import com.adventours.calendar.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final AwsS3Service awsS3Service;

    @Auth
    @GetMapping("/presigned-url")
    public ResponseEntity<CommonResponse<UploadKeyResponse>> getPresignedUrl(@RequestParam(name = "file-extension") String fileExtension) {
        return ResponseEntity.ok(new CommonResponse<>(awsS3Service.getPresignedUrl(fileExtension)));
    }

    @Auth
    @PostMapping
    public ResponseEntity<CommonResponse<Void>> createCalendar(@RequestBody final CreateCalendarRequest request) {
        final Long userId = UserContext.getContext();
        calendarService.createCalendar(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>());
    }

    @Auth
    @PutMapping("/{calendarId}")
    public ResponseEntity<CommonResponse<Void>> updateCalendar(@PathVariable final String calendarId, @RequestBody final UpdateCalendarRequest request) {
        final Long userId = UserContext.getContext();
        calendarService.updateCalendar(userId, calendarId, request);
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @Auth
    @GetMapping("/my")
    public ResponseEntity<CommonResponse<List<MyCalendarListResponse>>> getMyCalendarList() {
        final Long userId = UserContext.getContext();
        final List<MyCalendarListResponse> myCalendarList = calendarService.getMyCalendarList(userId);
        return ResponseEntity.ok(new CommonResponse<>(myCalendarList));
    }

    @Auth
    @GetMapping("/sub")
    public ResponseEntity<CommonResponse<List<SubCalendarListResponse>>> getSubscribeList() {
        final Long userId = UserContext.getContext();
        final List<SubCalendarListResponse> myCalendarList = calendarService.getSubscribeList(userId);
        return ResponseEntity.ok(new CommonResponse<>(myCalendarList));
    }

    @Auth
    @PostMapping("/sub/{calendarId}")
    public ResponseEntity<CommonResponse<Void>> subscribeCalendar(@PathVariable final String calendarId) {
        final Long userId = UserContext.getContext();
        calendarService.subscribe(userId, calendarId);
        return ResponseEntity.ok(new CommonResponse<>());
    }
}
