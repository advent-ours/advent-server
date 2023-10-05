package com.adventours.calendar.gift.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GiftReact react = GiftReact.NONE;

    public GiftPersonalState() {
    }

    public GiftPersonalState(final GiftPersonalStatePk giftPersonalStatePk) {
        this.giftPersonalStatePk = giftPersonalStatePk;
    }

    public void open() {
        this.isOpened = true;
    }
}
