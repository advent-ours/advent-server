package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;

interface OAuthRequestPort {
    OAuthProviderInformation requestUserInformation(String token);

    OAuthProvider getOAuthProvider();
}
