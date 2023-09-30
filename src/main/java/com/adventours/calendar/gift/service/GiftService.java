package com.adventours.calendar.gift.service;

import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.persistence.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;

    @Transactional
    public void updateGift(final long userId, final UpdateGiftRequest request) {
        final Gift gift = getGift(request);
        validateOwnerOfGift(userId, gift);
        gift.updateContent(request.giftType(), request.title(), request.textBody(), request.contentUrl());
    }

    private static void validateOwnerOfGift(final long userId, final Gift gift) {
        if (!gift.getCalendar().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 캘린더의 소유자가 아닙니다.");
        }
    }

    private Gift getGift(final UpdateGiftRequest request) {
        return giftRepository.findById(request.id()).orElseThrow();
    }
}
