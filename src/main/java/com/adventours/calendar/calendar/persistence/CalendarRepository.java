package com.adventours.calendar.calendar.persistence;

import com.adventours.calendar.calendar.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, String> {
}