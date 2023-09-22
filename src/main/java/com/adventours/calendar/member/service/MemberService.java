package com.adventours.calendar.member.service;

import com.adventours.calendar.member.domain.Member;
import com.adventours.calendar.member.domain.OAuthProvider;
import com.adventours.calendar.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final Map<OAuthProvider, OAuthRequestPort> oAuthRequestPortMap;
    private final MemberRepository memberRepository;

    public LoginResponse login(final OAuthProvider provider, final LoginRequest request) {
        OAuthRequestPort oAuthRequestPort = getProperProviderPort(provider);
        OAuthProviderInformation userInformation = oAuthRequestPort.requestUserInformation(request.token());
        Optional<Member> optionalMember = memberRepository.findByProviderAndProviderId(provider, userInformation.getProviderId());
        if (optionalMember.isPresent()) {
            return new LoginResponse(optionalMember.get().getId(), false);
        } else {
            final Member member = memberRepository.save(new Member(
                    provider,
                    userInformation.getProviderId(),
                    userInformation.getInitialProfileImage()
            ));
            return new LoginResponse(member.getId(), true);
        }
    }

    private OAuthRequestPort getProperProviderPort(final OAuthProvider provider) {
        return oAuthRequestPortMap.get(provider);
    }
}
