package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class AppleUserInformation extends OAuthUserInformation {
    private static final OAuthProvider provider = OAuthProvider.APPLE;
    private String providerId;

    @Override
    public OAuthProvider getProvider() {
        return provider;
    }
}
