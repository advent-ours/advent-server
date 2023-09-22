package com.adventours.calendar.member.service;

interface OAuthRequestPort {
    OAuthUserInformation requestUserInformation(String token);
}
