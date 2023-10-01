package com.adventours.calendar.user.api;

import com.adventours.calendar.common.DBTestUtil;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.domain.User;

public class CreateUserDB {

    private Long id;
    private OAuthProvider provider = OAuthProvider.KAKAO;
    private String providerId = "providerId";
    private String nickname = "nickname";
    private String profileImgUrl = "profileImgUrl";

    public CreateUserDB id(Long id) {
        this.id = id;
        return this;
    }

    public CreateUserDB provider(OAuthProvider provider) {
        this.provider = provider;
        return this;
    }

    public CreateUserDB providerId(String providerId) {
        this.providerId = providerId;
        return this;
    }

    public CreateUserDB nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public CreateUserDB profileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
        return this;
    }

    public User create() {
        return DBTestUtil.userRepository.save(new User(
                id,
                provider,
                providerId,
                nickname,
                profileImgUrl
        ));
    }
}
