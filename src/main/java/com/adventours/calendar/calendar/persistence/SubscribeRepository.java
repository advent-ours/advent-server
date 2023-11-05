package com.adventours.calendar.calendar.persistence;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.subscribe.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    @Query("select count(s) from Subscribe s where s.subscribePk.calendar = :calendar")
    Long countByCalendar(Calendar calendar);

    @Modifying
    @Query("delete from Subscribe s where s.subscribePk.calendar = :calendar")
    void deleteAllByCalendar(@Param("calendar") Calendar calendar);
}