package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;
import lombok.Getter;

@Getter
public abstract class OAuthUserInformation {

    private OAuthProvider provider;
    private String providerId;
    private String initialProfileImage;
}
