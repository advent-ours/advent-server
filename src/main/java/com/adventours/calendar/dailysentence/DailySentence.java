package com.adventours.calendar.dailysentence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class DailySentence {
    @Id
    Long day;
    String sentence;
}
