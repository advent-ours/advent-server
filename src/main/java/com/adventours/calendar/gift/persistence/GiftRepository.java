package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.gift.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findByCalendar(Calendar calendar);

    Optional<Gift> findByCalendarAndDays(Calendar calendar, int days);

    List<Gift> findAllByCalendar(Calendar calendar);
}