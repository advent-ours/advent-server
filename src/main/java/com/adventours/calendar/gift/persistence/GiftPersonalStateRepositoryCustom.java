package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.gift.domain.GiftPersonalState;

import java.util.List;

public interface GiftPersonalStateRepositoryCustom {


    void saveAllBulk(List<GiftPersonalState> giftPersonalStateList);
}
