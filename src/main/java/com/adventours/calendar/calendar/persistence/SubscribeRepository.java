package com.adventours.calendar.calendar.persistence;

import com.adventours.calendar.subscribe.domain.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
}