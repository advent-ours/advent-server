package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.gift.domain.Gift;

import java.util.List;

public interface GiftRepositoryCustom {
    void saveAllBulk(List<Gift> gifts);
}
