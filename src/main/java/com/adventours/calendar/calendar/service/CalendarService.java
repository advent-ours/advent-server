package com.adventours.calendar.calendar.service;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.calendar.persistence.SubscribeRepository;
import com.adventours.calendar.exception.AlreadyExistCalendarException;
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
        init24Gifts(calendar);
    }

    private void init24Gifts(final Calendar calendar) {
        //TODO: bulk Insert로 성능 개선 필요 (승현쌤 블로그나 구경가자), @batchsize로 가능할듯?
        List<Gift> gifts = new ArrayList<>(24);
        for (int i = 1; i <= 24; i++) {
            gifts.add(Gift.initOf(calendar, i));
        }
        giftRepository.saveAll(gifts);
    }

    private Calendar createCalendar(final CreateCalendarRequest request, final User user) {
        final Calendar calendar;
        try {
            calendar = calendarRepository.save(new Calendar(user, request.title()));
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistCalendarException();
        }
        return calendar;
    }

    @Transactional(readOnly = true)
    public List<CalendarListResponse> getMyCalendarList(final Long userId) {
        final User user = userRepository.getReferenceById(userId);
        final List<Calendar> calendarList = calendarRepository.findAllByUser(user);
        return CalendarListResponse.toListForResponse(calendarList);
    }

    public void subscribe(final Long userId, final String calendarId) {
        final User user = userRepository.getReferenceById(userId);
        final Calendar calendar = calendarRepository.getReferenceById(UUID.fromString(calendarId));
        try {
            subscribeRepository.save(new Subscribe(new SubscribePk(user, calendar)));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException();
        }
        init24PersonalStateData(calendar, user);
    }

    private void init24PersonalStateData(final Calendar calendar, final User user) {
        //TODO: bulk Insert로 성능 개선 필요 (승현쌤 블로그나 구경가자), @batchsize로 가능할듯?
        List<GiftPersonalState> giftPersonalStateList = new ArrayList<>(24);
        List<Gift> giftList = giftRepository.findAllByCalendar(calendar);
        for (int i = 1; i <= 24; i++) {
            giftPersonalStateList.add(new GiftPersonalState(new GiftPersonalStatePk(giftList.get(i-1), user)));
        }
        giftPersonalStateRepository.saveAll(giftPersonalStateList);
    }

    @Transactional(readOnly = true)
    public List<CalendarListResponse> getSubscribeList(final Long userId) {
        final User user = userRepository.getReferenceById(userId);
        List<Calendar> calendarList = calendarRepository.findAllBySubscriber(user);
        return CalendarListResponse.toListForResponse(calendarList);
    }

    @Transactional
    public void updateCalendar(final Long userId, final String calendarId, final UpdateCalendarRequest request) {
        final Calendar calendar = calendarRepository.findById(UUID.fromString(calendarId)).orElseThrow();
        if (!calendar.isOwner(userId)) {
            throw new RuntimeException();
        }
        calendar.update(request.title());
    }
}
