package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.gift.domain.GiftPersonalState;
import com.adventours.calendar.gift.domain.GiftPersonalStatePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftPersonalStateRepository extends JpaRepository<GiftPersonalState, GiftPersonalStatePk> {
}