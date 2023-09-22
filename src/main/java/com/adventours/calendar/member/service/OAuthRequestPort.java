package com.adventours.calendar.member.service;

import com.adventours.calendar.member.domain.OAuthProvider;

interface OAuthRequestPort {
    OAuthUserInformation requestUserInformation(String token);

    OAuthProvider getOAuthProvider();
}
