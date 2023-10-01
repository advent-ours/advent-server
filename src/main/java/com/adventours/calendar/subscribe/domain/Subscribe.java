package com.adventours.calendar.subscribe.domain;

import com.adventours.calendar.global.BaseTime;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "SUBSCRIBE")
public class Subscribe extends BaseTime {

    @EmbeddedId
    private SubscribePk subscribePk;

    public Subscribe() {
    }

    public Subscribe(SubscribePk subscribePk) {
        this.subscribePk = subscribePk;
    }
}
