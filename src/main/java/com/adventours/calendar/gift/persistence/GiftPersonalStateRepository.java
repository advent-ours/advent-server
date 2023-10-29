package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.gift.domain.GiftPersonalState;
import com.adventours.calendar.gift.domain.GiftPersonalStatePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GiftPersonalStateRepository extends JpaRepository<GiftPersonalState, GiftPersonalStatePk> {
    @Query(value =
            "select count(*) " +
            "from gift_personal_state ps " +
            "join gift g " +
            "on ps.gift_id = g.gift_id " +
            "where g.calendar_id = :calendarId " +
            "and g.days <= :todayDate " +
            "and ps.user_id = :userId " +
            "and ps.is_opened = 'F'",
            nativeQuery = true)
    Long countNotOpenedGift(UUID calendarId, Long userId, int todayDate);
}