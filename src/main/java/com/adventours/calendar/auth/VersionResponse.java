package com.adventours.calendar.auth;

public record VersionResponse(
        String requiredIosAppVersion,
        String latestIosAppVersion,
        String requiredAosAppVersion,
        String latestAosAppVersion,
        UpdateRequiredState updateRequired
) {
}
