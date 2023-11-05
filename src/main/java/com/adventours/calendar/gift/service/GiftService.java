package com.adventours.calendar.gift.service;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.exception.NotFoundGiftException;
import com.adventours.calendar.exception.NotOwnerException;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.domain.GiftPersonalState;
import com.adventours.calendar.gift.domain.GiftPersonalStatePk;
import com.adventours.calendar.gift.persistence.GiftPersonalStateRepository;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cloud.aws.s3.media-url}")
    private String s3MediaUrl;

    @Transactional
    public void updateGift(final long userId, final long giftId, final UpdateGiftRequest request) {
        final Gift gift = getGift(giftId);
        validateOwnerOfGift(userId, gift);
        gift.updateContent(
                request.giftType(),
                request.title(),
                request.textBody(),
                request.uploadKey() == null
                        ? gift.getContentUrl()
                        : createContentUrl(request.uploadKey(), request.extension()));
    }

    private String createContentUrl(String uploadKey, String extension) {
        return s3MediaUrl + uploadKey + "." + extension;
    }

    private static void validateOwnerOfGift(final long userId, final Gift gift) {
        if (!gift.getCalendar().getUser().getId().equals(userId)) {
            throw new NotOwnerException();
        }
    }

    private Gift getGift(final Long giftId) {
        return giftRepository.findById(giftId).orElseThrow(NotFoundGiftException::new);
    }

    public List<GiftListResponse> getGiftList(final Long userId, final String calendarId) {
        final User user = userRepository.getReferenceById(userId);
        final Calendar calendar = calendarRepository.getReferenceById(UUID.fromString(calendarId));
        final List<Gift> giftList = giftRepository.findByCalendar(calendar);
        return giftList.stream().map(gift -> {
            final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findById(
                    new GiftPersonalStatePk(gift, user)).orElse(new GiftPersonalState());
            return new GiftListResponse(
                    gift.getId(),
                    gift.getOpenAt(),
                    gift.getCreatedAt(),
                    gift.getUpdatedAt(),
                    giftPersonalState.isOpened()
            );
        }).toList();
    }

    @Transactional
    public GiftDetailResponse getGiftDetail(Long userId, Long giftId) {
        final User user = userRepository.getReferenceById(userId);
        final Gift gift = giftRepository.findById(giftId).orElseThrow(NotFoundGiftException::new);
        final boolean isMyGift = gift.getCalendar().getUser().equals(user);
        final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findById(new GiftPersonalStatePk(gift, user)).orElse(new GiftPersonalState());
        giftPersonalState.open();
        return new GiftDetailResponse(
                gift.getId(),
                gift.getOpenAt(),
                gift.getGiftType(),
                gift.getTitle(),
                gift.getTextBody(),
                gift.getContentUrl(),
                gift.getCreatedAt(),
                gift.getUpdatedAt(),
                isMyGift,
                giftPersonalState.isReacted(),
                isMyGift ? giftPersonalStateRepository.countReactedCountByGiftId(gift.getId()) : null
        );
    }
}
