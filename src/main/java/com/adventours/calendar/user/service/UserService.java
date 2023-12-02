package com.adventours.calendar.user.service;

import com.adventours.calendar.calendar.service.CalendarService;
import com.adventours.calendar.exception.NotFoundUserException;
import com.adventours.calendar.user.domain.OAuthProvider;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Map<OAuthProvider, OAuthRequestPort> oAuthRequestPortMap;
    private final UserRepository userRepository;
    private final CalendarService calendarService;

    @Value("${app.default-calendar-id}")
    private String defaultCalendarId;

    public LoginResponse login(final OAuthProvider provider, final LoginRequest request) {
        OAuthRequestPort oAuthRequestPort = getProperProviderPort(provider);
        OAuthUserInformation userInformation = oAuthRequestPort.requestUserInformation(request.token());
        Optional<User> optionalUser = userRepository.findByProviderAndProviderId(provider, userInformation.getProviderId());
        if (optionalUser.isPresent()) {
            return new LoginResponse(optionalUser.get().getId(), optionalUser.get().getNickname(), false);
        } else {
            final User user = userRepository.save(new User(
                    provider,
                    userInformation.getProviderId(),
                    userInformation.getInitialProfileImage()
            ));
            calendarService.subscribe(user.getId(), defaultCalendarId);
            return new LoginResponse(user.getId(), user.getNickname(), true);
        }
    }

    private OAuthRequestPort getProperProviderPort(final OAuthProvider provider) {
        return oAuthRequestPortMap.get(provider);
    }

    @Transactional
    public void updateNickname(final Long userId, final UpdateNicknameRequest request) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.updateNickname(request.nickname());
    }

    @Transactional
    public void withdraw(final Long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.withdraw();
    }
}
