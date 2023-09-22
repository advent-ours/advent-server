package com.adventours.calendar;

interface OAuthRequestPort {
    OAuthUserInformation requestUserInformation(String token);
}
