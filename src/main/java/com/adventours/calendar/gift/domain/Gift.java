package com.adventours.calendar.gift.domain;

import com.adventours.calendar.calendar.domain.Calendar;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
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
public class Gift {
    @Id
    @Column(name = "GIFT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CALENDAR_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Calendar calendar;

    @Column(nullable = false)
    private int day;

    //TODO: Enum으로 제공
    @Column(nullable = false)
    private String contentType;

    private String contentUrl;

    private String body;
}
