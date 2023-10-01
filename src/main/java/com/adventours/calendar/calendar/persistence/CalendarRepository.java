package com.adventours.calendar.calendar.persistence;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CalendarRepository extends JpaRepository<Calendar, UUID> {
    List<Calendar> findAllByUser(User user);

    @Query("select c from Calendar c JOIN Subscribe s on c.id = s.subscribePk.calendar.id WHERE s.subscribePk.user = :user")
    List<Calendar> findAllBySubscriber(@Param("user") User user);
}