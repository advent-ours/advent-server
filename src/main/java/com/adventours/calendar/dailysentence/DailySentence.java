package com.adventours.calendar.dailysentence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class DailySentence {
    @Id
    @Column(name = "day")
    private Long day;
    private String sentence;
}
