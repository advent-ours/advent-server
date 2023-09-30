package com.adventours.calendar.gift.presentation;

import com.adventours.calendar.auth.Auth;
import com.adventours.calendar.auth.UserContext;
import com.adventours.calendar.gift.service.GiftService;
import com.adventours.calendar.gift.service.UpdateGiftRequest;
import com.adventours.calendar.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GiftController {
    private final GiftService giftService;

    @Auth
    @PostMapping("/calendar/{calendarId}/gifts/{giftId}")
    public ResponseEntity<CommonResponse<Void>> updateGift(@PathVariable final Long calendarId,
                                                           @PathVariable final Long giftId,
                                                           @RequestBody final UpdateGiftRequest request) {
        final Long userId = UserContext.getContext();
        giftService.updateGift(userId, giftId, request);
        return ResponseEntity.ok(new CommonResponse<>());
    }
}
