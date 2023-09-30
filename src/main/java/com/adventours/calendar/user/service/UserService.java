package com.adventours.calendar.user.service;

import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Map<OAuthProvider, OAuthRequestPort> oAuthRequestPortMap;
    private final UserRepository userRepository;

    public LoginResponse login(final OAuthProvider provider, final LoginRequest request) {
        OAuthRequestPort oAuthRequestPort = getProperProviderPort(provider);
        OAuthProviderInformation userInformation = oAuthRequestPort.requestUserInformation(request.token());
        Optional<User> optionalUser = userRepository.findByProviderAndProviderId(provider, userInformation.getProviderId());
        if (optionalUser.isPresent()) {
            return new LoginResponse(optionalUser.get().getId(), false);
        } else {
            final User user = userRepository.save(new User(
                    provider,
                    userInformation.getProviderId(),
                    userInformation.getInitialProfileImage()
            ));
            return new LoginResponse(user.getId(), true);
        }
    }

    private OAuthRequestPort getProperProviderPort(final OAuthProvider provider) {
        return oAuthRequestPortMap.get(provider);
    }

    @Transactional
    public void updateNickname(final Long userId, final UpdateNicknameRequest request) {
        final User user = userRepository.findById(userId).orElseThrow();
        user.updateNickname(request.nickname());
    }
}
