package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;
import lombok.Getter;

@Getter
public abstract class OAuthProviderInformation {
    private OAuthProvider provider;
    private String providerId;
    private String initialProfileImage;
}
