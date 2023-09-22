package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
public class KakaoUserInformation extends OAuthProviderInformation {
    private static final OAuthProvider provider = OAuthProvider.KAKAO;

    @JsonProperty(value = "id")
    private String providerId;
    @JsonProperty(value = "connected_at")
    private String connectedAt;
    private KakaoOAuthProperties properties;

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    public static class KakaoOAuthProperties {
        private String nickname;
        @JsonProperty(value = "profile_image")
        private String profileImage;
        @JsonProperty(value = "thumbnail_image")
        private String thumbnailImage;
        @JsonProperty(value = "kakao_account")
        private String kakaoAccount;
    }

    public KakaoUserInformation(
            final String providerId,
            final String connectedAt,
            final String nickname,
            final String profileImage,
            final String thumbnailImage,
            final String kakaoAccount) {
        this.providerId = providerId;
        this.connectedAt = connectedAt;
        this.properties = new KakaoOAuthProperties(
                nickname,
                profileImage,
                thumbnailImage,
                kakaoAccount);
    }
}
