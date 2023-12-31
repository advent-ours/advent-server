package com.adventours.calendar.gift.domain;

import com.adventours.calendar.user.domain.User;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class GiftPersonalStatePk implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GIFT_ID", nullable = false, referencedColumnName = "GIFT_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Gift gift;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false, referencedColumnName = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    public GiftPersonalStatePk(final Gift gift, final User user) {
        this.gift = gift;
        this.user = user;
    }

    public GiftPersonalStatePk() {
    }
}
