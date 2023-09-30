package com.adventours.calendar.gift.domain;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.global.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "GIFT")
public class Gift extends BaseTime {
    @Id
    @Column(name = "GIFT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CALENDAR_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Calendar calendar;

    @Column(nullable = false)
    private int days;

    //TODO: Enum으로 제공
    @Column(nullable = false)
    private GiftType giftType;

    private String title;

    private String contentUrl;

    private String textBody;

    public Gift() {
    }

    public Gift(final Calendar calendar, final int days, final GiftType giftType) {
        this.calendar = calendar;
        this.days = days;
        this.giftType = giftType;
    }

    public static Gift initOf(final Calendar calendar, final int days) {
        return new Gift(calendar, days, GiftType.INIT);
    }

    public void updateContent(final GiftType giftType, final String title, final String textBody, final String contentUrl) {
        this.giftType = giftType;
        this.title = title;
        this.textBody = textBody;
        this.contentUrl = contentUrl;
    }
}
