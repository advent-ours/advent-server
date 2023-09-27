package com.adventours.calendar.letter.domain;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "LETTER")
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long letterId;

    @ManyToOne
    @JoinColumn(name = "calendarId", nullable = false)
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "fromUserId", nullable = false)
    private User fromUser;

    @Column(nullable = false)
    private String body;

}
