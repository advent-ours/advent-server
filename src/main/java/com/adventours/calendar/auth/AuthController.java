package com.adventours.calendar.auth;

import com.adventours.calendar.exception.NotSupportedDeviceException;
import com.adventours.calendar.global.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Value("${client-version.ios.required}")
    private String REQUIRED_IOS_APP_VERSION;
    @Value("${client-version.ios.latest}")
    private String LATEST_IOS_APP_VERSION;
    @Value("${client-version.aos.required}")
    private String REQUIRED_ANDROID_APP_VERSION;
    @Value("${client-version.aos.latest}")
    private String LATEST_ANDROID_APP_VERSION;

    @GetMapping("/version")
    public ResponseEntity<CommonResponse<VersionResponse>> version(@RequestParam String os, @RequestParam String appVersion) {
        VersionResponse versionResponse = new VersionResponse(
                REQUIRED_IOS_APP_VERSION,
                LATEST_IOS_APP_VERSION,
                REQUIRED_ANDROID_APP_VERSION,
                LATEST_ANDROID_APP_VERSION,
                checkUpdateState(os, appVersion)
        );
        return ResponseEntity.ok(new CommonResponse<>(versionResponse));
    }

    private UpdateRequiredState checkUpdateState(String os, String appVersion) {
        switch (os) {
            case "IOS":
                if (isUpdateRequired(REQUIRED_IOS_APP_VERSION, appVersion)) {
                    return UpdateRequiredState.FORCE;
                } else if(isUpdateRequired(LATEST_IOS_APP_VERSION, appVersion)) {
                    return UpdateRequiredState.RECOMMENDED;
                } else {
                    return UpdateRequiredState.LATEST;
                }
            case "AOS":
                if (isUpdateRequired(REQUIRED_ANDROID_APP_VERSION, appVersion)) {
                    return UpdateRequiredState.FORCE;
                } else if(isUpdateRequired(LATEST_ANDROID_APP_VERSION, appVersion)) {
                    return UpdateRequiredState.RECOMMENDED;
                } else {
                    return UpdateRequiredState.LATEST;
                }
            default:
                throw new NotSupportedDeviceException();
        }
    }

    private boolean isUpdateRequired(String appVersion, String targetVersion) {
        String[] appVersionSplitString = appVersion.split("\\.");
        String[] requiredVersionSplitString = targetVersion.split("\\.");

        for (int i = 0; i < 3; i++) {
            if (Integer.parseInt(appVersionSplitString[i]) < Integer.parseInt(requiredVersionSplitString[i])) {
                return true;
            }
        }
        return false;
    }
}
