package com.adventours.calendar.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OAuthProvider provider;

    @Column(nullable = false)
    private String providerId;

    @Column(unique = true, nullable = false)
    private String nickname = "새로운친구";

    private String profileImgUrl;

    public User(final OAuthProvider provider, final String providerId, final String profileImgUrl) {
        this.provider = provider;
        this.providerId = providerId;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateNickname(final String nickname) {
        this.nickname = nickname;
    }

    public User() {
    }
}
