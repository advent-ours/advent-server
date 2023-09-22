package com.adventours.calendar.member.service;

import com.adventours.calendar.member.domain.OAuthProvider;

interface OAuthRequestPort {
    OAuthProviderInformation requestUserInformation(String token);

    OAuthProvider getOAuthProvider();
}
