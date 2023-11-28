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

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final GiftRepository giftRepository;
    private final CalendarRepository calendarRepository;
    private final GiftPersonalStateRepository giftPersonalStateRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    @Value("${cloud.aws.s3.dir}")
    private String bucketDir;

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
                getContentUrl(request));
    }

    private String getContentUrl(UpdateGiftRequest request) {
        switch (request.giftType()) {
            case LINK -> {
                return request.link();
            }
            case IMAGE,RECORD -> {
                return createContentUrl(request.uploadKey(), request.extension());
            }
            default -> {
                return null;
            }
        }
    }

    private String createContentUrl(String uploadKey, String extension) {
        return s3MediaUrl + "/" + bucketDir + "/" + uploadKey + "." + extension;
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
                    gift.getGiftType(),
                    giftPersonalState.isOpened(),
                    gift.getOpenAt(),
                    gift.getCreatedAt(),
                    gift.getUpdatedAt()
            );
        }).toList();
    }

    @Transactional
    public GiftDetailResponse getGiftDetail(Long userId, Long giftId) {
        final User user = userRepository.getReferenceById(userId);
        final Gift gift = giftRepository.findById(giftId).orElseThrow(NotFoundGiftException::new);
        final boolean isMyCalendar = gift.getCalendar().getUser().equals(user);
        boolean isReacted = false;
        if (!isMyCalendar) {
            final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findById(new GiftPersonalStatePk(gift, user)).orElse(new GiftPersonalState());
            giftPersonalState.open();
            isReacted = giftPersonalState.isReacted();
        }
        return new GiftDetailResponse(
                gift.getId(),
                gift.getOpenAt(),
                gift.getGiftType(),
                gift.getTitle(),
                gift.getTextBody(),
                gift.getContentUrl(),
                gift.getCreatedAt(),
                gift.getUpdatedAt(),
                isMyCalendar,
                isReacted,
                isMyCalendar ? giftPersonalStateRepository.countReactedCountByGiftId(gift.getId()) : null
        );
    }

    @Transactional
    public void reactGift(Long userId, Long giftId) {
        final User user = userRepository.getReferenceById(userId);
        final Gift gift = giftRepository.findById(giftId).orElseThrow(NotFoundGiftException::new);
        final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findById(new GiftPersonalStatePk(gift, user)).orElse(new GiftPersonalState());
        if (giftPersonalState.isReacted()) {
            giftPersonalState.cancelReact();
        } else {
            giftPersonalState.react();
        }
    }

    public List<GiftListResponse> getMyGiftListSummary(Long userId, String calendarId) {
        final Calendar calendar = calendarRepository.getReferenceById(UUID.fromString(calendarId));
        final List<Gift> giftList = giftRepository.findByCalendar(calendar);
        return giftList.stream()
                .filter(gift -> gift.getTitle() != null)
                .map(gift -> new GiftListResponse(
                        gift.getId(),
                        gift.getGiftType(),
                        false,
                        gift.getOpenAt(),
                        gift.getCreatedAt(),
                        gift.getUpdatedAt()
                )).toList();
    }

    public List<GiftListResponse> getSubGiftListSummary(Long userId, String calendarId) {
        final User user = userRepository.getReferenceById(userId);
        final Calendar calendar = calendarRepository.getReferenceById(UUID.fromString(calendarId));
        final List<Gift> giftList = giftRepository.findByCalendar(calendar);
        final LocalDateTime today = LocalDate.now(clock).atStartOfDay();
        return giftList.stream()
                .filter(gift -> gift.getOpenAt().isBefore(today))
                .filter(gift -> gift.getTitle() != null)
                .map(gift -> {
            final GiftPersonalState giftPersonalState = giftPersonalStateRepository.findById(
                    new GiftPersonalStatePk(gift, user)).orElse(new GiftPersonalState());
            return new GiftListResponse(
                    gift.getId(),
                    gift.getGiftType(),
                    giftPersonalState.isOpened(),
                    gift.getOpenAt(),
                    gift.getCreatedAt(),
                    gift.getUpdatedAt()
            );
        }).toList();
    }
}
