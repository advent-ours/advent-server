package com.adventours.calendar.gift.service;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.domain.GiftPersonalState;
import com.adventours.calendar.gift.domain.GiftReact;
import com.adventours.calendar.gift.persistence.GiftPersonalStateRepository;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final CalendarRepository calendarRepository;
    private final GiftPersonalStateRepository giftPersonalStateRepository;
    private final UserRepository userRepository;

    @Transactional
    public void updateGift(final long userId, final long giftId, final UpdateGiftRequest request) {
        final Gift gift = getGift(giftId);
        validateOwnerOfGift(userId, gift);
        gift.updateContent(request.giftType(), request.title(), request.textBody(), request.contentUrl());
    }

    private static void validateOwnerOfGift(final long userId, final Gift gift) {
        if (!gift.getCalendar().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 캘린더의 소유자가 아닙니다.");
        }
    }

    private Gift getGift(final Long giftId) {
        return giftRepository.findById(giftId).orElseThrow();
    }

    public List<GiftListResponse> getGiftList(final Long userId, final String calendarId) {
        final User user = userRepository.getReferenceById(userId);
        final Calendar calendar = calendarRepository.getReferenceById(UUID.fromString(calendarId));
        final List<Gift> giftList = giftRepository.findByCalendar(calendar);
        return giftList.stream().map(gift -> {
            //TODO: 구독 활성화 시 해당 필드 활성화
//            final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findById(new GiftPersonalStatePk(gift, user)).orElseThrow();
            final GiftPersonalState giftPersonalState = new GiftPersonalState();
            final boolean isOpened = giftPersonalState.isOpened();
            final GiftReact react = giftPersonalState.getReact();
            return new GiftListResponse(
                    gift.getId(),
                    gift.getDays(),
                    gift.getGiftType(),
                    gift.getTitle(),
                    gift.getTextBody(),
                    gift.getContentUrl(),
                    gift.getCreatedAt(),
                    gift.getUpdatedAt(),
                    isOpened,
                    react
            );
        }).toList();
    }

    public void openGift(final Long userId, final UUID calendarId, final int days) {
        final User user = userRepository.getReferenceById(userId);
        final Calendar calendar = calendarRepository.getReferenceById(calendarId);
        final Gift gift = giftRepository.findByCalendarAndDays(calendar, days).orElseThrow();
//        giftPersonalStateRepository.findBy
    }
}
