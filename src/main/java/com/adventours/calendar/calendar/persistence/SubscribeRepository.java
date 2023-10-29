package com.adventours.calendar.calendar.persistence;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.subscribe.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    @Query("select count(s) from Subscribe s where s.subscribePk.calendar = :calendar")
    Long countByCalendar(Calendar calendar);
}