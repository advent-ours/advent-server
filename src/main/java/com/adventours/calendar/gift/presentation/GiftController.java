package com.adventours.calendar.gift.presentation;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.UserContext;
import com.adventours.calendar.gift.service.GiftDetailResponse;
import com.adventours.calendar.gift.service.GiftListResponse;
import com.adventours.calendar.gift.service.GiftService;
import com.adventours.calendar.gift.service.UpdateGiftRequest;
import com.adventours.calendar.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GiftController {
    private final GiftService giftService;

    @Auth
    @PostMapping("/calendar/{calendarId}/gift/{giftId}")
    public ResponseEntity<CommonResponse<Void>> updateGift(@PathVariable final String calendarId,
                                                           @PathVariable final Long giftId,
                                                           @RequestBody final UpdateGiftRequest request) {
        final Long userId = UserContext.getContext();
        giftService.updateGift(userId, giftId, request);
        return ResponseEntity.ok(new CommonResponse<>());
    }

    @Auth
    @GetMapping("/calendar/{calendarId}/gift")
    public ResponseEntity<CommonResponse<List<GiftListResponse>>> getGiftList(@PathVariable final String calendarId) {
        final Long userId = UserContext.getContext();
        final List<GiftListResponse> giftList = giftService.getGiftList(userId, calendarId);
        return ResponseEntity.ok(new CommonResponse<>(giftList));
    }

    @Auth
    @GetMapping("/calendar/{calendarId}/gift/my/summary")
    public ResponseEntity<CommonResponse<List<GiftListResponse>>> getMyGiftListSummaryList(@PathVariable final String calendarId) {
        final Long userId = UserContext.getContext();
        final List<GiftListResponse> giftList = giftService.getMyGiftListSummary(userId, calendarId);
        return ResponseEntity.ok(new CommonResponse<>(giftList));
    }

    @Auth
    @GetMapping("/calendar/{calendarId}/gift/sub/summary")
    public ResponseEntity<CommonResponse<List<GiftListResponse>>> getSubGiftListSummary(@PathVariable final String calendarId) {
        final Long userId = UserContext.getContext();
        final List<GiftListResponse> giftList = giftService.getSubGiftListSummary(userId, calendarId);
        return ResponseEntity.ok(new CommonResponse<>(giftList));
    }

    @Auth
    @GetMapping("/calendar/{calendarId}/gift/{giftId}")
    public ResponseEntity<CommonResponse<GiftDetailResponse>> getGiftDetail(@PathVariable final String calendarId,
                                                                            @PathVariable final Long giftId) {
        final Long userId = UserContext.getContext();
        final GiftDetailResponse giftDetail = giftService.getGiftDetail(userId, giftId);
        return ResponseEntity.ok(new CommonResponse<>(giftDetail));
    }

    @Auth
    @PostMapping("/calendar/{calendarId}/gift/{giftId}/react")
    public ResponseEntity<CommonResponse<Void>> reactGift(@PathVariable final String calendarId,
                                                          @PathVariable final Long giftId) {
        final Long userId = UserContext.getContext();
        giftService.reactGift(userId, giftId);
        return ResponseEntity.ok(new CommonResponse<>());
    }
}
