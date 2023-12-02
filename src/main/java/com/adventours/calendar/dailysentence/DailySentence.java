package com.adventours.calendar.dailysentence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DailySentence {
    @Id
    @Column(name = "day")
    private Long day;
    private String sentence;

}
