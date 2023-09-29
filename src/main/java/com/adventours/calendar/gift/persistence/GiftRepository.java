package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.gift.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}