package com.adventours.calendar.calendar.domain;

import com.adventours.calendar.global.BaseTime;
import com.adventours.calendar.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Entity
@Table(name = "CALENDAR")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Calendar extends BaseTime {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "CALENDAR_ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CalendarTemplate template;

    public Calendar(final User user, final String title, final CalendarTemplate template) {
        this.user = user;
        this.title = title;
        this.template = template;
    }

    public Calendar(final UUID id, final User user, final String title, final CalendarTemplate template) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.template = template;
    }

    public boolean isOwner(final Long userId) {
        return user.getId().equals(userId);
    }

    public void update(final String title, CalendarTemplate template) {
        this.title = title;
        this.template = template;
    }
}
