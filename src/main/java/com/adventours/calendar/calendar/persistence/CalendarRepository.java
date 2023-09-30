package com.adventours.calendar.calendar.persistence;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CalendarRepository extends JpaRepository<Calendar, UUID> {
    List<Calendar> findAllByUser(User user);
}