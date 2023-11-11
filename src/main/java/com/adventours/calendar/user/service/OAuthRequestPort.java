package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;

interface OAuthRequestPort {
    OAuthUserInformation requestUserInformation(String token);

    OAuthProvider getOAuthProvider();
}
