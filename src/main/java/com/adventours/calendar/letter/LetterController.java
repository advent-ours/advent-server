package com.adventours.calendar.letter;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.UserContext;
import com.adventours.calendar.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

    @Auth
    @PostMapping("/calendar/{calendarId}/letter")
    public ResponseEntity<CommonResponse<Void>> createLetter(@RequestBody final SendLetterRequest request, @PathVariable String calendarId) {
        Long userId = UserContext.getContext();
        letterService.createLetter(userId, calendarId, request);
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @Auth
    @GetMapping("/calendar/{calendarId}/letter")
    public ResponseEntity<CommonResponse<List<LetterListResponse>>> getLetter(@PathVariable String calendarId) {
        Long userId = UserContext.getContext();
        List<LetterListResponse> letterList = letterService.getLetter(userId, calendarId);
        return ResponseEntity.ok(new CommonResponse<>(letterList));
    }
}
