package com.adventours.calendar.letter.domain;

import com.adventours.calendar.calendar.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<Letter> findAllByCalendar(Calendar calendar);
}