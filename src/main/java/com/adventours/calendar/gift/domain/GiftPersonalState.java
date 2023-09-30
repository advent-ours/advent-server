package com.adventours.calendar.gift.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.type.TrueFalseConverter;

@Getter
@Entity
@Table(name = "GIFT_PERSONAL_STATE")
public class GiftPersonalState {

    @EmbeddedId
    private GiftPersonalStatePk giftPersonalStatePk;

    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    @Convert(converter = TrueFalseConverter.class)
    private boolean isOpened = false;

    private GiftReact react;

    public GiftPersonalState() {
    }

}
