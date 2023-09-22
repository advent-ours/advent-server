package com.adventours.calendar.member.service;

import com.adventours.calendar.member.domain.Member;
import com.adventours.calendar.member.domain.OAuthProvider;
import com.adventours.calendar.member.persistence.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MemberService {
    private final Map<OAuthProvider, OAuthRequestPort> oAuthRequestPortMap;
    private final MemberRepository memberRepository;

    public MemberService(final Map<OAuthProvider, OAuthRequestPort> oAuthRequestPortMap, final MemberRepository memberRepository) {
        this.oAuthRequestPortMap = oAuthRequestPortMap;
        this.memberRepository = memberRepository;
    }

    public LoginResponse login(final OAuthProvider provider, final LoginRequest request) {
        OAuthRequestPort oAuthRequestPort = getProperProviderPort(provider);
        OAuthUserInformation userInformation = oAuthRequestPort.requestUserInformation(request.token());
        Optional<Member> optionalMember = memberRepository.findByProviderAndProviderId(provider, userInformation.providerId());
        if (optionalMember.isPresent()) {
            return new LoginResponse(optionalMember.get().getId(), false);
        } else {
            final Member member = memberRepository.save(new Member());
            return new LoginResponse(member.getId(), true);
        }

    }

    private OAuthRequestPort getProperProviderPort(final OAuthProvider provider) {
        return oAuthRequestPortMap.get(provider);
    }
}
