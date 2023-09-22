package com.adventours.calendar.member.service;

import com.adventours.calendar.member.domain.OAuthProvider;
import lombok.Getter;

@Getter
public abstract class OAuthProviderInformation {
    private OAuthProvider provider;
    private String providerId;
    private String initialProfileImage;
}
