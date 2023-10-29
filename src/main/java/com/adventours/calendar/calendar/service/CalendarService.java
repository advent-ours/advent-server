package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.persistence.SubscribeRepository;
import com.adventours.calendar.exception.AlreadyExistCalendarException;
import com.adventours.calendar.exception.AlreadySubscribedCalendarException;
import com.adventours.calendar.exception.NotFoundCalendarException;
import com.adventours.calendar.exception.NotOwnerException;
import com.adventours.calendar.gift.domain.Gift;
import com.adventours.calendar.gift.domain.GiftPersonalState;
import com.adventours.calendar.gift.domain.GiftPersonalStatePk;
import com.adventours.calendar.gift.persistence.GiftPersonalStateRepository;
import com.adventours.calendar.gift.persistence.GiftRepository;
import com.adventours.calendar.subscribe.domain.Subscribe;
import com.adventours.calendar.subscribe.domain.SubscribePk;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final GiftPersonalStateRepository giftPersonalStateRepository;

    @Transactional
    public void createCalendar(final Long userId, final CreateCalendarRequest request) {
        final User user = userRepository.getReferenceById(userId);
        final Calendar calendar = createCalendar(request, user);
        init25Gifts(calendar);
    }

    private void init25Gifts(final Calendar calendar) {
        //TODO: bulk Insert로 성능 개선 필요 (승현쌤 블로그나 구경가자), @batchsize로 가능할듯?
        List<Gift> gifts = new ArrayList<>(25);
        for (int i = 1; i <= 25; i++) {
            gifts.add(Gift.initOf(calendar, i));
        }
        giftRepository.saveAll(gifts);
    }

    private Calendar createCalendar(final CreateCalendarRequest request, final User user) {
        final Calendar calendar;
        try {
            calendar = calendarRepository.save(new Calendar(user, request.title(), request.template()));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistCalendarException();
        }
        return calendar;
    }

    @Transactional(readOnly = true)
    public List<SubCalendarListResponse> getMyCalendarList(final Long userId) {
        final User user = userRepository.getReferenceById(userId);
        final List<Calendar> calendarList = calendarRepository.findAllByUser(user);
        return SubCalendarListResponse.toListForResponse(calendarList);
    }

    @Transactional(readOnly = true)
    public List<SubCalendarListResponse> getSubscribeList(final Long userId) {
        final User user = userRepository.getReferenceById(userId);
        List<Calendar> calendarList = calendarRepository.findAllBySubscriber(user);
        return createResponseWithNotOpenedGiftCount(calendarList, user);
    }

    private List<SubCalendarListResponse> createResponseWithNotOpenedGiftCount(List<Calendar> calendarList, User user) {
        return calendarList.stream()
                .map(calendar -> new SubCalendarListResponse(
                        calendar.getId(),
                        calendar.getUser().getId(),
                        calendar.getUser().getNickname(),
                        calendar.getUser().getProfileImgUrl(),
                        calendar.getTitle(),
                        giftPersonalStateRepository.countNotOpenedGift(calendar.getId(), user.getId()),
                        calendar.getTemplate(),
                        calendar.getCreatedAt(),
                        calendar.getUpdatedAt()
                )).toList();
    }

    public void subscribe(final Long userId, final String calendarId) {
        final User user = userRepository.getReferenceById(userId);
        final Calendar calendar = calendarRepository.getReferenceById(UUID.fromString(calendarId));
        try {
            subscribeRepository.save(new Subscribe(new SubscribePk(user, calendar)));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadySubscribedCalendarException();
        }
        init25PersonalStateData(calendar, user);
    }

    @Transactional
    public void updateCalendar(final Long userId, final String calendarId, final UpdateCalendarRequest request) {
        final Calendar calendar = calendarRepository.findById(UUID.fromString(calendarId)).orElseThrow(NotFoundCalendarException::new);
        if (!calendar.isOwner(userId)) {
            throw new NotOwnerException();
        }
        calendar.update(request.title(), request.template());
    }

    private void init25PersonalStateData(final Calendar calendar, final User user) {
        //TODO: bulk Insert로 성능 개선 필요 (승현쌤 블로그나 구경가자), @batchsize로 가능할듯?
        List<GiftPersonalState> giftPersonalStateList = new ArrayList<>(25);
        List<Gift> giftList = giftRepository.findAllByCalendar(calendar);
        for (int i = 1; i <= 25; i++) {
            giftPersonalStateList.add(new GiftPersonalState(new GiftPersonalStatePk(giftList.get(i-1), user)));
        }
        giftPersonalStateRepository.saveAll(giftPersonalStateList);
    }
}
