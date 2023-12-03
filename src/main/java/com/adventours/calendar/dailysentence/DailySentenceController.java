package com.adventours.calendar.dailysentence;

import com.adventours.calendar.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DailySentenceController {

    private final DailySentenceRepository dailySentenceRepository;
    private final Clock clock;

    @RequestMapping("/dailysentence")
    public ResponseEntity<CommonResponse<String>> dailySentence() {
        LocalDate today = LocalDate.now(clock);
        LocalDate startOfService = LocalDate.of(2023, 12, 1);
        LocalDate endOfService = LocalDate.of(2023, 12, 25);
        // 2023년 12월 이전
        if (today.isBefore(startOfService)) {
            return ResponseEntity.ok(new CommonResponse<>("크리스마스가 얼마 남지 않았어요."));
        } else if (today.isAfter(endOfService)) {
            return ResponseEntity.ok(new CommonResponse<>("다음 크리스마스에 또 만나요."));
        } else {
            long day = today.getDayOfMonth();
            DailySentence dailySentence = dailySentenceRepository.findById(day).get();
            return ResponseEntity.ok(new CommonResponse<>(dailySentence.getSentence()));
        }
    }

}
