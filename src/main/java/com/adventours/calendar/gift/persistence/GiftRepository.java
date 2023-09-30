package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.gift.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findByCalendar(Calendar calendar);

}